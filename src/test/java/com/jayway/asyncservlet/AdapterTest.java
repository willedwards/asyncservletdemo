package com.jayway.asyncservlet;/**
 * Author: wge
 * Date: 20/09/2016
 * Time: 11:11
 */

import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AdapterTest
{
    private static final Logger log = LoggerFactory.getLogger(AdapterTest.class);

    //under test
    RepoListService repoListService = new GitHubMockService();

    @Test
    public void testAdapter() throws InterruptedException, ExecutionException
    {
        //fire the search
        log.info("Thread " + Thread.currentThread().getName() + "about to call search");

        ListenableFuture<?> repositoryListDto = repoListService.search("spring+boot");

        log.info("Thread " + Thread.currentThread().getName() + "looking at result");

        RepoListDto result = (RepoListDto)repositoryListDto.get();
        assertNotNull(result);

        assertEquals(2,result.getNbrOfRepositories());

    }
}