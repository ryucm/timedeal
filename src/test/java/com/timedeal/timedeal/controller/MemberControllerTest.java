package com.timedeal.timedeal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import com.timedeal.timedeal.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    public void signUp() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("memberId", "memberId");
        input.put("email", "email");
        input.put("password", "password");
        input.put("role", "ADMIN");

        mockMvc.perform(post("/member")
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("회원 정보 조회")
    public void getMember() throws Exception {
        mockMvc.perform(get("/member?memberId=memberId"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 탈퇴")
    public void deleteMember() throws Exception {
        mockMvc.perform(delete("/member?memberId=memberId"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인")
    public void login() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("memberId", "memberId");
        input.put("password", "password");

        mockMvc.perform(post("/member/login")
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그아웃")
    public void logout() throws Exception {
        Member member = Member.builder()
                .memberId("test")
                .email("test")
                .password("test")
                .role(Role.ADMIN)
                .build();

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("loginMember", member);

        mockMvc.perform(post("/member/logout"))
                .andExpect(status().isOk());
    }
}