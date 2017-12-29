package org.mjjaen.rest.contentnegotiation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mjjaen.rest.contentnegotiation.model.businessObject.MiMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContentnegotiationApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void textXml() throws Exception {
		mockMvc.perform(get("/webservices/content").header("Accept", "application/xml"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MiMediaType.APPLICATION_XML_UTF8));
	}
	
	@Test
	public void textJson() throws Exception {
		mockMvc.perform(get("/webservices/content").header("Accept", "application/json"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
}
