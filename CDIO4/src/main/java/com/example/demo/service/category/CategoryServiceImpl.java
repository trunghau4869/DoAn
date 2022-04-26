package com.example.demo.service.category;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public void delete(int idCategory) {
        categoryRepo.deleteById(idCategory);
    }
    @Override
    public void update(Category category) {
        categoryRepo.save(category);
    }
    @Override
    public List<Category> findByName(String categoryName) {
        return categoryRepo.findCategoryByCategoryName(categoryName);
    }

    @Override
    public void create(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepo.findById(id).orElse(null);
    }

}
