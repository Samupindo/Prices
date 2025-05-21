package com.develop.prices;

import static org.hamcrest.Matchers.*;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTestIT {

  @Autowired private MockMvc mockMvc;


  @Test
  public void getProductsWithFilters_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/products")
                .param("name", "Zumos")
                .param("priceMin", "1.00")
                .param("priceMax", "10.00")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "productId,asc"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.content", notNullValue()))
        .andExpect(jsonPath("$.content[0].productId", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("Zumos")))
        .andExpect(jsonPath("$.content.length()").value(1))
        .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
  }

  @Test
  public void getProductsWithOutFilters() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/products")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "productId,asc")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.content.length()").value(5))
        .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
  }

  @Test
  public void getProductsWithInvalidFilters() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/products")
                .param("name", "Ejemplo")
                .param("priceMin", "100.00")
                .param("priceMax", "1.00")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "productId,asc"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty())
        .andExpect(jsonPath("$.totalElements").value(0));
  }

  @Test
  public void getProductById_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/products/1")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.productId", is(1)))
        .andExpect(jsonPath("$.name", is("Zumos")));
  }

  @Test
  public void getProductById_notFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/products/999")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void postProduct_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"paco\"}"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated());
  }

  @Test
  public void postProduct_invalidName() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void putProduct_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"paco\"}"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  public void putProduct_invalidName() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void putProduct_notFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"paco\"}"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteProduct_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/products/1")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteProduct_notFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/products/999"))
        .andExpect(status().isNotFound());
  }
}
