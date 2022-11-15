package no.hvl.dat250.assignment.web.controller.registration;

import no.hvl.dat250.assignment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/register")
    public String registerPage(WebRequest request, Model model) {

        UserData userData = new UserData();
        model.addAttribute("userData", userData);

        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUserAccount(@Valid UserData userData, BindingResult bindingResult, Model model, WebRequest request) {

        if (request.getParameter("loginLink") != null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("userData", userData);
            return "/register";
        }

        try {
            userService.registerNewUser(userData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            bindingResult.rejectValue("username", "userData.username", "Existing Account");
            model.addAttribute("userData", userData);
            return "/register";
        }

        return "redirect:/login";
    }

}
