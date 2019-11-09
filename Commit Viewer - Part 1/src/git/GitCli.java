package git;

import java.util.List;

import model.GitCommit;

public interface GitCli {

	void gitClone();
	
	//void gitFetch();
	
	List<GitCommit> gitLog();

}
