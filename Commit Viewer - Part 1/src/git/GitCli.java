package git;

import java.util.List;

import exceptions.GitCliException;
import model.GitCommit;

public interface GitCli {

	/**
	 * Clones the git repository provided by an url
	 * @throws GitCliException
	 */
	void gitClone() throws GitCliException;
	
	/**
	 * Gets a list of the commits made in the repository
	 * @return list of the commits
	 * @throws GitCliException
	 */
	List<GitCommit> gitLog() throws GitCliException;

}
