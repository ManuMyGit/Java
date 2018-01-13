package org.mjjaen.microservices.limitsservice;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mjjaen.microservices.limitsservice.bean.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LimitsServiceApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private Configuration configuration;
	
	@Test
	public void contextLoads() throws Exception {
		mockMvc.perform(get("/microservices/limits"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.maximum", is(configuration.getMaximum())))
			.andExpect(jsonPath("$.minimum", is(configuration.getMinimum())));
	}

	public MockMvc getMockMvc() {
		return mockMvc;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
