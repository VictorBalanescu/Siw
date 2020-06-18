package it.uniroma3.siw.taskmanager.controller;

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
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.TagService;

@Controller
public class TagController {
	
	
	@Autowired
	SessionData sessionData;
	@Autowired
	TagService tagService;
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value = {"/newTag/{projectId}"}, method = RequestMethod.GET)
    public String newTag(Model model,@PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user", loggedUser);
        model.addAttribute("tagForm", new Tag());
        Project project=projectService.getProject(projectId);
        model.addAttribute("project",project);
        System.out.println(project.getId());
        return "newTag";
    }
	@RequestMapping(value = {"/newTag/{projectId}"}, method = RequestMethod.POST)
	public String registerTag(@Valid @ModelAttribute("tagForm") Tag tag,@PathVariable Long projectId,
			BindingResult projectBindingResult,Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		if(!projectBindingResult.hasErrors()){
			Project project =projectService.getProject(projectId);
			project.addTags(tag);
			System.out.println(projectId);
			tagService.saveTag(tag);
			model.addAttribute("projectForm", project);
			return"redirect:/project/"+project.getId();
		}
		return "home";
	}

}
