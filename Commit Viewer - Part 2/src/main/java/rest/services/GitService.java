package rest.services;

import java.util.List;
import java.util.logging.Logger;

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
	
	
	
	
	/**
	 * Checks if all parameters are within expected boundaries
	 * @param url
	 * @param page
	 * @param perPage
	 * @throws GitCliException
	 */
	default void checkParameters(String url, int page, int perPage) throws GitCliException {

		final Logger LOGGER = Logger.getLogger(GitService.class.getName());
		
		if(url == null){
			LOGGER.severe("Incorrect type of query parameter url");
			throw new GitCliException("The url can not be empty. A GitHub URL with the format -- https://github.com/<author>/<repository>.git -- is expected as query parameter.");	
		}
		if(page < 1){
			LOGGER.severe("Incorrect value of query parameter page");
			throw new GitCliException("Page must be greater than 1");
		}
		if(perPage < 1){
			LOGGER.severe("Incorrect value of query parameter perPage");
			throw new GitCliException("Per page must be greater than 1");
		}
	}
}
