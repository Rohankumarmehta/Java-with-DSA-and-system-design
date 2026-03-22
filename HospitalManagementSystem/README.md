# Hospital Management System

A comprehensive console-based Hospital Management System built with Java. This system helps manage patients, doctors, appointments, and billing in a hospital environment.

## Features

### 1. Patient Management
- Add new patients with details (name, age, gender, phone, address, blood group)
- View all patients in a formatted table
- Search patients by name
- View detailed patient information
- Update patient details
- Discharge patients
- Delete patient records

### 2. Doctor Management
- Add new doctors with specialization and department
- View all doctors
- Search doctors by specialization or department
- Update doctor information
- Toggle doctor availability
- Delete doctor records

### 3. Appointment Management
- Schedule appointments between patients and doctors
- View all appointments
- View appointments by patient or doctor
- Mark appointments as completed
- Cancel appointments

### 4. Billing Management
- Generate itemized bills (consultation, medicine, room, operation charges)
- View all bills with summary
- View bills by patient
- View detailed bill breakdown
- Mark bills as paid
- Track unpaid bills

### 5. Dashboard
- Overview of total and admitted patients
- Doctor availability statistics
- Appointment tracking
- Revenue and pending payment summary

## Project Structure

```
HospitalManagementSystem/
├── src/
│   └── com/
│       └── hospital/
│           ├── model/
│           │   ├── Patient.java
│           │   ├── Doctor.java
│           │   ├── Appointment.java
│           │   └── Bill.java
│           ├── service/
│           │   ├── PatientService.java
│           │   ├── DoctorService.java
│           │   ├── AppointmentService.java
│           │   └── BillingService.java
│           ├── util/
│           │   └── IDGenerator.java
│           └── HospitalManagementSystem.java
└── README.md
```

## How to Run

### Compile
```bash
cd HospitalManagementSystem
javac -d out src/com/hospital/util/*.java src/com/hospital/model/*.java src/com/hospital/service/*.java src/com/hospital/HospitalManagementSystem.java
```

### Run
```bash
java -cp out com.hospital.HospitalManagementSystem
```

## Sample Data

The application comes preloaded with sample data:
- **5 Doctors** across different specializations (Cardiology, Orthopedics, Neurology, Pediatrics, General Medicine)
- **3 Patients** with varied profiles

## Requirements

- Java 17 or higher
