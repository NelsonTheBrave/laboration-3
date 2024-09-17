package service;

import entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {
    public ArrayList<Product> products;

    public Warehouse() {
        products = new ArrayList<>();
    }

    public void addProduct(String name, Category category, int rating) {
        // Add a new product to the warehouse
        Product product = new Product(products.size() + 1, name, category, rating);
        products.add(product);
    }

    public void printProducts() {
        // Print all products in the warehouse
        for (Product product : products) {
            System.out.println("Id: " + product.id + ", Product: " + product.name + ", Category: " + product.category + ", Rating: " + product.rating + ", Manufactured: " + product.manufactureDate + ", Last Modified: " + product.lastModifiedDate);
        }
    }

    public void modifyProduct(int id, String newName, Category newCategory, int newRating) {
        // Modify an existing product in the warehouse
        products.stream()
                .filter(product -> product.id == id)
                .findFirst()
                .ifPresent(product -> {
                    product.name = newName;
                    product.category = newCategory;
                    product.rating = newRating;
                    product.setLastModifiedDate(LocalDateTime.now());
                });
    }

    public List<Product.ProductRecord> getAllProducts() {
        return products.stream()
                .map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate))
                .collect(Collectors.toList());
    }

    public Product.ProductRecord getProductById(int id) {
        return products.stream()
                .filter(product -> product.id == id)
                .map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate))
                .findFirst()
                .orElse(null);
    }

    public List<Product.ProductRecord> getProductsByCategory(Category category) {
        return products.stream()
                .filter(product -> product.category == category)
                .sorted(Comparator.comparing(p -> p.name))
                .map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate))
                .collect(Collectors.toList());
    }

    public List<Product.ProductRecord> getProductsManufacturedAfterDate(LocalDateTime date) {
        return products.stream()
                .filter(product -> product.manufactureDate.isAfter(date))
                .sorted(Comparator.comparing((Product product) -> product.manufactureDate).reversed())
                .map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate))
                .collect(Collectors.toList());
    }

    public List<Product.ProductRecord> getAllModifiedProducts() {
        return products.stream()
                .filter(product -> product.manufactureDate.isBefore(product.lastModifiedDate))
                .map(product -> new Product.ProductRecord(product.id, product.name, product.category, product.rating, product.manufactureDate, product.lastModifiedDate))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println("Hello There!");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct("Apple", Category.FOOD, 5);
        warehouse.addProduct("Banana", Category.FOOD, 2);
        warehouse.addProduct("Shirt", Category.CLOTHES, 3);
        warehouse.addProduct("Sweater", Category.CLOTHES, 3);
        warehouse.addProduct("Blouse", Category.CLOTHES, 3);

        warehouse.modifyProduct(2, "Orange", Category.FOOD, 4);
        warehouse.modifyProduct(5, "Vodka", Category.DRINK, 1);

        warehouse.printProducts();

//        warehouse.getAllProducts().forEach(System.out::println);

//        System.out.println(warehouse.getProductById(3));

//        warehouse.getProductsByCategory(Category.CLOTHES).forEach(System.out::println);
        
//        warehouse.getProductsManufacturedAfterDate(LocalDateTime.now().minusDays(1)).forEach(System.out::println);
        warehouse.getAllModifiedProducts().forEach(System.out::println);
    }
}
