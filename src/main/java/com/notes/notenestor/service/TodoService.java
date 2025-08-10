package com.notes.notenestor.service;

import com.notes.notenestor.dto.TodoDto;
import com.notes.notenestor.exception.ResourceNotFoundException;

import java.util.List;

public interface TodoService {

    Boolean saveTodo(TodoDto todo) throws ResourceNotFoundException;

    TodoDto getTodoById(Integer id) throws ResourceNotFoundException;

    List<TodoDto> getTodoByUser(Integer id);
}
