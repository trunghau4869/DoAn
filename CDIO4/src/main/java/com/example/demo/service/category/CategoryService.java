package com.example.demo.service.category;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> findAll();

    void delete(int idCategory);

    void update(Category category);

    List<Category> findByName(String categoryName);

    void create(Category category);

    Category findById(Integer id);
}
