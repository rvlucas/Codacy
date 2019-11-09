package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import exceptions.GitCliException;
import model.GitCommit;

public class GitCliImpl implements GitCli {
	private static final Logger LOGGER = Logger.getLogger(GitCli.class.getName());

	private static String DIR = "../Clones";
	private static String GIT_FORMAT = "--pretty=format:\"%H%x26%x26%x26%an%x26%x26%x26%cd%x26%x26%x26%s\"";
	private static String GIT_CLONE = "git clone ";
	private static String GIT_LOG = "git log ";
	private static String GIT_FETCH = "git fetch ";

	private String url;
	private Runtime rt;
	private String fileName;

	public GitCliImpl(String url) {
		this.url = url;

		rt = Runtime.getRuntime();

		String[] urlSplit = url.split("/");
		fileName = urlSplit[urlSplit.length - 1].replace(".git", "");
	}

	/**
	 * Clones the git repository provided by an url
	 * @throws GitCliException
	 */
	@Override
	public void gitClone() throws GitCliException {
		if (Files.notExists(Paths.get(DIR, fileName))) {

			LOGGER.info("Repository does not locally exist. Cloning " + url);

			new File(DIR).mkdirs();

			try {
				Process p = rt.exec(GIT_CLONE + url, null, new File(DIR));
				errorChecking(p);
				LOGGER.info("Cloning finished with success");

			} catch (IOException | InterruptedException e) {
				throw new GitCliException(e.getMessage());
			}
		} else {
			gitFetch();
		}
	}

	/**
	 * Gets a list of the commits made in the repository
	 * @return list of the commits
	 * @throws GitCliException
	 */
	@Override
	public List<GitCommit> gitLog() throws GitCliException {
		List<GitCommit> commits = new ArrayList<>();
		GitCommit gitCommit;

		try {
			LOGGER.info("Logging commits from " + fileName);

			Process p = rt.exec(GIT_LOG + GIT_FORMAT, null, new File(DIR + "/" + fileName));

			errorChecking(p);

			InputStream is = p.getInputStream();

			String str;
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			if (is != null) {
				while ((str = reader.readLine()) != null) {
					String[] commit = str.substring(1, str.length() - 1).split("&&&");

					gitCommit = new GitCommit(commit[0], commit[1], commit[3], commit[2]);
					commits.add(gitCommit);
				}
			}
			is.close();

			LOGGER.info("Logging finished with success");

		} catch (IOException | InterruptedException e) {
			throw new GitCliException(e.getMessage());
		}

		return commits;
	}
	/**
	 * Fetch the git repository provided by an url
	 * @throws GitCliException
	 */
	private void gitFetch() throws GitCliException {

		LOGGER.info("Repository already exists locally. Fetching most recent changes from " + fileName);

		try {
			Process p = rt.exec(GIT_FETCH, null, new File(DIR + "/" + fileName));
			errorChecking(p);

			LOGGER.info("Fetching finished with success");

		} catch (IOException | InterruptedException e) {
			throw new GitCliException(e.getMessage());
		}

	}

	/**
	 * Waits for the p process to end and checks if an error occur
	 * @param p - the process in execution
	 * @throws InterruptedException
	 * @throws GitCliException
	 */
	private void errorChecking(Process p) throws InterruptedException, GitCliException {
		int status = p.waitFor();
		if (status != 0) {
			InputStream err = p.getErrorStream();
			String result = new BufferedReader(new InputStreamReader(err)).lines().parallel()
					.collect(Collectors.joining("\n"));

			throw new GitCliException(result);
		}
	}

}
