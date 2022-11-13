package no.hvl.dat250.jpa.assignment.dummydata;


import no.hvl.dat250.jpa.assignment.models.poll.Poll;
import no.hvl.dat250.jpa.assignment.models.poll.PollStatus;
import no.hvl.dat250.jpa.assignment.models.user.Role;
import no.hvl.dat250.jpa.assignment.models.user.User;
import no.hvl.dat250.jpa.assignment.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignment.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignment.service.poll.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DummyData {
    private final UserRepository userRepository;
    private final PollService pollService;

    @Autowired
    public DummyData(UserRepository userRepository, PollService pollService) {
        this.userRepository = userRepository;
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
                }

                pollService.createPoll(p);
            }
        }

        for (long i = 1; i < 20; i++) {
            pollService.openPoll(i);
        }

        User[] users = userRepository.findAll().toArray(User[]::new);
        Poll[] polls = pollService.findAllOpenPolls().toArray(Poll[]::new);

        int un = users.length;
        int pn = polls.length;

        int n;

        for (int i = 0; i < 50; i++) {
            n = r.nextInt(2);
            User u = users[r.nextInt(un)];
            Poll p = polls[r.nextInt(pn)];
            pollService.updateUserVote(n == 0, u.getUsername(), p.getId());

            UUID uuid = UUID.randomUUID();

            pollService.createDeviceVote(uuid, p.getId());
            pollService.updateDeviceVote(uuid, r.nextInt(20), r.nextInt(20), p.getId());

            pollService.createAnonymousVote(p, r.nextBoolean());
        }
    }
}
