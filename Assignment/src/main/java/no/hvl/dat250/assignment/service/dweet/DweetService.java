package no.hvl.dat250.assignment.service.dweet;

import no.hvl.dat250.assignment.persistence.models.poll.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Service
public class DweetService {
    private final RestTemplate restTemplate;
    private final String URL = "https://dweet.io/dweet/for/dat250group9";

    @Autowired
    public DweetService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String pollOpened(Poll poll) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        DweetPoll dp = new DweetPoll(String.format(
                "A poll with the question '%s' and theme '%s' " +
                        "was opened by user '%s' %s" +
                        "at '%s'",
                poll.getQuestion(),
                poll.getTheme(),
                poll.getOwner().getUsername(),
                (poll.getIsPrivate() ? "" : "with the code: '" + poll.getCode() + "' "),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ));

        HttpEntity<DweetPoll> entity = new HttpEntity<>(dp, headers);

        return restTemplate.postForObject(URL, entity, String.class);
    }

    public String pollFinished(Poll poll) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        DweetPoll dp = new DweetPoll(String.format(
                "A poll with the question '%s' and theme '%s' " +
                        "was finished by user '%s' " +
                        "at '%s'",
                poll.getQuestion(),
                poll.getTheme(),
                poll.getOwner().getUsername(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ));

        HttpEntity<DweetPoll> entity = new HttpEntity<>(dp, headers);

        return restTemplate.postForObject(URL, entity, String.class);
    }
}
