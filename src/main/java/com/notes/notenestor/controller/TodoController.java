package com.notes.notenestor.controller;

import com.notes.notenestor.dto.TodoDto;
import com.notes.notenestor.exception.ResourceNotFoundException;
import com.notes.notenestor.service.NotesService;
import com.notes.notenestor.service.TodoService;
import com.notes.notenestor.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name="Todo related",description = "All the todo APIs")
@RestController
@RequestMapping("api/v1/todo")
public class TodoController {


    @Autowired
    private NotesService notesService;
    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER','ADMIN')")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws ResourceNotFoundException {
        Boolean save = todoService.saveTodo(todo);
        if (save) {
            return CommonUtil.createBuildResponseMessage("Todo save success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ADMIN')")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws ResourceNotFoundException {
        TodoDto save = todoService.getTodoById(id);
        return CommonUtil.createBuildResponse(save, HttpStatus.OK);

    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER','ADMIN')")
    public ResponseEntity<?> getAllTodo() throws ResourceNotFoundException {
        List<TodoDto> save = todoService.getTodoByUser(CommonUtil.getLoggedInUser().getId());
        if (CollectionUtils.isEmpty(save)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(save, HttpStatus.OK);

    }
}
