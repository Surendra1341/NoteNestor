package com.notes.notenestor.controller;

import com.notes.notenestor.dto.CategoryDto;
import com.notes.notenestor.dto.CategoryResponse;
import com.notes.notenestor.entity.Category;
import com.notes.notenestor.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;




    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        Boolean result = categoryService.saveCategory(categoryDto);
        if (result) {
            return new ResponseEntity<>("Saved successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Save failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory() {


        List<CategoryDto> allCategory = categoryService.getAllCategories();

        if(CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> allCategory = categoryService.getActiveCategories();

        if(CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
       CategoryDto categoryDto=  categoryService.getCategoryById(id);

        if(ObjectUtils.isEmpty(categoryDto)) {
            return new ResponseEntity<>("Category not found with id: "+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        Boolean deleted=  categoryService.deleteCategoryById(id);

        if(deleted) {
            return new ResponseEntity<>("Category deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("wrong id: "+id, HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
