package rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import exceptions.GitCliException;
import git.GitCli;
import git.GitCliImpl;
import model.GitCommit;

public class GitCliServiceImpl implements GitService {
	private static final Logger LOGGER = Logger.getLogger(GitCliServiceImpl.class.getName());
	
	/**
	 * Gets a list of the commits made in the repository
	 * @param url - where the repository is
	 * @param page - the number of the page to be return
	 * @param perPage - the amount of commits per page
	 * @return list of commits with a maximum of perPage commits
	 * @throws GitCliException
	 */
	public List<GitCommit> getLogs(String url, int page, int perPage) throws GitCliException {
		
		checkParameters(url, page, perPage);

		GitCli gitCli = new GitCliImpl(url);

		try {
			gitCli.gitClone();
			List<GitCommit> commits = gitCli.gitLog();

			LOGGER.info("Printing logs...");
			return getPage(commits, page, perPage);
		} catch (GitCliException e) {
			LOGGER.severe("An error has occurred while executing: " + e.getMessage());
			throw new GitCliException(e.getMessage());
		}
	}

	/**
	 * Returns the list filtered by page
	 * @param commits - list of all commits
	 * @param page - the number of the page to be return
	 * @param perPage - the amount of commits per page
	 * @return commits filtered by page with a maximum of perPage commits
	 */
	private List<GitCommit> getPage(List<GitCommit> commits, int page, int perPage) throws GitCliException {		
		if(page * perPage > commits.size()){
			LOGGER.severe("Page number does not exist. Please choose a lower numbered page");
			throw new GitCliException("Page number does not exist. Please choose a lower numbered page");
		}
		
		List<GitCommit> result = new ArrayList<GitCommit>();

		for (int i = 0; i < perPage && i < commits.size(); i++) {
			result.add(commits.get(((page - 1) * perPage) + i));
		}

		return result;
	}

}
