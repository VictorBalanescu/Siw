package it.uniroma3.siw.taskmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validation.CredentialsValidator;
import it.uniroma3.siw.taskmanager.controller.validation.UserValidator;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
import it.uniroma3.siw.taskmanager.service.UserService;

@Controller
public class ModificaDatiController {
	
	@Autowired
	SessionData sessionData;
	@Autowired
	UserValidator userValidator;
	@Autowired
	CredentialsValidator credentialsValidator;
	@Autowired
	CredentialsService credentialsService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value= {"/modificaDati"}, method = RequestMethod.GET)
    public String nodificaDati(Model model) {
    	User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user", loggedUser);
        model.addAttribute("userForm", new User());
		model.addAttribute("credentialsForm", new Credentials());
    	return "modificaDati";
    }
	
	@RequestMapping(value= {"/modificaDati"}, method = RequestMethod.POST)
    public String nodificaDatiPost(@Valid @ModelAttribute("userForm") User user,
    		BindingResult userBindingResult,
			@Valid @ModelAttribute("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult, Model model){
			//validate user and credentials
			this.userValidator.validate(user,userBindingResult);
			this.credentialsValidator.validate(credentials,credentialsBindingResult);
			// if neither of them had invalid contents, Update the User and Credentials into the DB
			if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
				//set  user and credentials
				User loggedUser = sessionData.getLoggedUser();
				Credentials loggercredentials = sessionData.getLoggerCredentials();
				loggedUser.setNome(user.getNome());
				loggedUser.setCognome(user.getCognome());
				user.setId(loggedUser.getId());
		        userService.saveUser(user);
		        credentials.setId(loggercredentials.getId());
		        credentials.setUser(user);
				credentialsService.saveCredentials(credentials);
				loggercredentials.setUsername(credentials.getUsername());
				loggercredentials.setLastUpdateCreationTimeStamp(credentialsService.getCredentials(loggercredentials.getId()).getLastUpdateCreationTimeStamp());
				model.addAttribute("user",loggedUser);
				model.addAttribute("credentials",loggercredentials);
				return "modificaSuccessful";
			}
    	return "modificaDati";
    }

}
