package service;

import entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Warehouse {
    public ArrayList<Product> products;

    public Warehouse() {
        products = new ArrayList<>();
    }

    public void addProduct(String name, Category category, int rating) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        Product product = new Product(products.size() + 1, name, category, rating);
        products.add(product);
    }

    public void modifyProduct(int id, String newName, Category newCategory, int newRating) {
        // Modify an existing product in the warehouse
        products.stream().filter(product -> product.id == id).findFirst().ifPresent(product -> {
            product.name = newName;
            product.category = newCategory;
            product.rating = newRating;
            product.setLastModifiedDate(LocalDateTime.now());
        });
    }

    public List<Product.ProductRecord> getAllProducts() {
        return products.stream().map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate)).collect(Collectors.toList());
    }

    public Optional<Product.ProductRecord> getProductById(int id) {
        return products.stream()
                .filter(product -> product.id == id)
                .map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate))
                .findFirst();
    }



    public List<Product.ProductRecord> getProductsByCategory(Category category) {
        return products.stream().filter(product -> product.category == category).sorted(Comparator.comparing(p -> p.name)).map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate)).collect(Collectors.toList());
    }

    public List<Product.ProductRecord> getProductsManufacturedAfterDate(LocalDateTime date) {
        return products.stream().filter(product -> product.manufactureDate.isAfter(date)).sorted(Comparator.comparing((Product product) -> product.manufactureDate).reversed()).map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate)).collect(Collectors.toList());
    }

    public List<Product.ProductRecord> getAllModifiedProducts() {
        return products.stream().filter(product -> product.manufactureDate.isBefore(product.lastModifiedDate)).map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct("Apple", Category.FOOD, 5);
        warehouse.addProduct("Banana", Category.FOOD, 2);
        Optional<Product.ProductRecord> product = warehouse.getProductById(2);
        product.ifPresentOrElse(
                p -> System.out.println("Product found: " + p),
                () -> System.out.println("Product not found")
        );
    }
}
