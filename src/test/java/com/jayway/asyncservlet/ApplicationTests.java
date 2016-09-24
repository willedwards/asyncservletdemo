package com.jayway.asyncservlet;

import com.jayway.asyncservlet.domain.RepoListDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.assertTrue;

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
}
