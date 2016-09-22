package com.jayway.asyncservlet;/**
 * Author: wge
 * Date: 21/09/2016
 * Time: 10:25
 */

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

import java.util.concurrent.Callable;
import java.util.function.Function;


public class GitHubMockService implements RepoListService
{
    private static final Logger log = LoggerFactory.getLogger(GitHubMockService.class);

    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();

    private final RepoListDto cannedResponse;

    public GitHubMockService(final RepoListDto cannedResponse) {
        this.cannedResponse = cannedResponse;
    }

    @Override
    public ListenableFuture<RepoListDto> search(String query, Function adapt)
    {
        ListenableFuture<RepoListDto> task = simpleAsyncTaskExecutor.submitListenable(new Callable<RepoListDto>() {
            @Override
            public RepoListDto call() throws Exception
            {
                    log.info("taking time for remote call to complete");
                    Thread.sleep(6000);
                    return cannedResponse;
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

        //ResponseEntity<RepoListDto> result = (ResponseEntity<RepoListDto>)deferredResult.getResult();;

        return task;//result.getBody();

    }
}
