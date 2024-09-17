package entities;

import java.time.LocalDateTime;

public class Product {
    public int id;
    public String name;
    public Category category;
    public int rating;
    public final LocalDateTime manufactureDate;
    LocalDateTime lastModifiedDate;

    public Product(int id, String name, Category category, int rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        manufactureDate = LocalDateTime.now();
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
