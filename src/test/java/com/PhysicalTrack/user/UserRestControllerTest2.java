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

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @Test // sign-in 테스트
    public void testSignIn() throws Exception {
    	
        String json = "{\"deviceId\":\"device123456789\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
