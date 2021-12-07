package com.mferrara.AppointmentBookingApp.controllers;

import com.mferrara.AppointmentBookingApp.models.ServiceProvider;
import com.mferrara.AppointmentBookingApp.repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/serviceproviders")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderRepository providerRepository;

    @PostMapping
    public ServiceProvider createServiceProvider(@RequestBody ServiceProvider newServiceProvider){
        return providerRepository.save(newServiceProvider);
    }

    @GetMapping
    public List<ServiceProvider> getAllServiceProviders(){
        return providerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ServiceProvider getOneProviderById(@PathVariable Long id){
        return providerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ServiceProvider updateProvider(@PathVariable Long id, @RequestBody ServiceProvider update){
        ServiceProvider provider = providerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(update.getFirstName() != null)
            provider.setFirstName(update.getFirstName());
        if(update.getLastName() != null)
            provider.setLastName(update.getLastName());
        if(update.getTitle() != null)
            provider.setTitle(update.getTitle());
        if(update.getClients() != null)
            provider.setClients(update.getClients());
        if(update.getStartTime() != null)
            provider.setStartTime(update.getStartTime());
        if(update.getHoursOpen() != null)
            provider.setHoursOpen(update.getHoursOpen());
        if(update.getTimeSlotsPerHour() != null)
            provider.setTimeSlotsPerHour(update.getTimeSlotsPerHour());
        if(update.getDaysOff() != null)
            provider.setDaysOff(update.getDaysOff());

        return providerRepository.save(provider);
    }

    @DeleteMapping("/{id}")
    public String deleteServiceProvider(@PathVariable Long id){
        ServiceProvider provider = providerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String result = provider.getFirstName() + " " + provider.getLastName() + " was removed from system";
        providerRepository.deleteById(id);
        return result;

    }

}
