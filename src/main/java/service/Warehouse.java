package service;

import entities.Category;
import entities.Product;

import java.util.ArrayList;

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
            System.out.println("Id: " + product.id + ", Product: " + product.name + ", Category: " + product.category + ", Rating: " + product.rating + ", Manufactured: " + product.manufactureDate);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello There!");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct("Apple", Category.FOOD, 5);
        warehouse.addProduct("Banana", Category.FOOD, 2);
        warehouse.addProduct("Shirt", Category.CLOTHES, 3);
        warehouse.printProducts();
    }
}
