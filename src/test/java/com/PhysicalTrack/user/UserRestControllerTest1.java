package com.PhysicalTrack.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSignUp() throws Exception {
    	
        String json = "{\"deviceId\":\"device123\",\"name\":\"홍길동\",\"age\":33,\"gender\":\"male\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
