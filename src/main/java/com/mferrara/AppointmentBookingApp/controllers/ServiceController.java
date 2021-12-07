package com.mferrara.AppointmentBookingApp.controllers;


import com.mferrara.AppointmentBookingApp.models.Service;
import com.mferrara.AppointmentBookingApp.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping
    public Service createService(@RequestBody Service newService){
        return serviceRepository.save(newService);
    }

    @GetMapping
    public List<Service> getAllServices(){
        return serviceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Service getOneServiceById(@PathVariable Long id){
        return serviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Service updateId(@PathVariable Long id, @RequestBody Service update){
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(update.getName() != null)
            service.setName(update.getName());
        if(update.getDescription() != null)
            service.setDescription(update.getDescription());
        if(update.getPrice() != null)
            service.setPrice(update.getPrice());
        if(update.getLengthInMinutes() != null)
            service.setLengthInMinutes(update.getLengthInMinutes());
        if(update.getServiceProviders() != null)
            service.setServiceProviders(update.getServiceProviders());

        return service;
    }

    @DeleteMapping("/{id}")
    public String deleteServiceById(@PathVariable Long id){
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String result = service.getName() + " was removed from Services";
        serviceRepository.deleteById(id);
        return result;
    }
}
