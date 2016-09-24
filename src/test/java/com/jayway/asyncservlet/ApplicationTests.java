package com.jayway.asyncservlet;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.AsyncRestTemplate;

import com.jayway.asyncservlet.domain.RepoListDto;
import com.jayway.asyncservlet.domain.RepoListService;
import com.jayway.asyncservlet.github.GitHubItems;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTests extends MockMvcTest {
    private static Logger log = LoggerFactory.getLogger(ApplicationTests.class);

    @Test
    @SuppressWarnings( "unchecked" )
    public void shouldPass_springBootRepo() throws Exception {
        Object output = fireGETAsObject("http://localhost:8080/async?q=spring+boot");

        ResponseEntity<RepoListDto> op = (ResponseEntity<RepoListDto>) output;

        RepoListDto result = op.getBody();

        log.info(result.toString());

        assertTrue(op.getStatusCode().is2xxSuccessful());
    }
    
    @Import(Application.class)
    @Configuration
    public static class TestConfig {
        @Bean
        RepoListService<GitHubItems, RepoListDto> buildRepoListService(AsyncRestTemplate asyncRestTemplate){
            String json = "{ \"total_count\": 2, \"items\": ["
                    + "{ \"full_name\": \"test1/name1\", \"description\": \"description1\", \"html_url\": \"https://url1\","
                    + " \"owner\": { \"login\": \"test1\", \"avatar_url\": \"https://avatar1\", \"html_url\": \"https://owner1\" }"
                    + "},"
                    + "{ \"full_name\": \"test2/name2\", \"description\": \"description2\", \"html_url\": \"https://url2\","
                    + " \"owner\": { \"login\": \"test2\", \"avatar_url\": \"https://avatar2\", \"html_url\": \"https://owner2\" }"
                    + "} ] }";

            return new GitHubMockService(json);
        }
    }
}
