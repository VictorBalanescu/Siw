package it.uniroma3.siw.taskmanager.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.repository.TagRepository;

@Service
public class TagService {
	@Autowired
	TagRepository tagRepository;
	@Transactional
	public Tag getTag(long id) {
		Optional<Tag> result=this.tagRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Tag getTag(String nome) {
		Optional<Tag> result= this.tagRepository.findByNome(nome);
		return result.orElse(null);
	}
	@Transactional
	public void getTagProject(Long id) {
		//Optional<Tag> result= this.tagRepository.findById(id);
		//return result.orElse(null);
	}
	@Transactional
	public Tag saveTag(Tag tag) {
		return this.tagRepository.save(tag);
	}
	@Transactional
	public void deleteTag(Tag tag) {
		this.tagRepository.delete(tag);
	}
	
}
