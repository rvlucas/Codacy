package rest.services;

import java.util.List;

import exceptions.GitCliException;
import model.GitCommit;

public interface GitCliService {

	List<GitCommit> getLogs(String url, int page, int perPage) throws GitCliException;
}
