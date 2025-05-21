package com.develop.prices;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class PurchaseControllerTestIT {

  @Autowired private MockMvc mockMvc;

  private String validPurchaseJson;
  private String invalidPurchaseJson;

  @BeforeEach
  void setUp() {

    validPurchaseJson =
        """
          {
          "customerId": 1
           }
        """;
    invalidPurchaseJson =
        """
          {
          "customerId": 99
           }
        """;
  }

  @Test
  public void getPurchasesWithFilters() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/purchases")).andDo(print());
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/purchases")
                .param("customerId", "1")
                .param("totalPriceMax", "100.00")
                .param("totalPriceMin", "0.00")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "purchaseId,asc"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.content", notNullValue()))
        .andExpect(jsonPath("$.content[0].purchaseId", notNullValue()))
        .andExpect(jsonPath("$.content[0].customer.customerId", is(1)))
        .andExpect(jsonPath("$.content[0].totalPrice", greaterThanOrEqualTo(7.90)))
        .andExpect(jsonPath("$.content.length()", greaterThanOrEqualTo(1)))
        .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
  }

  @Test
  public void getPurchasesWithOutFilters() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/purchases")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "purchaseId,asc"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.content.length()", greaterThanOrEqualTo(1)))
        .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
  }

  @Test
  public void getPurchasesWithInvalidFilters() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/purchases")
                .param("customerId", "999")
                .param("totalPriceMax", "100.00")
                .param("totalPriceMin", "0.00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty())
        .andExpect(jsonPath("$.totalElements").value(0));
  }

  @Test
  public void getPurchaseById_success() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/purchases/4"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.purchaseId", is(4)))
        .andExpect(jsonPath("$.customer.customerId", is(4)));
  }

  @Test
  public void getPurchaseById_notFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/purchases/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void postPurchase_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/purchases")
                .content(validPurchaseJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.purchaseId", notNullValue()))
        .andExpect(jsonPath("$.customer.customerId", is(1)))
        .andExpect(jsonPath("$.totalPrice", greaterThanOrEqualTo(0)))
        .andExpect(jsonPath("$.shopping").value(true));
  }

  @Test
  public void postPurchase_invalidCustomerId() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/purchases")
                .content(invalidPurchaseJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void addProductPurchase_success() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post("/purchases/1/productInShop/1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.purchaseId", notNullValue()))
        .andExpect(jsonPath("$.customer.customerId", is(1)))
        .andExpect(jsonPath("$.totalPrice", greaterThanOrEqualTo(7.90)))
        .andExpect(jsonPath("$.shopping").value(true));
  }

  @Test
  public void addProductPurchase_invalidPurchaseId() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/purchases/999/productInShop/4"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void addProductPurchase_invalidProductInShopId() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/purchases/4/productInShop/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void patchFinishPurchase_success() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.patch("/purchases/4/finish"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.purchaseId", notNullValue()))
        .andExpect(jsonPath("$.customer.customerId", is(4)))
        .andExpect(jsonPath("$.shopping").value(false));
  }

  @Test
  public void patchFinishPurchase_invalidPurchaseId() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.patch("/purchases/9/finish"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deletePurchase_success() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/purchases/4"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  public void deletePurchase_invalidPurchaseId() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/purchases/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deletePurchase_productInPurchase() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/purchases/1/productInShop/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void deletePurchase_productoInPurchase_invalidPurchaseId() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/purchases/999/productInShop/4"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deletePurchase_productoInPurchase_invalidProductInShopId() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/purchases/4/productInShop/999"))
        .andExpect(status().isNotFound());
  }
}
