package org.cn.inventoryservice.web;

import lombok.AllArgsConstructor;
import org.cn.inventoryservice.entity.Category;
import org.cn.inventoryservice.repository.CategoryRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class CategoryGraphQLController {
    private CategoryRepository CategoryRepository;

    @QueryMapping
    public List<Category> categories() {
        return this.CategoryRepository.findAll();
    }

    @QueryMapping
    public Category getCategory(@Argument Long id){
        return this.CategoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("product %s not found", id))
        );
    }

    @MutationMapping
    public Category saveCategory(@Argument Category category){
        return this.CategoryRepository.save(category);
    }

}
