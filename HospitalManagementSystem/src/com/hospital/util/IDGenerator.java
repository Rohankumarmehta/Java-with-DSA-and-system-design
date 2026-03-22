package com.hospital.util;

public class IDGenerator {
    private int patientCounter = 0;
    private int doctorCounter = 0;
    private int appointmentCounter = 0;
    private int billCounter = 0;

    public String generatePatientId() {
        patientCounter++;
        return String.format("PAT-%04d", patientCounter);
    }

    public String generateDoctorId() {
        doctorCounter++;
        return String.format("DOC-%04d", doctorCounter);
    }

    public String generateAppointmentId() {
        appointmentCounter++;
        return String.format("APT-%04d", appointmentCounter);
    }

    public String generateBillId() {
        billCounter++;
        return String.format("BIL-%04d", billCounter);
    }
}
