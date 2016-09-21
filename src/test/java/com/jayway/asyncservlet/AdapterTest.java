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
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.async.DeferredResult;

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

        Object result = repositoryListDto.get();
        assertNotNull(result);

        DeferredResult res = (DeferredResult)result;
        ResponseEntity< RepoListDto> list = (ResponseEntity< RepoListDto>)res.getResult();
        assertNotNull(list);

        RepoListDto repoListDto = list.getBody();
        assertEquals(2,repoListDto.getNbrOfRepositories());

    }
}