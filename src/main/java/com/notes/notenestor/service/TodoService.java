package com.notes.notenestor.service;

import com.notes.notenestor.dto.TodoDto;
import com.notes.notenestor.exception.ResourceNotFoundException;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface TodoService {

    public Boolean saveTodo(TodoDto todo) throws ResourceNotFoundException;

    public TodoDto getTodoById(Integer id) throws ResourceNotFoundException;

    public List<TodoDto> getTodoByUser(Integer id);
}
