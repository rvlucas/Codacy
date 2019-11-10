package rest.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import exceptions.GitCliException;
import model.GitCommit;
import rest.services.GitApiServiceImpl;
import rest.services.GitCliServiceImpl;

@Path("/gitcli")
public class GitCliController {
	private static final Logger LOGGER = Logger.getLogger(GitCliController.class.getName());

	private GitCliServiceImpl gitCliService;
	private GitApiServiceImpl gitApiService;


	/**
	 * Endpoint that gets a list commits
	 * @param url - where the repository is
	 * @param page - the number of the page to be return
	 * @param perPage - the amount of commits per page
	 * @return a response with a JSON that contains the page with a list of commits with a maximum of perPage commits
	 * @throws GitCliException
	 */
	@GET
	@Path("/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitList(@QueryParam("url") String url, @QueryParam("page") @DefaultValue("1") int page,
			@QueryParam("perPage") @DefaultValue("10") int perPage) throws GitCliException {

		LOGGER.info("Getting the commits");
		
		gitApiService = new GitApiServiceImpl();
		List<GitCommit> commits = gitApiService.getLogs(url, page, perPage);
		
		return Response.ok(commits).build();
		
//		try {
//			gitCliService = new GitCliServiceImpl();
//			List<GitCommit> commits = gitCliService.getLogs(url, page, perPage);
//			return Response.ok(commits).build();
//		} catch (GitCliException e) {
//			return Response.serverError().entity("An error has occurred while executing: " + e.getMessage()).build();			
//		}
	}

}
