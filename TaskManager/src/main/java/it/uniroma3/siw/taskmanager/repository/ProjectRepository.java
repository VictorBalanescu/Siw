package it.uniroma3.siw.taskmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;

public interface ProjectRepository  extends CrudRepository<Project, Long>{

	public Optional<Project> findByNome(String nome);
	public List<Project> findByMembers(User user);
	public List<Project> findByOwner(User user);
}
