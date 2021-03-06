package rest.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.GitCliException;
import model.GitCommit;

public class GitApiServiceImpl implements GitService {
	private static final Logger LOGGER = Logger.getLogger(GitApiServiceImpl.class.getName());

	private static final String GITHUB_API_URL = "https://api.github.com/repos/<user>/<repo>/commits?page=<page>&per_page=<perPage>";

	private static final String ACCEPT_HEADER_VALUE = "application/vnd.github.v3+json";

	/**
	 * Gets a list of the commits made in the repository using git api
	 * 
	 * @param url
	 *            - where the repository is
	 * @param page
	 *            - the number of the page to be return
	 * @param perPage
	 *            - the amount of commits per page
	 * @return list of commits with a maximum of perPage commits
	 * @throws GitCliException
	 */
	public List<GitCommit> getLogs(String url, int page, int perPage) throws GitCliException {

		checkParameters(url, page, perPage);

		String[] urlSplit = url.split("/");
		String repo = urlSplit[urlSplit.length - 1].replace(".git", "");
		String user = urlSplit[urlSplit.length - 2];

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(GITHUB_API_URL.replace("<user>", user).replace("<repo>", repo)
				.replace("<page>", String.valueOf(page)).replace("<perPage>", String.valueOf(perPage)));
		get.addHeader("Accept", ACCEPT_HEADER_VALUE);

		CloseableHttpResponse response;
		try {
			response = client.execute(get);
			InputStream is = response.getEntity().getContent();
			String s = IOUtils.toString(is, "UTF-8");
			JSONArray commits = new JSONArray(s);

			return getCommits(commits);
		} catch (IOException e) {
			LOGGER.severe("An error has occurred while fetching commits using gitApiService: " + e.getMessage());
			throw new GitCliException("An error has occurred while fetching commits using gitApiService: " + e.getMessage());
		}
	}

	/**
	 * Parses a JSON array into a list of commits
	 * 
	 * @param array
	 * @return
	 */
	private List<GitCommit> getCommits(JSONArray array) {
		List<GitCommit> commits = new ArrayList<>();
		GitCommit gitCommit;
		String hash, author, message, time;
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			hash = jo.getString("sha");
			author = jo.getJSONObject("commit").getJSONObject("author").getString("name");
			time = jo.getJSONObject("commit").getJSONObject("committer").getString("date");
			message = jo.getJSONObject("commit").getString("message");
			gitCommit = new GitCommit(hash, author, message, time);
			commits.add(gitCommit);
		}

		return commits;
	}

}
