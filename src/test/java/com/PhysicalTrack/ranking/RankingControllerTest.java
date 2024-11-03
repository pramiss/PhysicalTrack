package com.PhysicalTrack.ranking;

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
class RankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test // pushup ranking 가져오기
    public void testPushupRanking() throws Exception {
    	
    	// object Mapper
    	ObjectMapper objectMapper = new ObjectMapper();
    	
        // body JSON
        Map<String, Object> jsonMap = new HashMap<>();
        
    	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6Im1lZ2EyOTE3MyIsInVzZXJJZCI6MiwibmFtZSI6IuyVoO2UjOunneqzoCIsImlhdCI6MTczMDYxNzE4OSwiZXhwIjoxNzMwODc2Mzg5fQ.bMduZvjwf87gGrR8C9-RVAIDLjSFAAoIrag-hCog8aU";
        String json = objectMapper.writeValueAsString(jsonMap);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ranking/pushup")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }


}
