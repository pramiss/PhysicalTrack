package com.PhysicalTrack.user;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @Test // sign-up 테스트
    public void testSignUp() throws Exception {
    	
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceId", "device16889");
        jsonObject.put("name", "홍길동");
        jsonObject.put("birthYear", 1996);
        jsonObject.put("gender", "male");
    	

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
        		.andDo(MockMvcResultHandlers.print());
        
    }

}
