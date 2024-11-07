package com.PhysicalTrack.statistics;

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
class StatisticsControllerTestOther {

	@Autowired
    private MockMvc mockMvc;

    @Test
    public void testPushupRanking() throws Exception {
    	
    	// object Mapper
    	ObjectMapper objectMapper = new ObjectMapper();
    	
        // body JSON
        Map<String, Object> jsonMap = new HashMap<>();
        
    	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImRldmljZTEyMzQ1Njc4OSIsInVzZXJJZCI6NSwibmFtZSI6Iu2Zjeq4uOuPmSIsImlhdCI6MTczMDk4NTgzMSwiZXhwIjoxNzMxMjQ1MDMxfQ.62nZn-AGjZZOieUJf0mWhvaC0WrTbC8anc9GUQ75Vgw";
        String json = objectMapper.writeValueAsString(jsonMap);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/weekly-stats/2")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
