package no.hvl.dat250.jpa.assignment.controller.Registration;

import lombok.NonNull;
import no.hvl.dat250.jpa.assignment.models.Role;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
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

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/register")
    public String registerPage(WebRequest request, Model model){

        UserData userData = new UserData();
        model.addAttribute("userData", userData);

        return "/register";
    }

    @PostMapping(value="/register")
    public String registerUserAccount(@Valid UserData userData, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userData);
            return "/register";
        }
        try {
            registerNewUser(userData);
        } catch (Exception e) {
            bindingResult.rejectValue("username", "userData.username","Existing Account");
            model.addAttribute("registrationForm", userData);
            return "/register";
        }
            return "/login";
    }

    public void registerNewUser(@NonNull UserData userData) throws Exception {

        if(existingUserCheck(userData.getUsername())) {
            throw new Exception("Account with this username already exists");
        }

        User user = new User();

        user.setFirstname(userData.getFirstName());
        user.setLastname(userData.getLastName());
        user.setUsername(userData.getUsername());
        user.setPassword(userData.getPassword());
        user.setEmail(userData.getEmail());
        user.setRole(Role.Regular);

        userService.saveUser(user);
    }

    public boolean existingUserCheck(String username) {
        //userService.getUserByUsername(username) != null;
        return false;
    }
}
