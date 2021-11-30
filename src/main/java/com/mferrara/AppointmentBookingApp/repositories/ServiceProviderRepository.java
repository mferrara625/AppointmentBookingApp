package com.mferrara.AppointmentBookingApp.repositories;

import com.mferrara.AppointmentBookingApp.models.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

}
