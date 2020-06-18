package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name = "id",columnDefinition = "serial", nullable = false)
	private Long id;
	@Column(nullable = false)
	protected String nome;
	@Column(nullable=false)
	protected String cognome;
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "owner")
	private List<Project> ownedProjects;
	@ManyToMany(mappedBy = "members")
    private List<Project> visibleProjects;
	@Column(updatable = false, nullable = false)
	private LocalDateTime creationTimestamp;
	@Column(nullable = false)
    private LocalDateTime lastUpdateTimestamp;
	
	public User() {
        this.ownedProjects = new ArrayList<>();
        this.visibleProjects = new ArrayList<>();
    }

    public User(String nome, String cognome) {
        this();
        this.nome = nome;
        this.cognome = cognome;
    }
    
    @PrePersist
    protected void onPersist() {
        this.creationTimestamp = LocalDateTime.now();
        this.lastUpdateTimestamp = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateTimestamp = LocalDateTime.now();
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public List<Project> getOwnedProjects() {
		return ownedProjects;
	}
	public void setOwnedProjects(List<Project> ownedProjects) {
		this.ownedProjects = ownedProjects;
	}
	public List<Project> getVisibleProjects() {
		return visibleProjects;
	}
	public void setVisibleProjects(List<Project> visibleProjects) {
		this.visibleProjects = visibleProjects;
	}
	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(LocalDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public LocalDateTime getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	
}
