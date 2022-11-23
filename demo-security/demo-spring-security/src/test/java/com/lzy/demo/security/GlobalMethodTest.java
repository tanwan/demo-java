package com.lzy.demo.security;

import com.lzy.demo.security.config.SecurityConfig;
import com.lzy.demo.security.controller.GlobalMethodController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(GlobalMethodController.class)
@Import(SecurityConfig.class)
public class GlobalMethodTest {

    @Autowired
    private MockMvc mockMvc;



    /**
     * 测试@Secured("IS_AUTHENTICATED_FULLY")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void testFullyAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/secured/fully-authenticated"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }

    /**
     * 测试@Secured({"ROLE_USER", "ROLE_ADMIN"})
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void testHasAnyRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/secured/role"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }

    /**
     * 测试@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void testJsrRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jsr250/role"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }

    /**
     * 测试@PreAuthorize("hasRole('USER')")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void testPreAuthorize() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pre-post/role"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }


    /**
     * 测试@PostAuthorize("returnObject.name == authentication.name")
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser
    public void testPostAuthorize() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pre-post/post-authorize"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }
}
