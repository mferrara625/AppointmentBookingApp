package com.mferrara.AppointmentBookingApp.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
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
    //TODO : remove manytomany relationship between sProvider and client
    @ManyToMany
    @JoinTable(
            name = "serviceProvider_client",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "serviceProvider_id")
    )
    public Set<Client> clients;
    @OneToMany
    @JoinColumn(name = "serviceProvider_id", referencedColumnName = "id")
    private List<Appointment> appointments;

    public ServiceProvider(){

    }

    public ServiceProvider(String firstName, String lastName, String title, Set<Client> clients) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.clients = clients;
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
}
