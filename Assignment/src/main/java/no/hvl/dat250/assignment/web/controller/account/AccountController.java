package no.hvl.dat250.assignment.web.controller.account;

import no.hvl.dat250.assignment.authenticationfacade.AuthenticationFacade;
import no.hvl.dat250.assignment.models.user.User;
import no.hvl.dat250.assignment.service.user.UserService;
import no.hvl.dat250.assignment.web.formobject.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.NoSuchElementException;

@Controller
public class AccountController {
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public AccountController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(value = "/account")
    public String accountPage(Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        try {
            User user = userService.getUserByUsername(authentication.getName());
            UserUpdateForm uuf = new UserUpdateForm(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());

            model.addAttribute("user", uuf);

            return "account/account";

        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }
    }

    @PutMapping(value = "/account")
    public String editUser(UserUpdateForm uuf) {
        Authentication authentication = authenticationFacade.getAuthentication();

        try {
            userService.updateUser(authentication.getName(), uuf);
            return "redirect:/account";
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }
    }
}
