package com.mferrara.AppointmentBookingApp.repositories;

import com.mferrara.AppointmentBookingApp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
