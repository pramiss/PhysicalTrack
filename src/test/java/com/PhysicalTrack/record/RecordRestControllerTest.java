package com.PhysicalTrack.record;

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

    @Test // sign-up 테스트
    public void testSignUp() throws Exception {
    	
    	// object Mapper
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	// workoutDetail JSON
        Map<String, Object> workoutDetailJosnMap = new HashMap<>();
        workoutDetailJosnMap.put("quantity", 30);
        
        // body JSON
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("workoutId", 1);
        jsonMap.put("workoutDetail", objectMapper.writeValueAsString(workoutDetailJosnMap));
    	

    	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImRldmljZTEyMzQ1Njc4OSIsInVzZXJJZCI6NSwibmFtZSI6Iu2Zjeq4uOuPmSIsImlhdCI6MTczMDk4NTgzMSwiZXhwIjoxNzMxMjQ1MDMxfQ.62nZn-AGjZZOieUJf0mWhvaC0WrTbC8anc9GUQ75Vgw";
        String json = objectMapper.writeValueAsString(jsonMap);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record/pushups")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
