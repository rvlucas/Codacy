package app;

import java.util.List;

import git.GitCli;
import git.GitCliImpl;
import model.GitCommit;

public class Main {

	public static void main(String[] args) {

		String url = args[0];

		GitCli gitCli = new GitCliImpl(url);
		
		gitCli.gitClone();
		
		List<GitCommit> commits = gitCli.gitLog();
		
		for (GitCommit commit : commits){ 
		    System.out.println(commit.toString());
		}
		
	}

}
