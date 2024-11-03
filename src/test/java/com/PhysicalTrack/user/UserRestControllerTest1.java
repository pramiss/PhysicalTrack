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

    @Test // sign-up 테스트
    public void testSignUp() throws Exception {
    	
        String json = "{\"deviceId\":\"mega29173\",\"name\":\"애플망고\",\"birthYear\":2099,\"gender\":\"male\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
