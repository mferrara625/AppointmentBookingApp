package com.mferrara.AppointmentBookingApp.controllers;

import com.mferrara.AppointmentBookingApp.models.Appointment;
import com.mferrara.AppointmentBookingApp.models.Service;
import com.mferrara.AppointmentBookingApp.models.ServiceProvider;
import com.mferrara.AppointmentBookingApp.repositories.AppointmentRepository;
import com.mferrara.AppointmentBookingApp.repositories.ServiceProviderRepository;
import com.mferrara.AppointmentBookingApp.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
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

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment newAppointment){
        ServiceProvider provider = providerRepository.findById(newAppointment.getProvider().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); ;
//        for(Appointment appts : provider.getAppointments())
//        if(appts.getStartTime().isEqual((newAppointment.getStartTime()))){
//            System.out.println("TEST = TRUE");
//        }
        Appointment appointment = newAppointment;
        Service service = serviceRepository.findById(appointment.getService().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        int hour = 0;
        int time = service.getLengthInMinutes();
        while( time >= 60){
            hour += 1;
            time -= 60;
        }
        appointment.setEndTime(appointment.getStartTime().plusHours(hour));
        appointment.setEndTime(appointment.getEndTime().plusMinutes(time));

        return appointmentRepository.save(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    @GetMapping("/schedule/{providerId}")
    public List<LocalDateTime> viewSchedule(@PathVariable Long providerId, @RequestParam(defaultValue = "0" ) int monthId) {
        ServiceProvider provider = providerRepository.findById(providerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<LocalDateTime> result = new ArrayList<>();
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
            int hour = provider.getStartTime();
            for (int i = 0; i < provider.getHoursOpen(); i++) {
                for(int j = 0; j < provider.getTimeSlotsPerHour(); j++){
                    LocalDateTime dateTime = LocalDateTime.of(year, month, day, i + hour , j*(60/provider.getTimeSlotsPerHour()));
                    if(!result.contains(dateTime)){
                        result.add(dateTime);
                    }
                }
                for(Appointment appts : provider.getAppointments()){
                    result.remove(appts.getStartTime());
                    if(appts.getStartTime() != null && appts.getEndTime() != null)
                    result.removeIf(localDateTime -> (localDateTime.isAfter(appts.getStartTime()) && localDateTime.isBefore(appts.getEndTime())));
                }
                result.removeIf(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));
                for(DayOfWeek dow : provider.getDaysOff())
                result.removeIf(localDateTime -> localDateTime.getDayOfWeek().equals(dow));
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
        if(update.getNotes() != null)
            appointment.setNotes(update.getNotes());
        if(update.getClient() != null)
            appointment.setClient(update.getClient());
        if(update.getProvider() != null)
            appointment.setProvider(update.getProvider());
        if(update.getStartTime() != null)
            appointment.setStartTime(update.getStartTime());
        if(update.getService() != null)
            appointment.setService(update.getService());

        if(update.getEndTime() != null)
            appointment.setEndTime(update.getEndTime());
        else{
            Service service = serviceRepository.findById(appointment.getService().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            int hour = 0;
            int time = service.getLengthInMinutes();
            while( time >= 60){
                hour += 1;
                time -= 60;
            }
            appointment.setEndTime(appointment.getStartTime().plusHours(hour));
            appointment.setEndTime(appointment.getEndTime().plusMinutes(time));
        }

        return appointmentRepository.save(appointment);
    }

    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id){
        Appointment appt = appointmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        appointmentRepository.deleteById(id);
        return "Appointment was deleted";

    }
}
