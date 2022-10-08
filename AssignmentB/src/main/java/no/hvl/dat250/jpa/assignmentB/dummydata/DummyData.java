package no.hvl.dat250.jpa.assignmentB.dummydata;


import no.hvl.dat250.jpa.assignmentB.dao.user.UserRepository;
import no.hvl.dat250.jpa.assignmentB.dao.poll.PollRepository;
import no.hvl.dat250.jpa.assignmentB.models.User;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
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
        User admin = new User("admin", "admin password", Role.Admin);

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
