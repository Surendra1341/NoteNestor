package com.notes.notenestor.controller;

import com.notes.notenestor.dto.TodoDto;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.service.NotesService;
import com.notes.notenestor.service.TodoService;
import com.notes.notenestor.util.CommonUtil;
import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {


    @Autowired
    private NotesService notesService;
    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws ResourceNotFoundException {
      Boolean save =  todoService.saveTodo(todo);
      if(save){
          return CommonUtil.createBuildResponseMessage("Todo save success", HttpStatus.CREATED);
      }
      return CommonUtil.createErrorResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("{id}")
    public ResponseEntity<?> saveTodo(@PathVariable Integer id) throws ResourceNotFoundException {
        TodoDto save =  todoService.getTodoById(id);
        return CommonUtil.createBuildResponse(save, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<?> getAllTodo() throws ResourceNotFoundException {
        List<TodoDto>  save =  todoService.getTodoByUser(1);
        if(CollectionUtils.isEmpty(save)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(save, HttpStatus.OK);

    }
}
