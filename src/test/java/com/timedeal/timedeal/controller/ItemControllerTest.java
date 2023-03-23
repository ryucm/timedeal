package com.timedeal.timedeal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timedeal.timedeal.item.service.ItemService;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    private MockHttpSession mockHttpSession = new MockHttpSession();
    private Map<String, Object> product = new HashMap<>();

    @BeforeEach
    @DisplayName("회원가입 및 로그인, 아이템 생성")
    public void signUp() throws Exception {
        Member admin = Member.builder()
                .memberId("memberId")
                .email("email")
                .password("password")
                .role(Role.ADMIN)
                .build();

        mockHttpSession.setAttribute("loginMember", admin);
        createItem();
    }

    @Test
    @DisplayName("상품등록")
    public void createItem() throws Exception{


        product.put("itemName", "product");
        product.put("price", 1000L);
        product.put("stock", 100);

        mockMvc.perform(post("/item")
                    .session(mockHttpSession)
                    .content(objectMapper.writeValueAsString(product))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품등록-Admin 권한 없음")
    public void createItemNotAdmin() throws Exception{
        Member user = Member.builder()
                .memberId("memberId")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();

        mockHttpSession.setAttribute("loginMember", user);
        product.put("itemName", "product");
        product.put("price", 1000L);
        product.put("stock", 100);

        mockMvc.perform(post("/item")
                        .session(mockHttpSession)
                        .content(objectMapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 삭제")
    public void deleteItem() throws Exception {

        mockMvc.perform(delete("/item?id=1")
                .session(mockHttpSession))
                .andExpect(status().isOk());
    }
}