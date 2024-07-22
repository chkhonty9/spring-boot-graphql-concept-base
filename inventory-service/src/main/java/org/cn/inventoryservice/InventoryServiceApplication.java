package org.cn.inventoryservice;

import org.cn.inventoryservice.entity.Category;
import org.cn.inventoryservice.entity.Product;
import org.cn.inventoryservice.repository.CategoryRepository;
import org.cn.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            List.of("Printer", "Phone", "Laptop").forEach(category -> {
                Category cat = Category.builder().name(category).build();
                categoryRepository.save(cat);
            });

            categoryRepository.findAll().forEach(category -> {
               for(int i = 0; i < 10; i++) {
                   Product product = Product.builder()
                           .id(UUID.randomUUID().toString())
                           .name(category.getName() + " " + i)
                           .price(Math.random() * 200)
                           .quantity((int) (500 * Math.random()))
                           .category(category)
                           .build();
                   productRepository.save(product);
               }
            });
        };
    }
}
