package no.hvl.dat250.jpa.assignment.web.springsecurity;

import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        User u = user.orElseThrow(() -> new UsernameNotFoundException(username + " was not found"));

        return new CustomUserDetails(u);
    }
}
