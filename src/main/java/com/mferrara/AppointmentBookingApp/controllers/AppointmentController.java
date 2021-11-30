package com.mferrara.AppointmentBookingApp.controllers;

import com.mferrara.AppointmentBookingApp.models.Appointment;
import com.mferrara.AppointmentBookingApp.models.ServiceProvider;
import com.mferrara.AppointmentBookingApp.repositories.AppointmentRepository;
import com.mferrara.AppointmentBookingApp.repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceProviderRepository providerRepository;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment newAppointment){
        ServiceProvider provider = providerRepository.findById(newAppointment.getProvider().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); ;
        for(Appointment appts : provider.getAppointments())
        if(appts.getTime().isEqual((newAppointment.getTime()))){
            System.out.println("TEST = TRUE");
        }
        return appointmentRepository.save(newAppointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    @GetMapping("/schedule/{providerId}")
    public List<LocalDateTime> viewSchedule(@PathVariable Long providerId, @RequestParam(defaultValue = "0" ) int monthId) {
        ServiceProvider provider = providerRepository.findById(providerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<LocalDateTime> result = new ArrayList<>();
        int startTime = 9; //TODO: add startTime and hoursOpen to Provider class (&& List<Day> daysOff )
        int hoursOpen = 8; // TODO: ^^^^see above^^^^^^
        Month month;
        int year;
        if(monthId == 0)
            month = LocalDateTime.now().getMonth();
        else
            month = Month.of(monthId);
        if(monthId != 0 && monthId < LocalDateTime.now().getMonth().getValue())
            year = LocalDateTime.now().getYear() + 1;
        else
            year = LocalDateTime.now().getYear();

        for (int day = 1; day <= month.maxLength(); day++) {
            int hour = startTime - 1;
            for (int i = 0; i < hoursOpen * 2; i++) {
                int min;
                if (i % 2 == 0) {
                    min = 0;
                    hour++;
                } else min = 30;
                LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, min);
                if(!result.contains(dateTime)){
                    result.add(dateTime);
                }
                for(Appointment appts : provider.getAppointments()){
                    result.remove(appts.getTime());
                }
                result.removeIf(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));
            }
        }

        return result;
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id){
        return appointmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment update){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(update.getDescription() != null)
            appointment.setDescription(update.getDescription());
        if(update.getClient() != null)
            appointment.setClient(update.getClient());
        if(update.getProvider() != null)
            appointment.setProvider(update.getProvider());

        return appointmentRepository.save(appointment);
    }

    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id){
        Appointment appt = appointmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        appointmentRepository.deleteById(id);
        return "Appointment was deleted";

    }
}
