package com.notes.notenestor.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.notenestor.dto.TodoDto;
import com.notes.notenestor.entity.Todo;
import com.notes.notenestor.enums.TodoStatus;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.repository.TodoRepo;
import com.notes.notenestor.service.TodoService;
import com.notes.notenestor.util.Validation;
import jdk.jshell.Snippet;
import jdk.jshell.StatementSnippet;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;


    @Override
    public Boolean saveTodo(TodoDto todoDto) throws ResourceNotFoundException {

        //validate todo status
        validation.todoValidation(todoDto);


      Todo todo=    mapper.map(todoDto,Todo.class);
      todo.setStatusId(todoDto.getStatus().getId());

      Todo save = todoRepo.save(todo);

      return !ObjectUtils.isEmpty(save);

    }

    @Override
    public TodoDto getTodoById(Integer id) throws ResourceNotFoundException {
        Todo todo =todoRepo.findById(id).
                orElseThrow(()->new ResourceNotFoundException("todo not found and id invalid"));
       TodoDto todoDto = mapper.map(todo,TodoDto.class);
       setStatus(todoDto,todo);
       return todoDto;
    }

    private void setStatus(TodoDto todoDto,Todo todo) {
        for(TodoStatus status: TodoStatus.values()){
            if(status.getId().equals(todo.getStatusId())){
                TodoDto.StatusDto statusDto= TodoDto.StatusDto.builder()
                        .id(status.getId())
                        .name(status.getName())
                        .build();
                todoDto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser(Integer id) {
        Integer userId = id;

      return  todoRepo.findByCreatedBy(userId)
              .stream()
              .map(td->mapper.map(td,TodoDto.class))
              .toList();
    }
}
