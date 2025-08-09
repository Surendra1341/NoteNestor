package com.notes.notenestor.util;


import com.notes.notenestor.dto.CategoryDto;
import com.notes.notenestor.dto.TodoDto;
import com.notes.notenestor.dto.UserDto;
import com.notes.notenestor.enums.TodoStatus;
import com.notes.notenestor.exception.ExistDataException;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.exception.ValidationException;
import com.notes.notenestor.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class Validation {

    @Autowired
    private UserRepo userRepo;


    public void categoryValidation(CategoryDto categoryDto) {

        Map<String, Object> errors = new HashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("category obj / JSON shouldn't be null / empty");
        } else {

            // name ke lye
            if (ObjectUtils.isEmpty(categoryDto.getName())) {
                errors.put("name", "Name field is  null / empty");
            } else {

                if (categoryDto.getName().length() > 20) {
                    errors.put("name", "Name field is longer than 20 characters");
                }
            }

            // description ke lye
            if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
                errors.put("description", "description field is  null / empty");
            } else {

                if (categoryDto.getDescription().length() > 100) {
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

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

    }


    public void todoValidation(TodoDto todo) throws ResourceNotFoundException {

        TodoDto.StatusDto reqStatus = todo.getStatus();

        Boolean statusFound = false;
        for (TodoStatus status : TodoStatus.values()) {
            if (status.getId().equals(reqStatus.getId())) {
                statusFound = true;

                break;
            }
        }

        if (!statusFound) {
            throw new ResourceNotFoundException("invalid status value");
        }
    }


    public void userValidation(UserDto userDto) throws ResourceNotFoundException {


        //name
        if (!StringUtils.hasText(userDto.getName())) {
            throw new IllegalArgumentException("name field is  null / empty  -> invalid");
        }

        //email  and
        if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)) {
            throw new IllegalArgumentException("email field invalid");
        }else{
            // already email exist
            Boolean exists = userRepo.existsByEmail(userDto.getEmail());
            if(exists){
                throw new ExistDataException("email already exists");
            }
        }





    }
}
