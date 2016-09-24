package com.jayway.asyncservlet.domain;

import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Function;

public interface RepoListService<ITEM, DTO> {

    ListenableFuture<DTO> search(String query, Function<ITEM, DTO> adapt);


}


