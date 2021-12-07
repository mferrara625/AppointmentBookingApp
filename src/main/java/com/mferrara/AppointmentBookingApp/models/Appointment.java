package com.mferrara.AppointmentBookingApp.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;
    private String notes;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToOne
    private Service service;
    @ManyToOne
    @JoinColumn(name = "serviceProvider_id", referencedColumnName = "id")
    private ServiceProvider provider;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Appointment(){}

    public Appointment(String notes, LocalDateTime startTime, LocalDateTime endTime, Service service, ServiceProvider provider, Client client) {
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.service = service;
        this.provider = provider;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() { return endTime; }

    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ServiceProvider getProvider() {
        return provider;
    }

    public void setProvider(ServiceProvider provider) {
        this.provider = provider;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
