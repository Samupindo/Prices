package com.develop.prices.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductModelTest {

    private ProductModel productModel;
    private final Integer TEST_ID = 1;
    private final String TEST_NAME = "Test Product";

    @Mock
    private List<ProductInShopModel> mockPrices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializa el objeto para cada test
        productModel = new ProductModel();
        productModel.setProductId(TEST_ID);
        productModel.setName(TEST_NAME);

        // Configura la lista mockeada de precios
        ReflectionTestUtils.setField(productModel, "prices", mockPrices);
    }

    @Test
    @DisplayName("Constructor vacío crea una instancia correctamente")
    void testDefaultConstructor() {
        // Act
        ProductModel product = new ProductModel();

        // Assert
        assertNotNull(product, "El constructor debería crear una instancia no nula");
    }

    @Test
    @DisplayName("Constructor con nombre crea una instancia correctamente")
    void testNamedConstructor() {
        // Act
        ProductModel product = new ProductModel(TEST_NAME);

        // Assert
        assertNotNull(product, "El constructor debería crear una instancia no nula");
        assertEquals(TEST_NAME, product.getName(), "El nombre debería establecerse correctamente");
    }

    @Test
    @DisplayName("getPrices() debe devolver la lista de precios correctamente")
    void getPrices() {
        // Act
        List<ProductInShopModel> result = productModel.getPrices();

        // Assert
        assertSame(mockPrices, result, "Debería devolver la misma lista de precios que se estableció");
    }

    @Test
    @DisplayName("getProductId() debe devolver el ID del producto correctamente")
    void getProductId() {
        // Act
        Integer result = productModel.getProductId();

        // Assert
        assertEquals(TEST_ID, result, "Debería devolver el ID que se estableció");
    }

    @Test
    @DisplayName("setProductId() debe establecer el ID del producto correctamente")
    void setProductId() {
        // Arrange
        Integer newId = 2;

        // Act
        productModel.setProductId(newId);

        // Assert
        assertEquals(newId, productModel.getProductId(), "El ID debería actualizarse al nuevo valor");
    }

    @Test
    @DisplayName("getName() debe devolver el nombre del producto correctamente")
    void getName() {
        // Act
        String result = productModel.getName();

        // Assert
        assertEquals(TEST_NAME, result, "Debería devolver el nombre que se estableció");
    }

    @Test
    @DisplayName("setName() debe establecer el nombre del producto correctamente")
    void setName() {
        // Arrange
        String newName = "Nuevo nombre del producto";

        // Act
        productModel.setName(newName);

        // Assert
        assertEquals(newName, productModel.getName(), "El nombre debería actualizarse al nuevo valor");
    }

    @Test
    @DisplayName("toString() debe devolver una representación string que incluya ID y nombre")
    void testToString() {
        // Act
        String result = productModel.toString();

        // Assert
        assertNotNull(result, "toString() no debería devolver null");
        assertTrue(result.contains(TEST_ID.toString()), "toString() debería contener el ID del producto");
        assertTrue(result.contains(TEST_NAME), "toString() debería contener el nombre del producto");
        assertTrue(result.contains("ProductModel"), "toString() debería contener el nombre de la clase");
    }

    @Test
    @DisplayName("Relación bidireccional con ProductInShopModel funciona correctamente")
    void testBidirectionalRelationship() {
        // Esta prueba requiere una instancia real de ProductInShopModel

        // Arrange
        ProductModel product = new ProductModel("Test Product");
        product.setProductId(1);

        List<ProductInShopModel> pricesList = new ArrayList<>();
        ProductInShopModel priceItem = new ProductInShopModel();
        priceItem.setProduct(product);
        priceItem.setPrice(new BigDecimal("10.5"));
        pricesList.add(priceItem);

        ReflectionTestUtils.setField(product, "prices", pricesList);

        // Act
        List<ProductInShopModel> retrievedPrices = product.getPrices();

        // Assert
        assertNotNull(retrievedPrices, "La lista de precios no debería ser null");
        assertEquals(1, retrievedPrices.size(), "Debería haber un elemento en la lista");
        assertSame(product, retrievedPrices.get(0).getProduct(), "La relación bidireccional debería mantenerse");
        assertEquals(new BigDecimal("10.5"), retrievedPrices.get(0).getPrice(), "El precio debería ser correcto");
    }
}