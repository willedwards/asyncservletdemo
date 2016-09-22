package com.jayway.asyncservlet.github;

import com.jayway.asyncservlet.domain.RepoListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

class RepositoryListDtoAdapter extends ListenableFutureAdapter<RepoListDto, ResponseEntity<GitHubItems>> {

    private final Function<GitHubItems, RepoListDto> adaptFunction;

    public RepositoryListDtoAdapter(ListenableFuture<ResponseEntity<GitHubItems>> gitHubItems,Function<GitHubItems, RepoListDto> adaptFunction ) {
        super(gitHubItems);
        this.adaptFunction = adaptFunction;
    }

    @Override
    protected RepoListDto adapt(ResponseEntity<GitHubItems> responseEntity) throws ExecutionException {
        return adaptFunction.apply( responseEntity.getBody( ) );
    }
}
