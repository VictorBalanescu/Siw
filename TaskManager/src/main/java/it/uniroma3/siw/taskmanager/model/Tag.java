package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column (nullable = false)
	private String colore;
	@Column
	private String descrizione;
	@Column(updatable = false, nullable = false)
	private LocalDateTime creationTimestamp;
	@Column(nullable = false)
    private LocalDateTime lastUpdateTimestamp;
	
	
	public Tag() {
		
	}
	public Tag(String nome,String colore,String descrizione) {
		this.nome=nome;
		this.colore=colore;
		this.descrizione=descrizione;
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
	public String getColore() {
		return colore;
	}
	public void setColore(String colore) {
		this.colore = colore;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
