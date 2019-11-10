package rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import exceptions.GitCliException;
import git.GitCli;
import git.GitCliImpl;
import model.GitCommit;

public class GitCliServiceImpl implements GitCliService {
	private static final Logger LOGGER = Logger.getLogger(GitCliServiceImpl.class.getName());

	public List<GitCommit> getLogs(String url, int page, int perPage) throws GitCliException {

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

	private List<GitCommit> getPage(List<GitCommit> commits, int page, int perPage) {
		List<GitCommit> result = new ArrayList<GitCommit>();

		for (int i = 0; i < perPage; i++) {
			result.add(commits.get(page + i));
		}

		return result;
	}

}
