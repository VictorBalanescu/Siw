package it.uniroma3.siw.taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
    protected TaskRepository taskRepository;

    /**
     * This method retrieves a Task from the DB based on its ID.
     * @param id the id of the Task to retrieve from the DB
     * @return the retrieved Task, or null if no Task with the passed ID could be found in the DB
     ***/
    @Transactional
    public Task getTask(long id) {
        Optional<Task> result = this.taskRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a Task in the DB.
     * @param user the Task to save into the DB
     * @return the saved Task
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     **/
    @Transactional
    public Task saveTask(Task task) {
        return this.taskRepository.save(task);
    }
    @Transactional
    public void deleteTask(Task task) {
    	this.taskRepository.delete(task);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     **/
    @Transactional
    public List<Task> getAllTasks() {
        List<Task> result = new ArrayList<>();
        Iterable<Task> iterable = this.taskRepository.findAll();
        for(Task task : iterable)
            result.add(task);
        return result;
    }

}
