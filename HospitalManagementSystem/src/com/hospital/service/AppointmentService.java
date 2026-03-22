package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.util.IDGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService {
    private final List<Appointment> appointments;
    private final IDGenerator idGenerator;

    public AppointmentService(IDGenerator idGenerator) {
        this.appointments = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    public Appointment scheduleAppointment(String patientId, String doctorId,
                                            String patientName, String doctorName,
                                            LocalDateTime dateTime, String description) {
        String id = idGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(id, patientId, doctorId,
                patientName, doctorName, dateTime, description);
        appointments.add(appointment);
        return appointment;
    }

    public Optional<Appointment> getAppointmentById(String appointmentId) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equalsIgnoreCase(appointmentId))
                .findFirst();
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equalsIgnoreCase(doctorId))
                .collect(Collectors.toList());
    }

    public boolean completeAppointment(String appointmentId) {
        Optional<Appointment> appointmentOpt = getAppointmentById(appointmentId);
        if (appointmentOpt.isPresent()) {
            appointmentOpt.get().setStatus(Appointment.Status.COMPLETED);
            return true;
        }
        return false;
    }

    public boolean cancelAppointment(String appointmentId) {
        Optional<Appointment> appointmentOpt = getAppointmentById(appointmentId);
        if (appointmentOpt.isPresent()) {
            appointmentOpt.get().setStatus(Appointment.Status.CANCELLED);
            return true;
        }
        return false;
    }

    public int getTotalAppointments() {
        return appointments.size();
    }

    public long getScheduledAppointments() {
        return appointments.stream()
                .filter(a -> a.getStatus() == Appointment.Status.SCHEDULED)
                .count();
    }
}
