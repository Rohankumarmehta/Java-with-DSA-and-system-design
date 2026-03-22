package com.hospital.service;

import com.hospital.model.Patient;
import com.hospital.util.IDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService {
    private final List<Patient> patients;
    private final IDGenerator idGenerator;

    public PatientService(IDGenerator idGenerator) {
        this.patients = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    public Patient addPatient(String name, int age, String gender,
                              String phone, String address, String bloodGroup) {
        String id = idGenerator.generatePatientId();
        Patient patient = new Patient(id, name, age, gender, phone, address, bloodGroup);
        patients.add(patient);
        return patient;
    }

    public Optional<Patient> getPatientById(String patientId) {
        return patients.stream()
                .filter(p -> p.getPatientId().equalsIgnoreCase(patientId))
                .findFirst();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public List<Patient> searchPatientsByName(String name) {
        return patients.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean updatePatient(String patientId, String name, String phone, String address) {
        Optional<Patient> patientOpt = getPatientById(patientId);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (name != null && !name.isEmpty()) patient.setName(name);
            if (phone != null && !phone.isEmpty()) patient.setPhone(phone);
            if (address != null && !address.isEmpty()) patient.setAddress(address);
            return true;
        }
        return false;
    }

    public boolean dischargePatient(String patientId) {
        Optional<Patient> patientOpt = getPatientById(patientId);
        if (patientOpt.isPresent()) {
            patientOpt.get().setAdmitted(false);
            return true;
        }
        return false;
    }

    public boolean deletePatient(String patientId) {
        return patients.removeIf(p -> p.getPatientId().equalsIgnoreCase(patientId));
    }

    public int getTotalPatients() {
        return patients.size();
    }

    public long getAdmittedPatients() {
        return patients.stream().filter(Patient::isAdmitted).count();
    }
}
