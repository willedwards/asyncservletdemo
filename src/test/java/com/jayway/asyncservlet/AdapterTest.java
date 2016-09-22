package com.jayway.asyncservlet;/**
 * Author: wge
 * Date: 20/09/2016
 * Time: 11:11
 */

import com.jayway.asyncservlet.domain.RepoDto;
import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AdapterTest
{
    private static final Logger log = LoggerFactory.getLogger(AdapterTest.class);

    //under test
    RepoListService repoListService;
    RepoListDto cannedResponse;
    RepoDto item1;
    RepoDto item2;

    @Before
    public void init(){
        cannedResponse = buildFakeData();
        repoListService = new GitHubMockService(cannedResponse);
    }

    private RepoListDto buildFakeData(){
        try {
            item1 = new RepoDto("name1", new URL("http://url1"), "desc1", "owner1", new URL("http://owner1URL"), new URL("http://owner1Avatar"));
            item2 = new RepoDto("name2", new URL("http://url2"), "desc2", "owner2",new URL("http://owner2URL"),new URL("http://owner2Avatar"));
             RepoListDto repoList = new RepoListDto("spring+boot", 2, Arrays.asList(item1, item2));
             return repoList;
         }
         catch (MalformedURLException e) {
             log.error("failed");
             throw new RuntimeException(e);
         }
    }

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
        List<RepoDto> bothRepos = result.getRepositories();
        assertTrue(bothRepos.contains(item1));
        assertTrue(bothRepos.contains(item2));

    }
}