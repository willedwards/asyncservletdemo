package com.jayway.asyncservlet.domain;

import com.jayway.asyncservlet.github.GitHubItem;
import com.jayway.asyncservlet.github.GitHubItems;
import com.jayway.asyncservlet.github.GitHubOwner;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface GitDTOMapping
{
    static Function<GitHubItems, RepoListDto> adaptFunction(String query){
        return items -> {
                          List<RepoDto> repoDtos = items.items( ).stream( ).map(githubItem2RepoDtoFunction).collect(Collectors.toList( ) );
                          return new RepoListDto( query, items.totalCount( ), repoDtos );
                };
    }

    Function<GitHubItem, RepoDto> githubItem2RepoDtoFunction = item -> {
        GitHubOwner owner = item.owner();
        return new RepoDto(item.fullName(),
                           item.getUrl(),
                           item.description(),
                           owner.userName(),
                           owner.url(),
                           owner.avatarUrl());
    };
}
