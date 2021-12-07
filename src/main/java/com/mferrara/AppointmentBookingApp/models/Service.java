package com.mferrara.AppointmentBookingApp.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
public class Service {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer lengthInMinutes;
    @ManyToMany
    @JoinTable(
            name = "service_serviceProvider",
            joinColumns = @JoinColumn(name = "serviceProvider_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<ServiceProvider> serviceProviders;

    public Service(){}

    public Service(String name, String description, Integer price, Integer lengthInMinutes, Set<ServiceProvider> serviceProviders) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.lengthInMinutes = lengthInMinutes;
        this.serviceProviders = serviceProviders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(Integer lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public Set<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(Set<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }
}
