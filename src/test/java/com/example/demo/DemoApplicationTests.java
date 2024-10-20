package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DemoApplicationTests {


    private TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;
	@Test
	void contextLoads() {
        //"Welcome to the Demo Application!"
	}

    @Test
    void getGreeting() {
        String baseUrl = "http://localhost:" + port + "/hello";
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        assertThat(response.getBody()).contains("Welcome");
    }

}
