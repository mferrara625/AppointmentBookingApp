package com.mferrara.AppointmentBookingApp.repositories;

import com.mferrara.AppointmentBookingApp.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
