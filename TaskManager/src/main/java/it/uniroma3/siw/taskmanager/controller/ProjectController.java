package it.uniroma3.siw.taskmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validation.ProjectValidator;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.TagService;
import it.uniroma3.siw.taskmanager.service.UserService;

@Controller
public class ProjectController {
	
		@Autowired
		ProjectValidator projectValidator;
		@Autowired
		ProjectService projectService;
	    @Autowired
	    SessionData sessionData;
	    @Autowired
	    UserService userService;
	    @Autowired
	    TagService tagService;
	
	 /**
     * This method is called when a GET request is sent by the user to URL "/newProject".
     * This method prepares and dispatches the User registration view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "newProject"
     **/
	 @RequestMapping(value = {"/newProject"}, method = RequestMethod.GET)
	    public String newProject(Model model) {
	    	User loggedUser = sessionData.getLoggedUser();
	        model.addAttribute("user", loggedUser);
	        model.addAttribute("projectForm", new Project());
	        return "newProject";
	    }
	 /**
	     * This method is called when a GET request is sent by the user to URL "/newProject".
	     * This method prepares and dispatches the User registration view.
	     *
	     * @param model the Request model
	     * @return the name of the target view, that in this case is "newProjectr"
	     **/
		@RequestMapping(value = {"/newProject"}, method = RequestMethod.POST)
		public String registerUser(@Valid @ModelAttribute("projectForm") Project project,
				BindingResult projectBindingResult,Model model) {
			User loggedUser = sessionData.getLoggedUser();
			model.addAttribute("user", loggedUser);
			this.projectValidator.validate(project, projectBindingResult);
			//se tutto va bene
			System.out.println(projectBindingResult.hasErrors());
			if(!projectBindingResult.hasErrors()){
				project.setOwner(loggedUser);
				this.projectService.saveProject(project);
				return "redirect:/project/"+project.getId();
			}
			return "home";
		}
		
		//stamp user project 
		@RequestMapping(value = {"/myProjects"}, method = RequestMethod.GET)
	    public String myProjects(Model model) {
	    	User loggedUser = sessionData.getLoggedUser();
	    	List<Project> projectList=projectService.getOwnerProject(loggedUser);
	        model.addAttribute("user", loggedUser);
	        model.addAttribute("projectsList", projectList);
	    	return "myProjects";
	    }
		//stampa le info del progetto
		@RequestMapping(value= {"/project/{projectId}"},method = RequestMethod.GET)
		public String project(Model model,@PathVariable Long projectId) {
			Project project = projectService.getProject(projectId);
			//List<Tag> tag =tagService.g
			model.addAttribute("projectForm", project);
			//model.addAttribute("tagsList", projectList);
			if(project==null)
				return "redirect:project";
			return"project";
		}
		//elimina il progetto
		@RequestMapping(value= {"/delete/project/{projectId}"},method = RequestMethod.POST)
		public String deleteProject(Model model,@PathVariable Long projectId) {
			Project project=projectService.getProject(projectId);
			projectService.deleteProject(project);
			User loggedUser = sessionData.getLoggedUser();
	    	List<Project> projectList=projectService.getOwnerProject(loggedUser);
	        model.addAttribute("user", loggedUser);
	        model.addAttribute("projectsList", projectList);
	    	return "myProjects";
		}

}
