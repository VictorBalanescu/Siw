package it.uniroma3.siw.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.taskmanager.model.User;

/**
 * This interface is a CrudRepository for repository operations on Users.
 *
 * @see User
 **/
public interface UserRepository extends CrudRepository<User, Long> {
	
	
	public Optional<User> findByNome(String nome);
    
}