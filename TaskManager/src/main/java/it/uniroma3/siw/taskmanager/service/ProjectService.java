package it.uniroma3.siw.taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.ProjectRepository;

/**
 * The CredentialService handles logic regarding Credentials.
 * This mainly consists in retrieving or storing Credentials in the DB.
 **/
@Service
public class ProjectService {
	@Autowired
	protected ProjectRepository projectRepository;
	@Transactional
	public Project getProject(long id) {
		Optional<Project> result=this.projectRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Project getProject(String nome) {
		Optional<Project> result= this.projectRepository.findByNome(nome);
		return result.orElse(null);
	}
	@Transactional
	public Project saveProject(Project project) {
		return this.projectRepository.save(project);
	}
	@Transactional
	public void deleteProject(Project project) {
		this.projectRepository.delete(project);
	}
	@Transactional
	public Project shareProject(Project project, User user) {
		project.addMember(user);
		return this.projectRepository.save(project);
	}
	@Transactional
	public Project addTask(Project project, Task task) {
		project.addTasks(task);
		return this.projectRepository.save(project);
	}
	//restituisce la lista dei projeti che un user puo vedere 
	@Transactional
	public List<Project> getByVisibility(User user){
		return this.projectRepository.findByMembers(user);
	}
	//restituisce la lista dei projeti che un user ha creato
	@Transactional
	public List<Project> getOwnerProject(User user){
		return this.projectRepository.findByOwner(user);
	}
	@Transactional
    public List<Project> getAllUsers() {
        List<Project> result = new ArrayList<>();
        Iterable<Project> iterable = this.projectRepository.findAll();
        for(Project project : iterable)
            result.add(project);
        return result;
    }

}
