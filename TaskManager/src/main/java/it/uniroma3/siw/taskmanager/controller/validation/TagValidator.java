package it.uniroma3.siw.taskmanager.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.taskmanager.model.Tag;

@Component
public class TagValidator implements Validator{
	final Integer MAX_LENGTH = 100;
    final Integer MIN_LENGTH = 2;
    final Integer MAX_DESC_LENGTH = 100;

    @Override
    public void validate(Object o, Errors errors) {
        Tag tag = (Tag) o;
        String nome = tag.getNome().trim();
        String descrizione= tag.getDescrizione().trim();
        String colore=tag.getColore().trim();

        if (nome.isBlank())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_LENGTH || nome.length() > MAX_LENGTH)
            errors.rejectValue("nome", "size");
        if (colore.isBlank())
            errors.rejectValue("colore", "required");
        else if (colore.length() < MIN_LENGTH || colore.length() > MAX_LENGTH)
            errors.rejectValue("colore", "size");
        if (descrizione.isBlank())
            errors.rejectValue("descrizione", "required");
        else if (descrizione.length() >MAX_DESC_LENGTH)
            errors.rejectValue("descrizione", "size");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tag.class.equals(clazz);
    }


}
