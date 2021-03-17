package com.TradeProject.ClientConnectivity.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClient(){
        return this.clientService.getClients();
    }

    @PostMapping(path="/signup")
    public Response registerNewClient(@RequestBody Client client){
        return this.clientService.addNewClient(client);
    }

    @PostMapping(path="/login")
    public Response loginClient(@RequestBody Client client){
        return this.clientService.loginClient(client);
    }

    @GetMapping(path = "{clientId}")
    public Client getClientById(@RequestParam Long clientid){
        return this.clientService.getClient(clientid);
    }

}

