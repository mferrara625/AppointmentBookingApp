package com.mferrara.AppointmentBookingApp.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
public class ServiceProvider {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private Integer startTime = 9;
    private Integer hoursOpen = 8;
    private Integer timeSlotsPerHour = 2;
    @ElementCollection
    private List<DayOfWeek> daysOff;

    //TODO : remove manytomany relationship between sProvider and client
    @ManyToMany
    @JoinTable(
            name = "serviceProvider_client",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "serviceProvider_id")
    )
    public Set<Client> clients;
    @ManyToMany
    @JoinTable(
            name = "service_serviceProvider",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "serviceProvider_id")
    )
    private Set<Service> services;
    @OneToMany
    @JoinColumn(name = "serviceProvider_id", referencedColumnName = "id")
    private List<Appointment> appointments;

    public ServiceProvider(){

    }

    public ServiceProvider(String firstName, String lastName, String title, Integer startTime, Integer hoursOpen, Integer timeSlotsPerHour, List<DayOfWeek> daysOff, Set<Client> clients, Set<Service> services, List<Appointment> appointments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.startTime = startTime;
        this.hoursOpen = hoursOpen;
        this.timeSlotsPerHour = timeSlotsPerHour;
        this.daysOff = daysOff;
        this.clients = clients;
        this.services = services;
        this.appointments = appointments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getHoursOpen() {
        return hoursOpen;
    }

    public void setHoursOpen(Integer hoursOpen) {
        this.hoursOpen = hoursOpen;
    }

    public Integer getTimeSlotsPerHour() {
        return timeSlotsPerHour;
    }

    public void setTimeSlotsPerHour(Integer timeSlotsPerHour) {
        this.timeSlotsPerHour = timeSlotsPerHour;
    }

    public List<DayOfWeek> getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(List<DayOfWeek> daysOff) {
        this.daysOff = daysOff;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }
}
