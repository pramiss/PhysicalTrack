package com.PhysicalTrack.records;

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
class RecordControllerTestRunning {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRunningRecord() throws Exception {
    	
    	// ObjectMapper 사용
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> request = new HashMap<>();
        
        request.put("duration", 1242.31);
        // request.put("tempo", Arrays.asList(0.3, 1.32, 2.5));
        
        String content = objectMapper.writeValueAsString(request);
        
        // token
    	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImRldmljZTEyMzQ1Njc4OSIsInVzZXJJZCI6NSwibmFtZSI6Iu2Zjeq4uOuPmSIsImlhdCI6MTczMDk4NzMyMywiZXhwIjoxOTkwMTg3MzIzfQ.r_REPaYe8UGXiWJ92Gseo_wp7rSNl5RMtjhxUpYCxXw";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record/running")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(content))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
