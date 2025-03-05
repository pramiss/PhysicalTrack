package com.PhysicalTrack.records;

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
class RecordControllerTestRunning {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRunningRecord() throws Exception {
    	
    	// ObjectMapper 사용
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> request = new HashMap<>();
        
        request.put("duration", 1463.0);
        request.put("tempo", Arrays.asList(35.258164048194885,38.00011122226715,43.00005900859833,37.99994397163391,37.99994385242462,39.00001621246338,38.00003254413605,37.99993145465851,47.00000274181366,82.00000941753387,76.00001418590546,76.9999520778656,49.000009059906006,39.99998128414154,41.00000524520874,40.00000739097595,41.99999213218689,39.00000333786011,43.00000762939453,58.99997639656067,77.00001430511475,75.00002598762512,78.99997437000275,50.9999965429306,38.000009655952454,41.99999213218689,40.000009417533875,42.00001132488251,40.99997961521149,41.000049233436584));
        
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
