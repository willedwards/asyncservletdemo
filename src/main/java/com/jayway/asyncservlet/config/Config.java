package com.jayway.asyncservlet.config;/**
 * Author: wge
 * Date: 19/09/2016
 * Time: 14:30
 */

import com.jayway.asyncservlet.domain.RepoListService;
import com.jayway.asyncservlet.github.GitHubRepoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class Config
{
    private static final Logger log = LoggerFactory.getLogger(Config.class);



    @Bean
    RepoListService buildRepoListService(){
        return new GitHubRepoListService();
    }
}
