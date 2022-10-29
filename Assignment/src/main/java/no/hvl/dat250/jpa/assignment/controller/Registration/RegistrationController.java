package no.hvl.dat250.jpa.assignment.controller.Registration;

import no.hvl.dat250.jpa.assignment.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    private UserService userService;

    @GetMapping(value="/account/register")
    public String registerPage(Model model){

        UserDto user = new UserDto();
        model.addAttribute("user", user);

        return "account/register";
    }
}
