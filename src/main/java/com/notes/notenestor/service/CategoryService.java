package com.notes.notenestor.service;

import com.notes.notenestor.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(Category category);

    public List<Category> getAllCategories();


}
