package com.mferrara.AppointmentBookingApp.controllers;


import com.mferrara.AppointmentBookingApp.repositories.ClientRepository;
import com.mferrara.AppointmentBookingApp.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {



    @Autowired
    private ClientRepository clientRepository;

    @PostMapping
    public Client createClient(@RequestBody Client newClient){
        return clientRepository.save(newClient);
    }

    @GetMapping
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Client getOneClient(@PathVariable Long id){
        return clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client update){
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(update.getFirstName() != null)
            client.setFirstName(update.getFirstName());
        if(update.getLastName() != null)
            client.setLastName(update.getLastName());
        if(update.getEmail() != null)
            client.setEmail(update.getEmail());
        if(update.getPhoneNumber() != null)
            client.setPhoneNumber(update.getPhoneNumber());

        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String result = client.getFirstName() + " " + client.getLastName() + " was removed from system";
        clientRepository.deleteById(id);
        return result;
    }
}
