package service;

import entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        warehouse.addProduct("Apple", Category.FOOD, 5);
        warehouse.addProduct("Banana", Category.FOOD, 2);
    }

    void setUpSingleProduct() {
        warehouse = new Warehouse();
        warehouse.addProduct("Apple", Category.FOOD, 5);
    }

    @Test
    @DisplayName("Adding a product to the warehouse")
    void testAddProduct() {
        setUpSingleProduct();

        assertThat(warehouse.products)
                .hasSize(1)
                .first()
                .matches(product -> product.name.equals("Apple"), "Product name is Apple")
                .matches(product -> product.category == Category.FOOD, "Product category is FOOD");
    }

    @Test
    @DisplayName("Adding a product with null or an empty name string should throw an exception")
    void testAddProductFailure() {

        assertThatThrownBy(() -> warehouse.addProduct(null, Category.FOOD, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name cannot be null or empty");

        assertThatThrownBy(() -> warehouse.addProduct("", Category.FOOD, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name cannot be null or empty");
    }

    @Test
    @DisplayName("Modifying a product in the warehouse")
    void testModifyProduct() {
        warehouse.modifyProduct(1, "Orange", Category.FOOD, 3);

        assertThat(warehouse.products)
                .hasSize(2)
                .extracting("name", "category", "rating")
                .contains(
                        tuple("Orange", Category.FOOD, 3),
                        tuple("Banana", Category.FOOD, 2)
                );
    }

    @Test
    @DisplayName("Get all products in the warehouse")
    void testGetAllProducts() {

        assertThat(warehouse.getAllProducts())
                .hasSize(2)
                .extracting("name", "category", "rating")
                .contains(
                        tuple("Apple", Category.FOOD, 5),
                        tuple("Banana", Category.FOOD, 2)
                );
    }

    @Test
    @DisplayName("Get a product by id")
    void testGetProductById() {

        assertThat(warehouse.getProductById(2))
                .isPresent()
                .get()
                .extracting("name", "category", "rating")
                .contains("Banana", Category.FOOD, 2);
    }

    @Test
    @DisplayName("Get products by category")
    void testGetProductsByCategory() {
        warehouse.addProduct("Shirt", Category.CLOTHES, 3);

        assertThat(warehouse.getProductsByCategory(Category.FOOD))
                .hasSize(2)
                .extracting("name", "category")
                .contains(
                        tuple("Apple", Category.FOOD),
                        tuple("Banana", Category.FOOD)
                );
    }

    @Test
    @DisplayName("Get all modified products")
    void testGetAllModifiedProducts() throws InterruptedException {
        Thread.sleep(100);
        warehouse.modifyProduct(1, "Orange", Category.FOOD, 3);

        warehouse.getAllProducts().forEach(System.out::println);
        assertThat(warehouse.getAllModifiedProducts())
                .hasSize(1)
                .extracting("name", "category", "rating")
                .contains(
                        tuple("Orange", Category.FOOD, 3)
                );
    }

    @Test
    @DisplayName("Get products manufactured after a certain date")
    void testGetProductsManufacturedAfterDate() {

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        assertThat(warehouse.getProductsManufacturedAfterDate(yesterday))
                .hasSize(2)
                .extracting("name", "category")
                .contains(
                        tuple("Apple", Category.FOOD),
                        tuple("Banana", Category.FOOD)
                );
    }
}

