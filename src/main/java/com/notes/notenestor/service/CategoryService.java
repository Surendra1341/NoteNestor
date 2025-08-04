package com.notes.notenestor.service;

import com.notes.notenestor.dto.CategoryDto;
import com.notes.notenestor.dto.CategoryResponse;
import com.notes.notenestor.entity.Category;
import com.notes.notenestor.exception.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategories();


    List<CategoryResponse> getActiveCategories();

    CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;

    Boolean deleteCategoryById(Integer id);
}
