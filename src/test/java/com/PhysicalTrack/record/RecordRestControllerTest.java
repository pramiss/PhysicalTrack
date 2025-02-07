package com.PhysicalTrack.record;

import java.util.Arrays;
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
class RecordRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPushupRecord() throws Exception {
    	
    	// ObjectMapper 사용
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> request = new HashMap<>();
        
        request.put("quantity", 44);
        request.put("tempo", Arrays.asList(0.3, 1.32, 2.5, 0.3, 1.32, 2.5));
        
        String content = objectMapper.writeValueAsString(request);
        
        // token
    	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6InNsZGZrajAzZGQxIiwidXNlcklkIjo3LCJuYW1lIjoi6rmA7Yag7J21IiwiaWF0IjoxNzM4NzQ4Njc3LCJleHAiOjE3MzkwMDc4Nzd9.3AaKNObbf9KvHHwOxDDAd2w9uUiEaLf69zhXRMhuE0M";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record/pushup")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(content))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
