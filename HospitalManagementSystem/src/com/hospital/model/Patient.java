package com.hospital.model;

import java.time.LocalDate;

public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String address;
    private String bloodGroup;
    private LocalDate admissionDate;
    private boolean isAdmitted;

    public Patient(String patientId, String name, int age, String gender,
                   String phone, String address, String bloodGroup) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.admissionDate = LocalDate.now();
        this.isAdmitted = true;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public boolean isAdmitted() {
        return isAdmitted;
    }

    public void setAdmitted(boolean admitted) {
        isAdmitted = admitted;
    }

    @Override
    public String toString() {
        return String.format(
            "| %-10s | %-20s | %-4d | %-6s | %-12s | %-8s | %-12s | %-10s |",
            patientId, name, age, gender, phone, bloodGroup,
            admissionDate, isAdmitted ? "Yes" : "No"
        );
    }

    public static String getTableHeader() {
        return String.format(
            "| %-10s | %-20s | %-4s | %-6s | %-12s | %-8s | %-12s | %-10s |",
            "ID", "Name", "Age", "Gender", "Phone", "Blood", "Admitted", "Active"
        );
    }

    public static String getTableSeparator() {
        return "+------------+----------------------+------+--------+--------------+----------+--------------+------------+";
    }
}
