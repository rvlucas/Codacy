package rest.services;

import java.util.List;

import exceptions.GitCliException;
import model.GitCommit;

public interface GitService {

	/**
	 * Gets a list of the commits made in the repository
	 * @param url - where the repository is
	 * @param page - the number of the page to be return
	 * @param perPage - the amount of commits per page
	 * @return list of commits with a maximum of perPage commits
	 * @throws GitCliException
	 */
	List<GitCommit> getLogs(String url, int page, int perPage) throws GitCliException;
}
