package com.jayway.asyncservlet;/**
 * Author: wge
 * Date: 21/09/2016
 * Time: 10:25
 */

import com.jayway.asyncservlet.domain.RepoDto;
import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;


public class GitHubMockService implements RepoListService
{
    private static final Logger log = LoggerFactory.getLogger(GitHubMockService.class);

    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();

    @Override
    public ListenableFuture<RepoListDto> search(String query)
    {
        ListenableFuture<RepoListDto> task = simpleAsyncTaskExecutor.submitListenable(new Callable<RepoListDto>() {
            @Override
            public RepoListDto call() throws Exception
            {
                try
                {
                    log.info("Thread " + Thread.currentThread().getName());
                    RepoDto item1 = new RepoDto("name1", new URL("http://url1"), "desc1", "owner1",new URL("http://owner1URL"),new URL("http://owner1Avatar"));
                    RepoDto item2 = new RepoDto("name2", new URL("http://url2"), "desc2", "owner2",new URL("http://owner2URL"),new URL("http://owner2Avatar"));
                    RepoListDto repoList = new RepoListDto("spring+boot", 2, Arrays.asList(item1, item2));
                    return repoList;

                }
                catch (MalformedURLException e)
                {
                    log.error("failed");
                    throw new RuntimeException(e);
                }
            }
        });

        log.info("Thread " + Thread.currentThread().getName() + "adding callback");

        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

        task.addCallback(
                 new ListenableFutureCallback<RepoListDto>() {
                     @Override
                     public void onSuccess(RepoListDto result) {
                         log.info("success called");
                         ResponseEntity<RepoListDto> responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
                         deferredResult.setResult(responseEntity);
                     }

                     @Override
                     public void onFailure(Throwable t) {
                         log.error("Failed to fetch result from remote service", t);
                         ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
                         deferredResult.setResult(responseEntity);
                     }
                 }
         );

         log.info("Thread " + Thread.currentThread().getName() + "finished adding callback");

        return task;

    }
}
