package com.abcenterprise.ppmtool.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcenterprise.ppmtool.domain.ProjectTask;
import com.abcenterprise.ppmtool.services.BacklogService;
import com.abcenterprise.ppmtool.validationerrorhandler.FieldErrorHandler;

@RestController
@CrossOrigin
@RequestMapping("/api/backlog")
public class BacklogController {

	@Autowired
	BacklogService backlogService;
	@Autowired
	FieldErrorHandler fieldErrorHandler;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> createNewProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id, Principal principal) {
		ResponseEntity<?> errorMap = fieldErrorHandler.mapValidationError(result);
		if (errorMap != null) {
			return errorMap;
		}
		backlogService.addProjectTask(backlog_id, projectTask, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.CREATED);
	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
		return backlogService.findBacklogById(backlog_id, principal.getName());
	}

	@GetMapping("/{backlog_id}/{projectSequence}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String projectSequence, Principal principal) {
		ProjectTask projectTask = backlogService.findByProjectSequence(backlog_id,
				projectSequence, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{backlog_id}/{projectSequence}")
	public ResponseEntity<?> updateProjectTaskByProjectSequence(@Valid @RequestBody ProjectTask projectTask,
			BindingResult result, @PathVariable String backlog_id, @PathVariable String projectSequence, Principal principal) {
		ResponseEntity<?> errorMap = fieldErrorHandler.mapValidationError(result);
		if (errorMap != null) {
			return errorMap;
		}
		backlogService.updateProjectTaskByProjectSequence(projectTask, backlog_id, projectSequence, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.CREATED);
	}

	@DeleteMapping("{backlog_id}/{projectSequence}")
	public ResponseEntity<?> deleteProjectTaskByProjectSequence(@PathVariable String backlog_id,
			@PathVariable String projectSequence, Principal principal) {
		backlogService.deleteProjectTaskByProjectSequence(backlog_id, projectSequence,principal.getName());
		return new ResponseEntity<String>(
				"Project Task deleted successfully for given project task Id" + projectSequence, HttpStatus.OK);

	}
}