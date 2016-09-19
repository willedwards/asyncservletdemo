package com.jayway.asyncservlet;

import com.jayway.asyncservlet.domain.RepoListService;
import com.jayway.asyncservlet.github.GitHubRepoListService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class Application {

    @Bean
    RepoListService buildRepoListService(){
        return new GitHubRepoListService();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
