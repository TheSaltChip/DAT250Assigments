package no.hvl.dat250.jpa.assignmentB.api;

import no.hvl.dat250.jpa.assignmentB.dao.ClientDao;
import no.hvl.dat250.jpa.assignmentB.models.Client;
import no.hvl.dat250.jpa.assignmentB.models.Poll;
import no.hvl.dat250.jpa.assignmentB.models.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ClientController {
    private final ClientDao clientDao = new ClientDao();

    /*public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }*/


    @GetMapping(path="user")
    public Client user(){
        return new Client("s","s",Role.Regular);
    }
    @GetMapping(value = "/user/{username}")
    public Client findByUsername(@PathVariable String username){
        return clientDao.findByUsername(username);
    }

    @PostMapping("/users")
    public Client createClient(@RequestBody Client client){
        clientDao.createClient(client.getUsername(),client.getPassword(),client.getRole());
        return client;
    }

    @PutMapping("{username}")
    public Client updateClient(@PathVariable String username, @RequestBody Client client) {
        clientDao.updateClient(username,client.getFirstname(),client.getLastname(),client.getEmail());
        return client;
    }

    @GetMapping(value = "/poll/{username}")
    public Set<Poll> getPollsFromClient(@PathVariable String username) {
        return clientDao.getPollsFromClient(username);
    }

    @PostMapping(path = "user/poll/{username}")
    public Poll addPollToClient(@PathVariable String username,@RequestBody Poll poll) {
        clientDao.addPollToClient(username,poll);
        return poll;
    }

    @DeleteMapping("deleteClient/{username}")
    public Client deleteClient(@PathVariable String username) {
        return clientDao.deleteClient(username);
    }

    @PutMapping("/role/{username}")
    public Client changeRole(@PathVariable String username,@RequestBody Role role) {
        return clientDao.changeRole(username,role);
    }

    @GetMapping("user/role/{username}")
    public Role getRoleOfClient(@PathVariable String username) {
        return clientDao.getRoleOfClient(username);
    }
}
