package no.hvl.dat250.jpa.assignment.dummydata;


import no.hvl.dat250.jpa.assignment.models.*;
import no.hvl.dat250.jpa.assignment.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignment.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignment.repository.vote.VoteRepository;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class DummyData {
    private final UserRepository userRepository;
    private final PollRepository pollRepository;
    private final PollService pollService;

    @Autowired
    public DummyData(UserRepository userRepository, PollRepository pollRepository, PollService pollService) {
        this.userRepository = userRepository;
        this.pollRepository = pollRepository;
        this.pollService = pollService;
    }

    @PostConstruct
    private void postConstruct() throws NoSuchAlgorithmException {
        PasswordEncoder enc = new BCryptPasswordEncoder();
        String password = enc.encode("admin password");
        User admin = new User("admin", password, Role.Admin);
        admin.setFirstname("admin");
        admin.setLastname("adminsen");
        admin.setEmail("admin@admin.com");

        userRepository.save(admin);

        Random r = new Random(1337);

        int pollN = 0;

        for (int i = 0; i < 14; i++) {
            User c = new User(String.format("user%d", i), enc.encode(("" + i)), Role.Regular);

            userRepository.save(c);

            for (int j = 0; j < 5; j++) {
                Poll p = new Poll("Poll" + pollN++, "Theme" + r.nextInt(5), r.nextBoolean(), c);
                int k = r.nextInt(3);

                if (k == 0) {
                    p.setActiveStatusToFinished();
                } else if (k == 1) {
                    p.setActiveStatusToOpen();
                }

                pollRepository.save(p);
            }
        }

        for (int i = 0; i < 3; i++) {
            User d = new User(String.format("d%d", i), "pass", Role.Device);
            userRepository.save(d);
        }

        User[] users = userRepository.findAll().toArray(User[]::new);
        Poll[] polls = pollRepository.findAllByActiveStatus(PollStatus.OPEN).toArray(Poll[]::new);

        int un = users.length;
        int pn = polls.length;

        int n;

        for (int i = 0; i < 50; i++) {
            n = r.nextInt(2);
            User u = users[r.nextInt(un)];
            Poll p = polls[r.nextInt(pn)];
            pollService.updateVote(n == 0, u.getUsername(),p.getId());
        }
    }
}
