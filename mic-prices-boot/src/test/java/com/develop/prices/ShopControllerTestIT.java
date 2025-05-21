package com.develop.prices;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ShopControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    private String validShopCreationJson,
            invalidShopCreationJson,
            validAddProductToShopJson,
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

        validAddProductToShopJson = """
                {
                    "price": 10
                }
                """;

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

    @Test
    public void getShopLocationWithFiltersCountry() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/shops")
                                .param("country", "es"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].country", is("España")))
                .andExpect(jsonPath("$.content[0].city", is("A Coruña")))
                .andExpect(jsonPath("$.content[0].address", is("Rúa Río Brexa, 5 ")))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.totalPages", greaterThanOrEqualTo(1)));

    }

    @Test
    public void getShopLocationWithFiltersCity() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/shops")
                                .param("city", "A Coruña"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].country", is("España")))
                .andExpect(jsonPath("$.content[0].city", is("A Coruña")))
                .andExpect(jsonPath("$.content[0].address", is("Rúa Río Brexa, 5 ")))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.totalPages", greaterThanOrEqualTo(1)));

    }

    @Test
    public void getShopLocationWithFiltersAddress() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/shops")
                                .param("address", "Rúa Río Brexa, 5"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].country", is("España")))
                .andExpect(jsonPath("$.content[0].city", is("A Coruña")))
                .andExpect(jsonPath("$.content[0].address", is("Rúa Río Brexa, 5 ")))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.totalPages", greaterThanOrEqualTo(1)));

    }

    @Test
    public void getShopById() throws Exception {
        String shopId = "2";

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/shops/" + shopId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopId", is(2)))
                .andExpect(jsonPath("$.country", is("Argentina")))
                .andExpect(jsonPath("$.city", is("Córdoba")))
                .andExpect(jsonPath("$.address", is("Av. Vélez Sarsfield")));

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
    public void addProductToShop() throws Exception {
        String shopId = "4";
        String productId = "1";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/shops/" + shopId + "/products/" + productId)
                        .content(validAddProductToShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productInShopId", notNullValue()))
                .andExpect(jsonPath("$.shopId", is(4)))
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.price", is(10)));

    }

    @Test
    public void addProductToShop_error() throws Exception{
        String shopId = "1";
        String productId = "1000";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/shops/" + shopId + "/products/" + productId)
                        .content(validAddProductToShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateShop() throws Exception {
        String shopId = "2";

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
        String shopId = "2";

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
    public void updateProductInShop() throws Exception {
        String shopId = "3";
        String productId = "3";

        mockMvc.perform(MockMvcRequestBuilders.patch("/shops/" + shopId + "/products/" + productId)
                        .content(validAddProductToShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.productInShopId", notNullValue()))
                .andExpect(jsonPath("$.shopId", is(3)))
                .andExpect(jsonPath("$.productId", is(3)))
                .andExpect(jsonPath("$.price", is(10)));
    }

    @Test
    public void updateProductInShop_error() throws Exception{
        String shopId = "3";
        String productId = "1000";

        mockMvc.perform(MockMvcRequestBuilders.patch("/shops/" + shopId + "/products/" + productId)
                        .content(validAddProductToShopJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void deleteShop() throws Exception {
        String shopId = "2";

        mockMvc.perform(MockMvcRequestBuilders.delete("/shops/" + shopId))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShop_error() throws Exception {
        String shopId = "1000";

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/" + shopId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteProductInShop() throws Exception {
        String shopId = "3";
        String productId = "3";

        mockMvc.perform(MockMvcRequestBuilders.delete("/shops/" + shopId + "/products/" + productId))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductInShop_error() throws Exception {
        String shopId = "1";
        String productId = "1000";

        mockMvc.perform(MockMvcRequestBuilders.delete("/shops/" + shopId + "/products/" + productId))
                .andExpect(status().isNotFound());
    }

}
