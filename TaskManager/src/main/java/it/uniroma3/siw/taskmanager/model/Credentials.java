package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

//entita
@Entity
public class Credentials {
	public static final String DEFAULT_ROLE= "DEFAULT";
	public static final String ADMIN_ROLE="ADMIN";
	
	//variabili
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false,name="id")
	private Long id; //id per identificare la pwd
	@Column(nullable =false,unique = true,length = 100)
	private String username; //username univoca
	@Column(nullable=false,length = 100)
	private  String password; //password
	@OneToOne(cascade = CascadeType.ALL)
	private User user; //user 
	@Column(nullable = false, updatable = false)
	private LocalDateTime creationTimeStamp; //data che viene creata
	@Column(nullable=false)
	private LocalDateTime lastUpdateCreationTimeStamp; //ultmio aggiornamento
	@Column(nullable = false,length = 10)
	private String role;
	
	//costruttore
	public Credentials() {
		
	}
	public Credentials(String username,String password ) {
		this.username=username;
		this.password=password;
	}
	@PrePersist
	protected void onPersist() {
		this.creationTimeStamp = LocalDateTime.now();
		this.lastUpdateCreationTimeStamp = LocalDateTime.now();
	}
	@PreUpdate
	protected void onUpdate() {
		this.lastUpdateCreationTimeStamp=LocalDateTime.now();
	}
	
	//metodi setter e getter
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getCreationTimeStamp() {
		return creationTimeStamp;
	}
	public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}
	public LocalDateTime getLastUpdateCreationTimeStamp() {
		return lastUpdateCreationTimeStamp;
	}
	public void setLastUpdateCreationTimeStamp(LocalDateTime lastUpdateCreationTimeStamp) {
		this.lastUpdateCreationTimeStamp = lastUpdateCreationTimeStamp;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	//equals and hashCode
	@Override
	public boolean equals(Object o) {
		if(this==o)
			return true;
		if(!(o instanceof Credentials))
			return false;
		Credentials user= (Credentials) o;
		return Objects.equals(username,user.getUsername()) &&
				Objects.equals(role, user.role) &&
                Objects.equals(creationTimeStamp, user.getCreationTimeStamp()) &&
                Objects.equals(lastUpdateCreationTimeStamp, user.getLastUpdateCreationTimeStamp());
	}
	@Override
	public int hashCode() {
		return Objects.hash(username,role);
	}
	@Override
	public String toString() {
		return "Credentials{" +
                "id=" + id +
                ", userName='" + username+ '\'' +
                ", role='" + role + '\'' +
                ", creationTimestamp=" + creationTimeStamp +
                ", lastUpdateTimestamp=" + lastUpdateCreationTimeStamp +
                '}';
	}
	
}
