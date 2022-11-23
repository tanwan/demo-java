package com.lzy.demo.security;


import com.lzy.demo.security.config.SecurityConfig;
import com.lzy.demo.security.controller.SecurityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(SecurityController.class)
@Import(SecurityConfig.class)
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试hasRole
     *
     * @throws Exception exception
     * @see WithMockUser
     */
    @Test
    @WithMockUser
    public void testHasRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/security/user"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }

    /**
     * 测试hasRole
     *
     * @throws Exception exception
     * @see WithMockUser
     */
    @Test
    @WithMockUser(roles = "wrong role")
    public void testHasNoRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/security/user"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andReturn();
    }


    /**
     * 测试hasAuthority
     *
     * @throws Exception exception
     * @see WithMockUser
     */
    @Test
    @WithMockUser
    public void testHasAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/security/authority"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }

    /**
     * 测试hasAuthority
     *
     * @throws Exception exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testHasAnyRole() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/security/any-role"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }


    /**
     * 测试hasIpAddress
     *
     * @throws Exception exception
     */
    @Test
    public void testHasIpAddress() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/security/ip")
                        .with(r -> {
                            r.setRemoteAddr("192.168.0.1");
                            return r;
                        }))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andReturn();
    }


    /**
     * 测试PermitAll
     *
     * @throws Exception exception
     */
    @Test
    public void testPermitAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/security/permit-all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }
}
