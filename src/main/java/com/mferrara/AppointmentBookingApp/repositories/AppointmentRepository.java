package com.mferrara.AppointmentBookingApp.repositories;

import com.mferrara.AppointmentBookingApp.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
