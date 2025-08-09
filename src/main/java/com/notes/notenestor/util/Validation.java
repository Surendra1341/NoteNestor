package com.notes.notenestor.util;


import com.notes.notenestor.dto.CategoryDto;
import com.notes.notenestor.dto.TodoDto;
import com.notes.notenestor.enums.TodoStatus;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;

@Component
public class Validation {


    public void categoryValidation(CategoryDto categoryDto) {

        Map<String, Object> errors = new HashMap<>();

        if(ObjectUtils.isEmpty(categoryDto)) {
            throw new  IllegalArgumentException("category obj / JSON shouldn't be null / empty");
        }else{

            // name ke lye
            if(ObjectUtils.isEmpty(categoryDto.getName())) {
                errors.put("name", "Name field is  null / empty");
            }else {

                if(categoryDto.getName().length() > 20) {
                    errors.put("name", "Name field is longer than 20 characters");
                }
            }

            // description ke lye
            if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
                errors.put("description", "description field is  null / empty");
            }else {

                if(categoryDto.getDescription().length() > 100) {
                    errors.put("description", "description field is longer than 100 characters");
                }
            }

            // Validate isActive
            if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
                errors.put("isActive", "isActive field is null or empty");
            } else if (!(categoryDto.getIsActive() instanceof Boolean)) {
                errors.put("isActive", "Invalid isActive value, must be true or false");
            }

        }

        if(!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

    }


    public void todoValidation(TodoDto todo) throws ResourceNotFoundException {

        TodoDto.StatusDto reqStatus =todo.getStatus();

        Boolean statusFound = false;
        for (TodoStatus status :TodoStatus.values() ){
            if(status.getId().equals(reqStatus.getId())){
                statusFound = true;

            }
        }

        if(!statusFound){
            throw new ResourceNotFoundException("invalid status value");
        }
    }
}
