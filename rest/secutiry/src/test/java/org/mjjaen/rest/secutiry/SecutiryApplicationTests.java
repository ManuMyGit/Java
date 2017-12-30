package org.mjjaen.rest.secutiry;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.
*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecutiryApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void correctAuthentication() throws Exception {
		this.mockMvc
        	.perform(get("/webservices/security").header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("username:password".getBytes())))
        	.andExpect(status().isOk());
	}
	
	@Test
	public void incorrectAuthentication() throws Exception {
		this.mockMvc
        	.perform(get("/webservices/security").header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:password".getBytes())))
        	.andExpect(status().isUnauthorized());
	}
}
