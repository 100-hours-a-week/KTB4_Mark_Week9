package com.mark.community.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void CSRF_토큰_없이_POST_요청하면_403을_반환한다() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void GET_요청은_CSRF_토큰_없이도_통과한다() throws Exception {
        MvcResult result = mockMvc.perform(get("/csrf")).andReturn();
        assertNotEquals(403, result.getResponse().getStatus());
    }

    @Test
    void csrf토큰_발급_경로는_인증없이_접근가능하다() throws Exception {
        MvcResult result = mockMvc.perform(get("/csrf")).andReturn();
        assertNotEquals(401, result.getResponse().getStatus());
    }

    @Test
    void H2콘솔은_인증없이_접근가능하다() throws Exception {
        MvcResult result = mockMvc.perform(get("/h2-console")).andReturn();
        assertNotEquals(401, result.getResponse().getStatus());
    }

    @Test
    void 회원가입_POST_users는_인증없이_접근가능하다() throws Exception {
        MvcResult result = mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andReturn();
        assertNotEquals(401, result.getResponse().getStatus());
    }

    @Test
    void 로그인없이_게시글목록조회에_접근하면_401을_반환한다() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 로그인없이_회원정보수정을_시도하면_401을_반환한다() throws Exception {
        mockMvc.perform(patch("/users").with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 로그인없이_회원탈퇴를_시도하면_401을_반환한다() throws Exception {
        mockMvc.perform(delete("/users").with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 로그인없이_댓글작성을_시도하면_401을_반환한다() throws Exception {
        mockMvc.perform(post("/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 로그인한_유저는_게시글목록조회에_접근할_수_있다() throws Exception {
        MvcResult result = mockMvc.perform(get("/posts")).andReturn();
        assertNotEquals(401, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser
    void 로그인한_유저가_CSRF_토큰과_함께_회원탈퇴를_요청하면_403이_아니다() throws Exception {
        MvcResult result = mockMvc.perform(delete("/users").with(csrf())).andReturn();
        assertNotEquals(403, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser
    void 로그인한_유저가_CSRF_토큰없이_회원탈퇴를_요청하면_403을_반환한다() throws Exception {
        mockMvc.perform(delete("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void ROLE_USER는_임시글작성에_접근하면_403을_반환한다() throws Exception {
        mockMvc.perform(post("/posts/temp").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_AUTH_USER")
    void ROLE_AUTH_USER는_임시글작성에_접근할_수_있다() throws Exception {
        MvcResult result = mockMvc.perform(post("/posts/temp").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn();
        assertNotEquals(403, result.getResponse().getStatus());
    }
}