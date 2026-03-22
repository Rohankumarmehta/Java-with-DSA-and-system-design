package com.hospital.model;

public class Doctor {
    private String doctorId;
    private String name;
    private String specialization;
    private String phone;
    private String department;
    private double consultationFee;
    private boolean isAvailable;

    public Doctor(String doctorId, String name, String specialization,
                  String phone, String department, double consultationFee) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.department = department;
        this.consultationFee = consultationFee;
        this.isAvailable = true;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return String.format(
            "| %-10s | %-20s | %-18s | %-12s | %-15s | %-10.2f | %-10s |",
            doctorId, name, specialization, phone, department,
            consultationFee, isAvailable ? "Yes" : "No"
        );
    }

    public static String getTableHeader() {
        return String.format(
            "| %-10s | %-20s | %-18s | %-12s | %-15s | %-10s | %-10s |",
            "ID", "Name", "Specialization", "Phone", "Department", "Fee", "Available"
        );
    }

    public static String getTableSeparator() {
        return "+------------+----------------------+--------------------+--------------+-----------------+------------+------------+";
    }
}
