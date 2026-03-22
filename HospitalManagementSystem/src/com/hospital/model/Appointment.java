package com.hospital.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED
    }

    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String patientName;
    private String doctorName;
    private LocalDateTime dateTime;
    private String description;
    private Status status;

    public Appointment(String appointmentId, String patientId, String doctorId,
                       String patientName, String doctorName,
                       LocalDateTime dateTime, String description) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.dateTime = dateTime;
        this.description = description;
        this.status = Status.SCHEDULED;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format(
            "| %-10s | %-15s | %-15s | %-18s | %-25s | %-10s |",
            appointmentId, patientName, doctorName,
            dateTime.format(formatter), description, status
        );
    }

    public static String getTableHeader() {
        return String.format(
            "| %-10s | %-15s | %-15s | %-18s | %-25s | %-10s |",
            "ID", "Patient", "Doctor", "Date & Time", "Description", "Status"
        );
    }

    public static String getTableSeparator() {
        return "+------------+-----------------+-----------------+--------------------+---------------------------+------------+";
    }
}
