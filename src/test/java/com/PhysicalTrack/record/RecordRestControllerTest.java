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
        workoutDetailJosnMap.put("quantity", 67);
        
        // body JSON
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("workoutId", 1);
        jsonMap.put("workoutDetail", objectMapper.writeValueAsString(workoutDetailJosnMap));
    	
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6Im1lZ2EyOTE3MyIsInVzZXJJZCI6MiwibmFtZSI6IuyVoO2UjOunneqzoCIsImlhdCI6MTczMDYxNzE4OSwiZXhwIjoxNzMwODc2Mzg5fQ.bMduZvjwf87gGrR8C9-RVAIDLjSFAAoIrag-hCog8aU
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImRldmljZTg4MjcyIiwidXNlcklkIjoxLCJuYW1lIjoi66mU66i465OcIiwiaWF0IjoxNzMwNjE3MzIyLCJleHAiOjE3MzA4NzY1MjJ9.W89zHDZAtOUZk4B1pELybmNy1SN1YZ9QI-WXwY80IEg
    	String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImF1dHVtbldpdGhDb21wb3NlMTIzMTIiLCJ1c2VySWQiOjMsIm5hbWUiOiLrsLDsp4TtlZgiLCJpYXQiOjE3MzA2ODk4NTcsImV4cCI6MTczMDk0OTA1N30.DyUnQf3hGbrpTkkJ5Rqhxua4HrxQpgp_zLttGJj0wzs";
        String json = objectMapper.writeValueAsString(jsonMap);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record/pushups")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
