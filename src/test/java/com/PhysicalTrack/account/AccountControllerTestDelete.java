package com.PhysicalTrack.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTestDelete {


    @Autowired
    private MockMvc mockMvc;
	
	@Test
	void test() throws Exception {
		
		
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZpY2VJZCI6ImF1dHVtbldpdGhDb21wb3NlMTIzMTIiLCJ1c2VySWQiOjMsIm5hbWUiOiLrsLDsp4TtlZgiLCJpYXQiOjE3MzcxODcyNTYsImV4cCI6MTczNzQ0NjQ1Nn0.N5EHqMNm3oP3860g0OK1mu8y1LC_ZheLZx6XiNwL8eE";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
        		.andDo(MockMvcResultHandlers.print());
	}

}
