package com.hospital.service;

import com.hospital.model.Doctor;
import com.hospital.util.IDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DoctorService {
    private final List<Doctor> doctors;
    private final IDGenerator idGenerator;

    public DoctorService(IDGenerator idGenerator) {
        this.doctors = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    public Doctor addDoctor(String name, String specialization, String phone,
                            String department, double consultationFee) {
        String id = idGenerator.generateDoctorId();
        Doctor doctor = new Doctor(id, name, specialization, phone, department, consultationFee);
        doctors.add(doctor);
        return doctor;
    }

    public Optional<Doctor> getDoctorById(String doctorId) {
        return doctors.stream()
                .filter(d -> d.getDoctorId().equalsIgnoreCase(doctorId))
                .findFirst();
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }

    public List<Doctor> searchDoctorsBySpecialization(String specialization) {
        return doctors.stream()
                .filter(d -> d.getSpecialization().toLowerCase().contains(specialization.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Doctor> searchDoctorsByDepartment(String department) {
        return doctors.stream()
                .filter(d -> d.getDepartment().toLowerCase().contains(department.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean updateDoctor(String doctorId, String phone, double consultationFee) {
        Optional<Doctor> doctorOpt = getDoctorById(doctorId);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (phone != null && !phone.isEmpty()) doctor.setPhone(phone);
            if (consultationFee > 0) doctor.setConsultationFee(consultationFee);
            return true;
        }
        return false;
    }

    public boolean toggleAvailability(String doctorId) {
        Optional<Doctor> doctorOpt = getDoctorById(doctorId);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            doctor.setAvailable(!doctor.isAvailable());
            return true;
        }
        return false;
    }

    public boolean deleteDoctor(String doctorId) {
        return doctors.removeIf(d -> d.getDoctorId().equalsIgnoreCase(doctorId));
    }

    public int getTotalDoctors() {
        return doctors.size();
    }

    public long getAvailableDoctors() {
        return doctors.stream().filter(Doctor::isAvailable).count();
    }
}
