package org.mjjaen.rest.internationalization;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mjjaen.rest.internationalization.controller.InternationalizationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InternationalizationApplicationTests {
	@SuppressWarnings("unused")
	@Autowired
    private MockMvc mockMvc;
	
	WebTestClient client;
	
	@Before
	public void config() {
		client = WebTestClient.bindToController(new InternationalizationController()).build();
	}
    @Test
    
    public void spanishTest()  {
    }
}
