import static org.hamcrest.Matchers.*;
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

@SpringBootTest(classes = com.develop.prices.MicPricesApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class CustomerControllerTestIT {

  @Autowired private MockMvc mockMvc;

  private Integer validId;
  private Integer invalidId;
  private String validCustomerJson;
  private String invalidCustomerJson;
  private String updateCustomerJson;
  private String InvalidpartialUpdateCustomerJson;

  @BeforeEach
  void setUp() {
    validId = 1;
    invalidId = 999;

    validCustomerJson =
"""
  {
  "name": "Jorgillo",
  "email": "jorgillo@correo.com",
  "phone": "123456789"
   }
""";

    invalidCustomerJson =
"""
{
"name": 123,
  "email": "jorgillo@correo.com",
  "phone": "123456789"
}
""";

    updateCustomerJson =
        """
          {
          "name": "Jorgo",
          "email": "jorgollo@correo.com",
          "phone": "122456789"
           }
        """;
    InvalidpartialUpdateCustomerJson =
        """
          {
          "name": ""
           }
        """;
  }

  @Test
  public void getCustomersWithFilters() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/customers")
                .param("name", "Alice")
                .param("phone", "123123123")
                .param("email", "alice@example.com")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "customerId,asc"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.content", notNullValue()))
        .andExpect(jsonPath("$.content[0].customerId", is(1)))
        .andExpect(jsonPath("$.content[0].name", is("Alice Johnson")))
        .andExpect(jsonPath("$.content[0].phone", is(123123123)))
        .andExpect(jsonPath("$.content[0].email", is("alice@example.com")))
        .andExpect(jsonPath("$.content.length()").value(1))
        .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
  }

  @Test
  public void getCustomersWithOutFilters() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/customers")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "customerId,asc"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(4))
        .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
  }

  @Test
  public void getCustomerWithInvalidFilters() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/customers")
                .param("name", "Ejemplo")
                .param("phone", "100100100")
                .param("email", "ejemplo@correo.com")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "customerId,asc"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty())
        .andExpect(jsonPath("$.totalElements").value(0));
  }

  @Test
  public void getCustomerById_success() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/customers/" + validId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerId", is(1)))
        .andExpect(jsonPath("$.name", is("Alice Johnson")));
  }

  @Test
  public void getCustomerById_notFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/customers/" + invalidId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void postCustomer_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/customers")
                .content(validCustomerJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.customerId", notNullValue()))
        .andExpect(jsonPath("$.name", is("Jorgillo")))
        .andExpect(jsonPath("$.email", is("jorgillo@correo.com")))
        .andExpect(jsonPath("$.phone", is(123456789)));
  }

  @Test
  public void postCustomer_invalidName() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/customers")
                .content(invalidCustomerJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void putCustomer_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/customers/" + validId)
                .content(updateCustomerJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.customerId", notNullValue()))
        .andExpect(jsonPath("$.name", is("Jorgo")))
        .andExpect(jsonPath("$.email", is("jorgollo@correo.com")))
        .andExpect(jsonPath("$.phone", is(122456789)));
  }

  @Test
  public void putCustomer_invalidName() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/customers/" + validId)
                .content(invalidCustomerJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void putCustomer_notFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/customers/" + invalidId)
                .content(updateCustomerJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void patchCustomer_success() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/customers/" + validId)
                .content(updateCustomerJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerId", notNullValue()))
        .andExpect(jsonPath("$.name", is("Jorgo")));
  }

  @Test
  public void patchCustomer_invalidName() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/customers/" + validId)
                .content(InvalidpartialUpdateCustomerJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void deleteCustomer_success() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/customers/" + validId))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteCustomer_notFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/customers/" + invalidId))
        .andExpect(status().isNotFound());
  }
}
