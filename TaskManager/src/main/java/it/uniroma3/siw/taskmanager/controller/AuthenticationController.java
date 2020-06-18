package it.uniroma3.siw.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validation.UserValidator;
import it.uniroma3.siw.taskmanager.controller.validation.CredentialsValidator;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
import javax.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	CredentialsService credentialService;
	@Autowired
	UserValidator userValidator;
	@Autowired
	CredentialsValidator credentialsValidator;
	@Autowired
	SessionData sessionData;
	
	 /**
     * This method is called when a GET request is sent by the user to URL "/register".
     * This method prepares and dispatches the User registration view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "register"
     */
	@RequestMapping(value= {"/register"}, method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("credentialsForm", new Credentials());
		return "registerUser";
	}
	
	 /**
     * This method is called when a GET request is sent by the user to URL "/register".
     * This method prepares and dispatches the User registration view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "register"
     **/
	@RequestMapping(value= {"/register"},method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("userForm") User user,
			BindingResult userBindingResult,
			@Valid @ModelAttribute("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult, Model model) {
		//validate user and credentials
		this.userValidator.validate(user,userBindingResult);
		this.credentialsValidator.validate(credentials,credentialsBindingResult);
		
		// if neither of them had invalid contents, store the User and the Credentials into the DB
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			//set  user and credentials
			credentials.setUser(user);
			credentialService.saveCredentials(credentials);
			model.addAttribute("user",user);
			return "registrazioneSuccessful";
		}
		
		return "registerUser";
		
	}
	
}
