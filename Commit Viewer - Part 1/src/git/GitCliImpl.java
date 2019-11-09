package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.GitCommit;

public class GitCliImpl implements GitCli {
	private static String DIR = "../Clones";
	private static String GIT_FORMAT = "--pretty=format:\"%H%x26%x26%x26%an%x26%x26%x26%cd%x26%x26%x26%s\"";
	private static String GIT_CLONE = "git clone ";
	private static String GIT_LOG = "git log ";
	private static String GIT_FETCH = "git fetch ";
	
	private String url;
	private Runtime rt;
	private String fileName;

	public GitCliImpl(String url) {
		this.url = url;

		rt = Runtime.getRuntime();
		
		String[] urlSplit = url.split("/");
		fileName = urlSplit[urlSplit.length - 1].replace(".git", "");
	}

	@Override
	public void gitClone() {
		if (Files.notExists(Paths.get(DIR, fileName))) {
			new File(DIR).mkdirs();

			try {
				Process p = rt.exec(GIT_CLONE + url, null, new File(DIR));
				p.waitFor();
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		else{
			gitFetch();
		}
		
		
		
	}

	@Override
	public List<GitCommit> gitLog() {
		List<GitCommit> commits = new ArrayList<>();
		GitCommit gitCommit;

		try {
			Process p = rt.exec(GIT_LOG + GIT_FORMAT, null, new File(DIR + "/" + fileName));

			InputStream is = p.getInputStream();
//			InputStream err = p.getErrorStream();
			String str;
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            if (is != null) {                            
                while ((str = reader.readLine()) != null) {
                	String[] commit = str.substring(1, str.length()-1).split("&&&");

                	gitCommit = new GitCommit(commit[0], commit[1], commit[3], commit[2]);
                	commits.add(gitCommit);
                }                
            }
            is.close();

//    		String result = new BufferedReader(new InputStreamReader(err)).lines()
//    				   .parallel().collect(Collectors.joining("\n"));
//    		System.out.println(result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return commits;
	}


	private void gitFetch() {
		try {
			Process p = rt.exec(GIT_FETCH, null, new File(DIR+ "/" + fileName));
			p.waitFor();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	


}
