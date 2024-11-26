package com.example.todoapi.controller.task;

import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.PageDTO;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskForm;
import com.example.todoapi.model.TaskListDTO;
import com.example.todoapi.service.task.TaskEntity;
import com.example.todoapi.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskId) {
        TaskEntity entity = taskService.find(taskId);

        TaskDTO dto = toTaskDTO(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm form) {
        var entity = taskService.create(form.getTitle());
        var dto = toTaskDTO(entity);
        return ResponseEntity
                .created(URI.create("/tasks/" + dto.getId()))
                .body(dto);
    }

    @Override
    public ResponseEntity<TaskListDTO> listTasks(Integer limit, Long offset) {
        var entityList = taskService.find(limit, offset);
        var dtoList = entityList.stream()
                .map(this::toTaskDTO)
                .toList();

        var pageDto = new PageDTO();
        pageDto.setLimit(limit);
        pageDto.setOffset(offset);
        pageDto.setSize(dtoList.size());

        var dto = new TaskListDTO();
        dto.setPage(pageDto);
        dto.setResults(dtoList);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> editTask(Long taskId, TaskForm taskForm) {
        var entity = taskService.update(taskId, taskForm.getTitle());
        var dto = this.toTaskDTO(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    private TaskDTO toTaskDTO(TaskEntity taskEntity) {
        var taskDTO = new TaskDTO();
        taskDTO.setId(taskEntity.getId());
        taskDTO.setTitle(taskEntity.getTitle());
        return taskDTO;
    }
}
