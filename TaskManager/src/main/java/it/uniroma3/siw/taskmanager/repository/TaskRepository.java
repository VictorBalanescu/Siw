package it.uniroma3.siw.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.taskmanager.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

	public Optional<Task> findByName(String nome);
}
