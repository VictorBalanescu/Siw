package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * A Task is a unitary activity managed by the TaskManager.
 * It is generated and owned by a specific User within the context of a specific Project.
 * The task is contained in the Project and is visible to whoever has visibility over its Project.
 * The Task can be marked as "completed".
 */
@Entity //entita
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false,length = 100)
	private String name;
	@Column
	private String description;
	@Column(nullable=false)
	private boolean completed;
	@Column(updatable = false,nullable=false)
	private LocalDateTime cretionTimeStamp;
	@Column(nullable=false)
	private LocalDateTime lastUpdateTimeStamp;
	@OneToMany()
	@JoinColumn(name="task_id")
	private List<Tag> tags;
	
	//costruttore 
	public Task() {
		
	}
	public Task(String name,String description,boolean completed) {
		this.name=name;
		this.description=description;
		this.completed=completed;
		this.tags=new ArrayList<>();
	}
	
	@PrePersist
	protected void onPersist()
	{
		this.cretionTimeStamp=LocalDateTime.now();
		this.lastUpdateTimeStamp=LocalDateTime.now();
	}
	@PreUpdate
	protected void onUpdate() {
		this.lastUpdateTimeStamp=LocalDateTime.now();
	}
	// GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreationTimestamp() {
        return cretionTimeStamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.cretionTimeStamp = creationTimestamp;
    }

    public LocalDateTime getLastUpdateTimestamp() {
        return lastUpdateTimeStamp;
    }

    public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimeStamp) {
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return completed == task.completed &&
               Objects.equals(name, task.name) &&
                Objects.equals(cretionTimeStamp, task.getCreationTimestamp()) &&
                Objects.equals(lastUpdateTimeStamp, task.getLastUpdateTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, completed, cretionTimeStamp, lastUpdateTimeStamp);
    }
}
