package app;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import exceptions.GitCliException;
import git.GitCli;
import git.GitCliImpl;
import model.GitCommit;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		setupLog();

		if (args.length != 1) {
			LOGGER.severe("Incorrect number of arguments");
			System.out.println(
					"Incorrect number of arguments.\nA GitHub URL with the format -- https://github.com/<author>/<repository>.git -- is expected as an argument.");
			System.exit(1);
		}

		String url = args[0];
		
		if(url == null || url.isEmpty()){
			LOGGER.severe("Incorrect type of argument");
			System.out.println(
					"Incorrect type of argument.\nA GitHub URL with the format -- https://github.com/<author>/<repository>.git -- is expected as an argument.");
			System.exit(1);
		}

		GitCli gitCli = new GitCliImpl(url);

		try {
			gitCli.gitClone();
			List<GitCommit> commits = gitCli.gitLog();

			LOGGER.info("Printing logs...");
			for (GitCommit commit : commits) {
				System.out.println(commit.toString());
			}
		} catch (GitCliException e) {
			LOGGER.severe("An error has occurred while executing: " + e.getMessage());
		}
	}

	/**
	 * Setup the logger used to make the logs
	 */
	private static void setupLog() {
		FileHandler fh;
		Logger globalLogger = Logger.getLogger("");

		for (Handler handler : globalLogger.getHandlers()) {
			globalLogger.removeHandler(handler);
		}

		try {
			new File("../logs").mkdirs();
			Date date = new Date();
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");

			fh = new FileHandler("../logs/" + dateFormatter.format(date) + ".log");
			globalLogger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
