package com.jayway.asyncservlet;

import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import com.jayway.asyncservlet.github.GitHubItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import static com.jayway.asyncservlet.domain.GitDTOMapping.adaptFunction;

@RestController
class AsyncController {
    private static final Logger log = LoggerFactory.getLogger(AsyncController.class);

    private final RepoListService<GitHubItems, RepoListDto> repoListService;

    @Autowired
    public AsyncController(RepoListService<GitHubItems, RepoListDto> repoListService) {
        this.repoListService = repoListService;
    }

    @RequestMapping("/async")
    DeferredResult<ResponseEntity<?>> async(@RequestParam("q") String query) {
        log.info("q = " + query);
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        ListenableFuture<RepoListDto> repositoryListDto = repoListService.search(query,adaptFunction(query));
        repositoryListDto.addCallback(
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
        return deferredResult;
    }


}
