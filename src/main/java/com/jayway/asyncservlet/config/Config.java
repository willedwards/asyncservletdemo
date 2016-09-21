package com.jayway.asyncservlet.config;/**
 * Author: wge
 * Date: 21/09/2016
 * Time: 10:21
 */

import com.jayway.asyncservlet.domain.RepoListService;
import com.jayway.asyncservlet.github.GitHubRepoListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class Config
{
    @Bean
    AsyncRestTemplate buildRestTemplate(){
        return new AsyncRestTemplate();
    }

    @Bean
    RepoListService buildRepoListService(AsyncRestTemplate asyncRestTemplate){
        return new GitHubRepoListService(asyncRestTemplate);
    }
}
