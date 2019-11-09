package git;

import java.util.List;

import exceptions.GitCliException;
import model.GitCommit;

public interface GitCli {

	void gitClone() throws GitCliException;
	
	List<GitCommit> gitLog() throws GitCliException;

}
