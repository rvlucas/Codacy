package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.GitCommit;

public class GitCliImpl implements GitCli {
	private static String DIR = "../Clones";
	
	private String url;
	private Runtime rt;

	public GitCliImpl(String url) {
		this.url = url;

		this.rt = Runtime.getRuntime();
	}

	@Override
	public void gitClone() {
		new File(DIR).mkdirs();

		try {
			Process p = rt.exec("git clone " + url, null, new File(DIR));
			p.waitFor();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<GitCommit> gitLog() {
		List<GitCommit> commits = new ArrayList<>();
		GitCommit gitCommit;
		
		String[] urlSplit = url.split("/");
		String fileName = urlSplit[urlSplit.length - 1].replace(".git", "");

		try {
			Process p = rt.exec("git log --pretty=format:\"%H%x26%x26%x26%an%x26%x26%x26%cd%x26%x26%x26%s\"", null, new File(DIR + "/" + fileName));
//			p.waitFor();
			InputStream is = p.getInputStream();
			InputStream err = p.getErrorStream();
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

    		String result = new BufferedReader(new InputStreamReader(err)).lines()
    				   .parallel().collect(Collectors.joining("\n"));
    		System.out.println(result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return commits;
	}

	


}
