package no.hvl.dat250.jpa.assignment.dummydata;


import no.hvl.dat250.jpa.assignment.repository.user.UserRepository;
import no.hvl.dat250.jpa.assignment.repository.poll.PollRepository;
import no.hvl.dat250.jpa.assignment.models.User;
import no.hvl.dat250.jpa.assignment.models.Poll;
import no.hvl.dat250.jpa.assignment.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class DummyData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @PostConstruct
    private void postConstruct() throws NoSuchAlgorithmException {
        String password = new BCryptPasswordEncoder().encode("admin password");
        User admin = new User("admin", password , Role.Admin);
        admin.setFirstname("admin");
        admin.setLastname("adminsen");
        admin.setEmail("admin@admin.com");

        userRepository.save(admin);

        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        Random r = new Random(1337);

        int pollN = 0;

        for (int i = 0; i < 14; i++) {
            User c = new User(
                    String.format("user%d", i),
                    String.format("%1s", bytesToHex(md.digest(("" + i).getBytes(StandardCharsets.UTF_8)))),
                    Role.Regular);

            userRepository.save(c);

            for (int j = 0; j < 5; j++) {
                Poll p = new Poll(
                        "Poll" + pollN++,
                        "Theme" + r.nextInt(5),
                        r.nextBoolean(),
                        r.nextBoolean(),
                        LocalDateTime.now(),
                        c);
                pollRepository.save(p);
            }
        }

        for (int i = 0; i < 3; i++) {
            User d = new User(String.format("d%d", i),"pass", Role.Device);
            userRepository.save(d);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
