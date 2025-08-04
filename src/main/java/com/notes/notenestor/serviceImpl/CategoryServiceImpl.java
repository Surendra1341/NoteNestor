package com.notes.notenestor.serviceImpl;

import com.notes.notenestor.entity.Category;
import com.notes.notenestor.repository.CategoryRepo;
import com.notes.notenestor.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public Boolean saveCategory(Category category) {
        category.setIsDeleted(false);
      Category saveCategory =  categoryRepo.save(category);
        return !ObjectUtils.isEmpty(saveCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories;
    }


}
