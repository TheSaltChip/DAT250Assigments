package no.hvl.dat250.jpa.assignment.web.controller.account;

import no.hvl.dat250.jpa.assignment.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.service.user.UserService;
import no.hvl.dat250.jpa.assignment.web.formobject.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class AccountController {
    private UserService userService;
    private AuthenticationFacade authenticationFacade;

    @Autowired
    public AccountController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/account")
    public String accountPage(Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            User user = userService.getUserByUsername(currentUserName);

            UserUpdateForm uuf = new UserUpdateForm(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());

            model.addAttribute("user", uuf);

            return "account/account";
        }

        return "account/login";
    }

}
