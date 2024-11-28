package com.PhysicalTrack.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    	
    	// object Mapper
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	// workoutDetail JSON
        Map<String, Object> workoutDetailJosnMap = new HashMap<>();
        workoutDetailJosnMap.put("quantity", 34);
        
        // List<Double> tempoList = new ArrayList<>(); //--> 저장O
        // List<Integer> tempoList = Arrays.asList(2, 4, 5, 6, 9); //--> 저장O
        // List<Object> tempoList = Arrays.asList(2, 4.24, 5.7, 6, 9.32); //--> 저장O
        // List<Object> tempoList = Arrays.asList(2, 4.24, 5.7, "6.2", 9.32); //--> 저장X
        // Double tempoList = 4.21; //--> 저장X
        List<Double> tempoList = Arrays.asList(2.0, 4.24, 5.7, 6.91, 9.32); //--> 저장O
        workoutDetailJosnMap.put("tempo", tempoList);

        
        // body JSON
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("workoutId", 1);
        jsonMap.put("workoutDetail", objectMapper.writeValueAsString(workoutDetailJosnMap));
    	

    	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImRldmljZTEyMzQ1Njc4OSIsInVzZXJJZCI6NSwibmFtZSI6Iu2Zjeq4uOuPmSIsImlhdCI6MTczMDk4NzMyMywiZXhwIjoxOTkwMTg3MzIzfQ.r_REPaYe8UGXiWJ92Gseo_wp7rSNl5RMtjhxUpYCxXw";
        String json = objectMapper.writeValueAsString(jsonMap);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record/pushup")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
