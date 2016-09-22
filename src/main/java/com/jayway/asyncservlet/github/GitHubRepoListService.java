package com.jayway.asyncservlet.github;

import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.function.Function;

public class GitHubRepoListService implements RepoListService<GitHubItems, RepoListDto> {
    private static final Logger log = LoggerFactory.getLogger(GitHubRepoListService.class);

    // API spec https://developer.github.com/v3/search/#search-repositories
    // https://developer.github.com/v3/#pagination
    // https://developer.github.com/v3/#rate-limiting
    private static final String QUESTIONS_URL = "https://api.github.com/search/repositories?q={query}";
    private final AsyncRestTemplate asyncRestTemplate;

    public GitHubRepoListService(AsyncRestTemplate asyncRestTemplate)
    {
        this.asyncRestTemplate = asyncRestTemplate;
    }

    @Override
    public ListenableFuture<RepoListDto> search(String query, Function<GitHubItems, RepoListDto> mapping) {
        ListenableFuture<ResponseEntity<GitHubItems>> gitHubItems = asyncRestTemplate.getForEntity(QUESTIONS_URL, GitHubItems.class, query);
        log.info("called async");
        return new RepositoryListDtoAdapter(gitHubItems, mapping);
    }
}
