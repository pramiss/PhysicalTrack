package com.PhysicalTrack.account;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTestUpdate {

    @Autowired
    private MockMvc mockMvc;
	
	@Test
	void test() throws Exception {
		
    	// ObjectMapper 사용
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> request = new HashMap<>();
        
        request.put("name", "라민 야말");
        request.put("birthYear", 2007);
        request.put("gender", "male");
        
        String content = objectMapper.writeValueAsString(request);
        
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImRldmljZTEyMzQ1Njc4OSIsInVzZXJJZCI6NSwibmFtZSI6Iu2Zjeq4uOuPmSIsImlhdCI6MTczMDk4NzMyMywiZXhwIjoxOTkwMTg3MzIzfQ.r_REPaYe8UGXiWJ92Gseo_wp7rSNl5RMtjhxUpYCxXw";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/account/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(content))
        		.andDo(MockMvcResultHandlers.print());
	}

}
