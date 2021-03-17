package com.TradeProject.ClientConnectivity.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableJpaAuditing
public class ClientService {
    private final ClientRepository clientRepository;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    Response response = new Response();

    //listing of all client in the database
    public List<Client> getClients(){
        List<Client> clients = clientRepository.findAll();
        for(Client c: clients){
            c.setPassword("");
        }
        return clients;
    }

    //adding of new client in the database
    public Response addNewClient(Client client){
        String password = client.getPassword();
        if (password.isEmpty()){
            throw new IllegalStateException("Invalid Password");
        }
        Optional<Client> clientByEmail = this.clientRepository.findClientByEmail(client.getEmail());
        String encodedPassword = encoder.encode(password);
        client.setPassword(encodedPassword);
        client.setBalance(500.00);

        if(clientByEmail.isPresent()){
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setStatus("Email is already taken");
            HttpStatus.BAD_REQUEST.value();
            throw new IllegalStateException("Email already Taken");
        }
        this.clientRepository.save(client);

        response.setCode(HttpStatus.OK.value());
        response.setStatus("Created Successfully");
        HttpStatus.OK.value();

        return response;
    }

    //getting a single client by id
    public Client getClient(Long clientId) {
        if(!this.clientRepository.existsById(clientId)){
            HttpStatus.BAD_REQUEST.value();
            throw new IllegalStateException("client with id "+ clientId + " does not exist");

        }
        HttpStatus.OK.value();
        return this.clientRepository.findById(clientId).orElse(null);

    }

    //login for the client
    public Response loginClient(Client client) {
        if(this.clientRepository.findClientByEmail(client.getEmail()).isPresent()){
            Client cl = this.clientRepository.findClientByEmail(client.getEmail()).orElse(null);
            String password = cl.getPassword();
            if(encoder.matches(client.getPassword(), password)){
                response.setCode(HttpStatus.OK.value());
                HttpStatus.OK.value();
                response.setStatus("Success");
                cl.setPassword("");
                response.setData(cl);
            }else{
                response.setCode(HttpStatus.BAD_REQUEST.value());
                response.setStatus("Login Failed");
                HttpStatus.BAD_REQUEST.value();
            }
        }else{
            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setStatus("Unauthorized Failed");
            HttpStatus.UNAUTHORIZED.value();
        }
        return response;
    }
}
