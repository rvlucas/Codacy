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
import rest.services.GitCliServiceImpl;

@Path("/gitcli")
public class GitCliController {
	private static final Logger LOGGER = Logger.getLogger(GitCliController.class.getName());

	private GitCliServiceImpl gitCliService;

	@GET
	@Path("/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommitList(@QueryParam("url") String url, @QueryParam("page") @DefaultValue("1") int page,
			@QueryParam("perPage") int perPage) throws GitCliException {

		LOGGER.info("Getting the commits");
		try {
			gitCliService = new GitCliServiceImpl();
			List<GitCommit> commits = gitCliService.getLogs(url, page, perPage);
			return Response.ok(commits).build();
		} catch (GitCliException e) {
			return Response.serverError().entity(e.getMessage()).build();			
		}
	}

}
