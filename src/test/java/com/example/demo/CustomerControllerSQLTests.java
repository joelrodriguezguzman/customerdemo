package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createCar() throws Exception {
        String carJson = "{\"name\":\"Joel\",\"mname\":\"Yohalmo\", \"lname\":\"Rodriguez\", \"email\":\"jrodriguez@me.com\", \"phone\":7037172001}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carJson))
                .andReturn();

        int status = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();

        assertThat(status).isEqualTo(200);
        assertThat(content).contains("Joel", "Rodriguez", "7037172001");
    }

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void updateCustomer() throws Exception {
        String carJson = "{\"name\":\"Joel\",\"mname\":\"Yohalmo\", \"lname\":\"Rodriguez\", \"email\":\"jrodriguez@me.com\", \"phone\":7037172001}";
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carJson))
                .andExpect(status().isOk());
    }
    // @Test
    // void deleteCustomer() throws Exception {
    //     mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1")
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }
}
