package com.jayway.asyncservlet;/**
 * Author: wge
 * Date: 21/09/2016
 * Time: 10:25
 */

import java.util.concurrent.Callable;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import com.jayway.asyncservlet.github.GitHubItems;


public class GitHubMockService implements RepoListService<GitHubItems, RepoListDto>
{
    private static final Logger log = LoggerFactory.getLogger(GitHubMockService.class);

    private final SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    private final GitHubItems response;

    public GitHubMockService(final String json) {
        ObjectMapper mapper = new ObjectMapper( );
        try {
            this.response = mapper.readValue(json, GitHubItems.class);
        } catch ( Exception e ) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public ListenableFuture<RepoListDto> search(String query, Function<GitHubItems, RepoListDto> adapt)
    {
        ListenableFuture<RepoListDto> task = simpleAsyncTaskExecutor.submitListenable(new Callable<RepoListDto>() {
            @Override
            public RepoListDto call() throws Exception
            {
                    log.info("taking time for remote call to complete");
                    Thread.sleep(1000);
                    return adapt.apply(response);
                }
        });

        return task;

    }
}
