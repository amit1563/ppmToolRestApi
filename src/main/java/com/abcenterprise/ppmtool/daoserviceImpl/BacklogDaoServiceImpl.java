package com.abcenterprise.ppmtool.daoserviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcenterprise.ppmtool.daoservice.BacklogDaoService;
import com.abcenterprise.ppmtool.domain.Backlog;
import com.abcenterprise.ppmtool.domain.Project;
import com.abcenterprise.ppmtool.domain.ProjectTask;
import com.abcenterprise.ppmtool.exception.BacklogNotFoundException;
import com.abcenterprise.ppmtool.exception.ProjectNotFoundException;
import com.abcenterprise.ppmtool.exception.ProjectTaskNotFoundException;
import com.abcenterprise.ppmtool.repositories.BacklogRepository;
import com.abcenterprise.ppmtool.repositories.ProjectTaskRepository;
import com.abcenterprise.ppmtool.services.ProjectService;

@Service
public class BacklogDaoServiceImpl implements BacklogDaoService {

	@Autowired
	private ProjectTaskRepository projectTaskRepo;
	@Autowired
	private BacklogRepository backlogRepo;
	@Autowired
	private ProjectService projectService;

	/**
	 * This method is to add project tasks to the backlog object step 1 : get the
	 * Backlog object with the given projectIdentifier ? if it is not null =>
	 * Backlog object exist otherwise throw ProjectNotFoundException step 2 : if
	 * Backlog exist ? then set it to projectTask and get the backlog Identifier to
	 * update the sequenceNumber of projetTask (BLI-) and update the PTSequence of
	 * backlog by incrementing it and set projectIdentifier step 3 : handle
	 * scenarios when priority and status is null
	 */
	@Override
	public void addProjectTask(String projectIdentifier, ProjectTask projectask,String username) {
		projectIdentifier = projectIdentifier.toUpperCase();
		Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();//backlogRepo.findByProjectIdentifier(projectIdentifier);
		if (backlog == null)
			throw new ProjectNotFoundException("Project Not found for given projectIdentifier");
		projectask.setBacklog(backlog);
		projectask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backlog.getPTSequence());
		projectask.setProjectIdentifier(projectIdentifier);
		backlog.setPTSequence(backlog.getPTSequence() + 1);
		if (projectask.getPriority() == null || projectask.getPriority() == 0) {
			projectask.setPriority(3);
		}

		if (projectask.getStatus() == null || projectask.getStatus().isEmpty()) {
			projectask.setStatus("TO_DO");
		}
		projectTaskRepo.save(projectask);
	}

	@Override
	public Iterable<ProjectTask> findBacklogById(String projectIdentifier , String username) {
		
		projectIdentifier = projectIdentifier.toUpperCase();
		Project project = projectService.findByProjectIdentifier(projectIdentifier, username);
		if (projectTaskRepo.findByProjectIdentifierOrderByPriority(projectIdentifier).isEmpty()) {
			throw new ProjectNotFoundException("Project Not found for given projectIdentifier");
		}
		Iterable<ProjectTask> itrable = projectTaskRepo.findByProjectIdentifierOrderByPriority(projectIdentifier);
		List<ProjectTask> projectTasksList = new ArrayList<>();
		itrable.forEach(new Consumer<ProjectTask>() {

			@Override
			public void accept(ProjectTask projectTask) {
				if(projectTask.getProjectIdentifier().equals(project.getProjectIdentifier())) {
					projectTasksList.add(projectTask);
				}
			}		
		});
		return projectTasksList;
	}

	@Override
	public ProjectTask findByProjectSequence(String backlog_id, String projectSequence, String username) {
		backlog_id = backlog_id.toUpperCase();
		projectSequence = projectSequence.toUpperCase();
		Backlog backlog =  projectService.findByProjectIdentifier(backlog_id, username).getBacklog(); //backlogRepo.findByProjectIdentifier(backlog_id);

		if (backlog == null) {
			throw new BacklogNotFoundException("backlog Not found for given backlog_id");
		}

		ProjectTask projectTask = projectTaskRepo.findByProjectSequence(projectSequence);

		if (projectTask == null) {
			throw new ProjectTaskNotFoundException(
					"Project Task NotFound for given project Sequence : " + projectSequence);
		}
		// make sure that the backlog/project id in the path corresponds to the right
		// project
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException(
					"Project Task '" + projectSequence + "' does not exist in project: '" + backlog_id);
		}

		return projectTask;
	}

	@Override
	public ProjectTask updateProjectTaskByProjectSequence(ProjectTask projectTask, String backlog_id,
			String projectSequence, String username) throws ProjectTaskNotFoundException {
		
		ProjectTask projectTaskTemp = null;
		try {
			projectTaskTemp = findByProjectSequence(backlog_id, projectSequence,username );
		} catch (BacklogNotFoundException | ProjectTaskNotFoundException ex) {
			throw new ProjectTaskNotFoundException("Project Task with provided project task equence " + projectSequence
					+ "' does not exist in backlog for the given backlog id : '" + backlog_id
					+ " for the project with project id " + projectSequence);
		}
		// set project task id because it should not be null so that jpa can update the
		// same
		projectTask.setId(projectTaskTemp.getId());
		projectTaskTemp = projectTask;
		if(projectTask.getPriority()==null || projectTask.getPriority().intValue()==0) {
			projectTaskTemp.setPriority(3);
		}
		projectTaskRepo.save(projectTaskTemp);
		return projectTaskRepo.findByProjectSequence(projectSequence);
	}

	@Override
	public void deleteProjectTaskByProjectSequence(String backlog_id, String projectSequence, String username)
			throws ProjectTaskNotFoundException {
		ProjectTask projectTaskTemp = null;
		backlog_id = backlog_id.toUpperCase();
		try {
			projectTaskTemp = findByProjectSequence(backlog_id, projectSequence, username);
		} catch (BacklogNotFoundException | ProjectTaskNotFoundException ex) {
			throw new ProjectTaskNotFoundException("Project Task with provided project task equence " + projectSequence
					+ "' does not exist in backlog for the given backlog id : '" + backlog_id
					+ " for the project with project id " + projectSequence);
		}

		Backlog backlog = backlogRepo.findByProjectIdentifier(backlog_id);

		if (backlog != null) {
			
			//this code is not required because we are mapping backlog by orphanRemoval = true and cascadeType = true
			/*
			 * List<ProjectTask> projectTasks=backlog.getProjectTasks();
			 * projectTasks.remove(projectTaskTemp); backlogRepo.save(backlog);
			 */
			projectTaskRepo.delete(projectTaskTemp);
		} else {
			throw new BacklogNotFoundException("Backlog not found for give backlog id" + backlog_id);
		}

	}

}
