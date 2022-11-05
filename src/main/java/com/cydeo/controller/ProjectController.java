package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {



    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createProject(Model model) {

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("managers", userService.findAllByRoleId(2L));//we will use also for employees
        model.addAttribute("projects", projectService.listAllProjects());

        return "/project/create";

    }




    @PostMapping("/create") //save button
    public String insertProject(@ModelAttribute("project") ProjectDTO project, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("managers", userService.findAllByRoleId(2L));
            model.addAttribute("projects", projectService.listAllProjects());

            return "/project/create";

        }

        projectService.save(project);

        return "redirect:/project/create";

    }




    @GetMapping("/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectCode);
        return "redirect:/project/create";
    }





    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);
        return "redirect:/project/create";
    }






    @GetMapping("/update/{projectCode}") //edit-part
    public String editProject(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("project", projectService.findByProjectCode(projectCode));//to select project which will be updated
        model.addAttribute("managers", userService.findAllByRoleId(2L));
        model.addAttribute("projects", projectService.listAllProjects());

        return "/project/update";

    }


    @PostMapping("/update") // for update : save button
    public String updateProject(@ModelAttribute("project") ProjectDTO project, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("managers", userService.findAllByRoleId(2L));
            model.addAttribute("projects", projectService.listAllProjects());

            return "/project/update";

        }

        projectService.update(project);

        return "redirect:/project/create";

    }






    /*

    @GetMapping("/manager/project-status")
    public String getProjectByManager(Model model) {

        //UserDTO manager = userService.findByUserName("admin@admin.com");
        UserDTO manager = userService.findAllByRoleId(2L).get(1);

        List<ProjectDTO> projects = projectService.getCountedListOfProjectDTO(manager);

        model.addAttribute("projects", projects);

        return "/manager/project-status";

    }



    @GetMapping("/manager/complete/{projectCode}")
    public String managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectService.findById(projectCode));
        return "redirect:/project/manager/project-status";
    }
     */


}
