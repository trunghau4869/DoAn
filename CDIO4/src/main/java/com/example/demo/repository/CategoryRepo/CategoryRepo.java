package com.example.demo.repository.CategoryRepo;


import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    List<Category> findAll();

    List<Category> findCategoryByCategoryName (String tendanhmuc);


}
