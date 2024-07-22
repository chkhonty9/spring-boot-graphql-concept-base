package org.cn.inventoryservice.web;

import lombok.AllArgsConstructor;
import org.cn.inventoryservice.dto.ProductRequestDTO;
import org.cn.inventoryservice.entity.Product;
import org.cn.inventoryservice.repository.CategoryRepository;
import org.cn.inventoryservice.repository.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ProductGraphQLController {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @QueryMapping
    public List<Product> products() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product getProduct(@Argument String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("product %s not found", id))
        );
    }

    @MutationMapping
    public Product saveProduct(@Argument ProductRequestDTO product) {
        Product p = new Product();
        p.setId(UUID.randomUUID().toString());
        p.setName(product.name());
        p.setPrice(product.price());
        p.setQuantity(product.quantity());
        p.setCategory(categoryRepository.findById(product.categoryId()).orElseThrow(
                () -> new RuntimeException(String.format("product %s not found", product.categoryId()))
        ));
        return productRepository.save(p);
    }

    @MutationMapping
    public Product updateProduct(@Argument String id,@Argument ProductRequestDTO product) {
        Product p = new Product();
        p.setId(id);
        p.setCategory(categoryRepository.findById(product.categoryId()).orElseThrow(
                () -> new RuntimeException(String.format("product %s not found", product.categoryId()))
        ));
        p.setName(product.name());
        p.setPrice(product.price());
        p.setQuantity(product.quantity());
        return productRepository.save(p);
    }

    @MutationMapping
    public void deleteProduct(@Argument String id) {
        productRepository.deleteById(id);
    }
}
