package it.uniroma3.siw.taskmanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false,length = 100)
	private String nome;
	@Column
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> members;
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="project_id")
	private List<Task> tasks;
	@OneToMany()
	@JoinColumn(name="project_id")
	private List<Tag> tags;
	
	public Project() {
		
	}
	public Project(String nome,String description) {
		this.nome= nome;
		this.description=description;
		this.members = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.tags= new ArrayList<>();
	}
	
	public void addMember(User user) {
        if (!this.members.contains(user))
            this.members.add(user);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTasks(Task task) {
    	if (!this.tasks.contains(task))
            this.tasks.add(task);
    }
    public void addTags(Tag tag) {
    	if (!this.tags.contains(tag))
            this.tags.add(tag);
    }

    @Override
    public String toString() {

        return "Project{" +
                "id=" + id +
                ", name='" + nome + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    // this is a semplification
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(nome, project.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

}
