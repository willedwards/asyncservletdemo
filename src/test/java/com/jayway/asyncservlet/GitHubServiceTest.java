package com.jayway.asyncservlet;/**
 * Author: wge
 * Date: 20/09/2016
 * Time: 11:11
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;

import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import com.jayway.asyncservlet.github.GitHubItems;


public class GitHubServiceTest
{
    private static final Logger log = LoggerFactory.getLogger(GitHubServiceTest.class);

    @Test
    public void shouldMapItemToDto() throws InterruptedException, ExecutionException
    {
        String json = "{ \"total_count\": 1, \"items\": ["
                + "{ \"full_name\": \"test1/name1\", \"description\": \"description1\", \"html_url\": \"https://url1\","
                + " \"owner\": { \"login\": \"test1\", \"avatar_url\": \"https://avatar1\", \"html_url\": \"https://owner1\" }" + "} ] }";

        RepoListService<GitHubItems, RepoListDto> tst = new GitHubMockService(json);
        //fire the search
        log.info("Thread " + Thread.currentThread().getName() + "about to call search");

        ListenableFuture<?> repositoryListDto = tst.search("spring+boot", AsyncController.adaptFunction("spring+boot"));

        log.info("Thread " + Thread.currentThread().getName() + "looking at result");

        RepoListDto result = (RepoListDto) repositoryListDto.get();

        assertNotNull(result);
        assertEquals(1, result.getNbrOfRepositories());
        assertTrue(result.getRepositories( ).size( ) == 1);
        assertEquals("test1/name1", result.getRepositories( ).get( 0 ).getName( ));
        assertEquals("description1", result.getRepositories( ).get( 0 ).getDescription( ));
        assertEquals("https://url1", result.getRepositories( ).get( 0 ).getUrl( ).toString( ));
        assertEquals("test1", result.getRepositories( ).get( 0 ).getOwner( ));
        assertEquals("https://owner1", result.getRepositories( ).get( 0 ).getOwnerUrl( ).toString( ));
        assertEquals("https://avatar1", result.getRepositories( ).get( 0 ).getOwnerAvatar( ).toString( ));
    }
}