package com.develop.prices.rest;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ShopControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    private String validShopCreationJson,
            invalidShopCreationJson,
            validUpdateShopJson,
            invalidUpdateShopJson,
            validPartialUpdateShopJson,
            invalidPartialUpdateShopJson;

    @BeforeEach
    public void init() {
        validShopCreationJson = """
                    {
                    "country": "Argentina",
                    "city": "Buenos Aires",
                    "address": "Alfredo R. Bufano 2701-2799"
                }
                """;

        invalidShopCreationJson = """
                {
                    "country": ,
                    "city": ,
                    "address":
                }""";

        validUpdateShopJson = """
                {
                    "country": "País Nuevo",
                    "city": "Ciudad Nueva",
                    "address": "Dirección Nueva"
                }
                """;

        invalidUpdateShopJson = """
                {
                    "country": ,
                    "city": ,
                    "address":
                }""";

        validPartialUpdateShopJson = """
                {
                "city": "Ciudad Nueva"
                }""";

        invalidPartialUpdateShopJson = """
                {
                "city":
                }""";

    }

//    @Test
//    public void getShopLocationWithFilters_ReturnsMatchingShops() throws Exception {
//        mockMvc.perform(get("/shops")
//                .param("country", "España")
//                .param("city", "Madrid"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(1)))
//                .andExpect(jsonPath("$.content[0].city", is("Madrid")))
//    }

    @Test
    public void getShopById() throws Exception {
        String shopId = "1";

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/shops/" + shopId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopId", is(1)))
                .andExpect(jsonPath("$.country", is("Argentina")))
                .andExpect(jsonPath("$.city", is("Buenos Aires")))
                .andExpect(jsonPath("$.address", is("Alfredo R. Bufano 2701-2799")));

    }

    @Test
    public void createShop() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/shops")
                                .content(validShopCreationJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.shopId", notNullValue()))
                .andExpect(jsonPath("$.country", is("Argentina")))
                .andExpect(jsonPath("$.city", is("Buenos Aires")))
                .andExpect(jsonPath("$.address", is("Alfredo R. Bufano 2701-2799")));
    }

    @Test
    public void createShop_error() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/shops")
                                .content(invalidShopCreationJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateShop() throws Exception {
        String shopId = "1";

        mockMvc.perform(
                MockMvcRequestBuilders.put("/shops/" + shopId)
                        .content(validUpdateShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.shopId", notNullValue()))
                .andExpect(jsonPath("$.country", is("País Nuevo")))
                .andExpect(jsonPath("$.city", is("Ciudad Nueva")))
                .andExpect(jsonPath("$.address", is("Dirección Nueva")));
    }

    @Test
    public void updateShop_error() throws Exception{
        String shopId = "1";

        mockMvc.perform(
                MockMvcRequestBuilders.put("/shops/" + shopId)
                        .content(invalidUpdateShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void partialUpdateShop() throws Exception {
        String shopId = "1";

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/shops/" + shopId)
                        .content(validPartialUpdateShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.shopId", notNullValue()))
                .andExpect(jsonPath("$.city", is("Ciudad Nueva")));
    }

    @Test
    public void partialUpdateShop_error() throws Exception{
        String shopId = "1";

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/shops/" + shopId)
                        .content(invalidPartialUpdateShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteShop() throws Exception {
        String shopId = "1";

        mockMvc.perform(MockMvcRequestBuilders.delete("/shops/" + shopId))
                .andExpect(status().isOk());
    }

    public void deleteShop_error() throws Exception {
        String shopId = "99";

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/" + shopId))
                .andExpect(status().isNotFound());
    }



}
