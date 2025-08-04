package com.notes.notenestor.controller;

import com.notes.notenestor.entity.Category;
import com.notes.notenestor.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;




    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        Boolean result = categoryService.saveCategory(category);
        if (result) {
            return new ResponseEntity<>("Saved successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Save failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory() {
        List<Category> allCategory = categoryService.getAllCategories();

        if(CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }



}
