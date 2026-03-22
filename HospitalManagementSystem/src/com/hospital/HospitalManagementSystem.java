package com.hospital;

import com.hospital.model.*;
import com.hospital.service.*;
import com.hospital.util.IDGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HospitalManagementSystem {
    private final Scanner scanner;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final BillingService billingService;

    public HospitalManagementSystem() {
        IDGenerator idGenerator = new IDGenerator();
        this.scanner = new Scanner(System.in);
        this.patientService = new PatientService(idGenerator);
        this.doctorService = new DoctorService(idGenerator);
        this.appointmentService = new AppointmentService(idGenerator);
        this.billingService = new BillingService(idGenerator);
    }

    public static void main(String[] args) {
        HospitalManagementSystem system = new HospitalManagementSystem();
        system.loadSampleData();
        system.run();
    }

    private void loadSampleData() {
        doctorService.addDoctor("Dr. Sharma", "Cardiology", "9876543210", "Cardiology", 500.0);
        doctorService.addDoctor("Dr. Patel", "Orthopedics", "9876543211", "Orthopedics", 600.0);
        doctorService.addDoctor("Dr. Gupta", "Neurology", "9876543212", "Neurology", 700.0);
        doctorService.addDoctor("Dr. Singh", "Pediatrics", "9876543213", "Pediatrics", 400.0);
        doctorService.addDoctor("Dr. Kumar", "General Medicine", "9876543214", "General", 300.0);

        patientService.addPatient("Rahul Verma", 35, "Male", "9123456780", "Delhi", "B+");
        patientService.addPatient("Priya Mehta", 28, "Female", "9123456781", "Mumbai", "A+");
        patientService.addPatient("Amit Joshi", 45, "Male", "9123456782", "Pune", "O+");
    }

    public void run() {
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> managePatients();
                case 2 -> manageDoctors();
                case 3 -> manageAppointments();
                case 4 -> manageBilling();
                case 5 -> showDashboard();
                case 0 -> {
                    running = false;
                    System.out.println("\n Thank you for using Hospital Management System!");
                    System.out.println(" Goodbye!\n");
                }
                default -> System.out.println("\n [!] Invalid choice. Please try again.\n");
            }
        }
        scanner.close();
    }

    private void showMainMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║       HOSPITAL MANAGEMENT SYSTEM                 ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║                                                  ║");
        System.out.println("║   1. Patient Management                          ║");
        System.out.println("║   2. Doctor Management                           ║");
        System.out.println("║   3. Appointment Management                      ║");
        System.out.println("║   4. Billing Management                          ║");
        System.out.println("║   5. Dashboard                                   ║");
        System.out.println("║   0. Exit                                        ║");
        System.out.println("║                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    // ======================== PATIENT MANAGEMENT ========================

    private void managePatients() {
        boolean back = false;
        while (!back) {
            System.out.println("\n┌──────────────────────────────────────────────────┐");
            System.out.println("│              PATIENT MANAGEMENT                   │");
            System.out.println("├──────────────────────────────────────────────────┤");
            System.out.println("│  1. Add New Patient                               │");
            System.out.println("│  2. View All Patients                             │");
            System.out.println("│  3. Search Patient by Name                        │");
            System.out.println("│  4. View Patient Details                          │");
            System.out.println("│  5. Update Patient Info                           │");
            System.out.println("│  6. Discharge Patient                             │");
            System.out.println("│  7. Delete Patient Record                         │");
            System.out.println("│  0. Back to Main Menu                             │");
            System.out.println("└──────────────────────────────────────────────────┘");

            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addPatient();
                case 2 -> viewAllPatients();
                case 3 -> searchPatientByName();
                case 4 -> viewPatientDetails();
                case 5 -> updatePatient();
                case 6 -> dischargePatient();
                case 7 -> deletePatient();
                case 0 -> back = true;
                default -> System.out.println("\n [!] Invalid choice.\n");
            }
        }
    }

    private void addPatient() {
        System.out.println("\n--- Add New Patient ---");
        String name = readString("Enter patient name: ");
        int age = readInt("Enter age: ");
        String gender = readString("Enter gender (Male/Female/Other): ");
        String phone = readString("Enter phone number: ");
        String address = readString("Enter address: ");
        String bloodGroup = readString("Enter blood group: ");

        Patient patient = patientService.addPatient(name, age, gender, phone, address, bloodGroup);
        System.out.println("\n [+] Patient added successfully!");
        System.out.println(" Patient ID: " + patient.getPatientId());
    }

    private void viewAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("\n [!] No patients found.");
            return;
        }
        System.out.println("\n--- All Patients ---");
        System.out.println(Patient.getTableSeparator());
        System.out.println(Patient.getTableHeader());
        System.out.println(Patient.getTableSeparator());
        patients.forEach(System.out::println);
        System.out.println(Patient.getTableSeparator());
        System.out.println("Total: " + patients.size() + " patients");
    }

    private void searchPatientByName() {
        String name = readString("Enter patient name to search: ");
        List<Patient> results = patientService.searchPatientsByName(name);
        if (results.isEmpty()) {
            System.out.println("\n [!] No patients found with name: " + name);
            return;
        }
        System.out.println("\n--- Search Results ---");
        System.out.println(Patient.getTableSeparator());
        System.out.println(Patient.getTableHeader());
        System.out.println(Patient.getTableSeparator());
        results.forEach(System.out::println);
        System.out.println(Patient.getTableSeparator());
    }

    private void viewPatientDetails() {
        String id = readString("Enter patient ID: ");
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isEmpty()) {
            System.out.println("\n [!] Patient not found with ID: " + id);
            return;
        }
        Patient p = patientOpt.get();
        System.out.println("\n--- Patient Details ---");
        System.out.println("  ID          : " + p.getPatientId());
        System.out.println("  Name        : " + p.getName());
        System.out.println("  Age         : " + p.getAge());
        System.out.println("  Gender      : " + p.getGender());
        System.out.println("  Phone       : " + p.getPhone());
        System.out.println("  Address     : " + p.getAddress());
        System.out.println("  Blood Group : " + p.getBloodGroup());
        System.out.println("  Admitted On : " + p.getAdmissionDate());
        System.out.println("  Is Admitted : " + (p.isAdmitted() ? "Yes" : "No"));
    }

    private void updatePatient() {
        String id = readString("Enter patient ID to update: ");
        if (patientService.getPatientById(id).isEmpty()) {
            System.out.println("\n [!] Patient not found.");
            return;
        }
        System.out.println("(Press Enter to skip a field)");
        String name = readString("New name: ");
        String phone = readString("New phone: ");
        String address = readString("New address: ");

        if (patientService.updatePatient(id, name, phone, address)) {
            System.out.println("\n [+] Patient updated successfully!");
        }
    }

    private void dischargePatient() {
        String id = readString("Enter patient ID to discharge: ");
        if (patientService.dischargePatient(id)) {
            System.out.println("\n [+] Patient discharged successfully!");
        } else {
            System.out.println("\n [!] Patient not found.");
        }
    }

    private void deletePatient() {
        String id = readString("Enter patient ID to delete: ");
        String confirm = readString("Are you sure? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            if (patientService.deletePatient(id)) {
                System.out.println("\n [+] Patient record deleted.");
            } else {
                System.out.println("\n [!] Patient not found.");
            }
        } else {
            System.out.println(" Cancelled.");
        }
    }

    // ======================== DOCTOR MANAGEMENT ========================

    private void manageDoctors() {
        boolean back = false;
        while (!back) {
            System.out.println("\n┌──────────────────────────────────────────────────┐");
            System.out.println("│              DOCTOR MANAGEMENT                    │");
            System.out.println("├──────────────────────────────────────────────────┤");
            System.out.println("│  1. Add New Doctor                                │");
            System.out.println("│  2. View All Doctors                              │");
            System.out.println("│  3. Search by Specialization                      │");
            System.out.println("│  4. Search by Department                          │");
            System.out.println("│  5. Update Doctor Info                            │");
            System.out.println("│  6. Toggle Doctor Availability                    │");
            System.out.println("│  7. Delete Doctor Record                          │");
            System.out.println("│  0. Back to Main Menu                             │");
            System.out.println("└──────────────────────────────────────────────────┘");

            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> viewAllDoctors();
                case 3 -> searchDoctorBySpecialization();
                case 4 -> searchDoctorByDepartment();
                case 5 -> updateDoctor();
                case 6 -> toggleDoctorAvailability();
                case 7 -> deleteDoctor();
                case 0 -> back = true;
                default -> System.out.println("\n [!] Invalid choice.\n");
            }
        }
    }

    private void addDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        String name = readString("Enter doctor name: ");
        String specialization = readString("Enter specialization: ");
        String phone = readString("Enter phone number: ");
        String department = readString("Enter department: ");
        double fee = readDouble("Enter consultation fee: ");

        Doctor doctor = doctorService.addDoctor(name, specialization, phone, department, fee);
        System.out.println("\n [+] Doctor added successfully!");
        System.out.println(" Doctor ID: " + doctor.getDoctorId());
    }

    private void viewAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("\n [!] No doctors found.");
            return;
        }
        System.out.println("\n--- All Doctors ---");
        System.out.println(Doctor.getTableSeparator());
        System.out.println(Doctor.getTableHeader());
        System.out.println(Doctor.getTableSeparator());
        doctors.forEach(System.out::println);
        System.out.println(Doctor.getTableSeparator());
        System.out.println("Total: " + doctors.size() + " doctors");
    }

    private void searchDoctorBySpecialization() {
        String spec = readString("Enter specialization to search: ");
        List<Doctor> results = doctorService.searchDoctorsBySpecialization(spec);
        if (results.isEmpty()) {
            System.out.println("\n [!] No doctors found with specialization: " + spec);
            return;
        }
        System.out.println("\n--- Search Results ---");
        System.out.println(Doctor.getTableSeparator());
        System.out.println(Doctor.getTableHeader());
        System.out.println(Doctor.getTableSeparator());
        results.forEach(System.out::println);
        System.out.println(Doctor.getTableSeparator());
    }

    private void searchDoctorByDepartment() {
        String dept = readString("Enter department to search: ");
        List<Doctor> results = doctorService.searchDoctorsByDepartment(dept);
        if (results.isEmpty()) {
            System.out.println("\n [!] No doctors found in department: " + dept);
            return;
        }
        System.out.println("\n--- Search Results ---");
        System.out.println(Doctor.getTableSeparator());
        System.out.println(Doctor.getTableHeader());
        System.out.println(Doctor.getTableSeparator());
        results.forEach(System.out::println);
        System.out.println(Doctor.getTableSeparator());
    }

    private void updateDoctor() {
        String id = readString("Enter doctor ID to update: ");
        if (doctorService.getDoctorById(id).isEmpty()) {
            System.out.println("\n [!] Doctor not found.");
            return;
        }
        System.out.println("(Press Enter to skip a field, enter 0 to skip fee)");
        String phone = readString("New phone: ");
        double fee = readDouble("New consultation fee (0 to skip): ");

        if (doctorService.updateDoctor(id, phone, fee)) {
            System.out.println("\n [+] Doctor updated successfully!");
        }
    }

    private void toggleDoctorAvailability() {
        String id = readString("Enter doctor ID: ");
        if (doctorService.toggleAvailability(id)) {
            Optional<Doctor> doc = doctorService.getDoctorById(id);
            doc.ifPresent(d ->
                System.out.println("\n [+] Dr. " + d.getName() + " is now " +
                    (d.isAvailable() ? "Available" : "Unavailable"))
            );
        } else {
            System.out.println("\n [!] Doctor not found.");
        }
    }

    private void deleteDoctor() {
        String id = readString("Enter doctor ID to delete: ");
        String confirm = readString("Are you sure? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            if (doctorService.deleteDoctor(id)) {
                System.out.println("\n [+] Doctor record deleted.");
            } else {
                System.out.println("\n [!] Doctor not found.");
            }
        } else {
            System.out.println(" Cancelled.");
        }
    }

    // ======================== APPOINTMENT MANAGEMENT ========================

    private void manageAppointments() {
        boolean back = false;
        while (!back) {
            System.out.println("\n┌──────────────────────────────────────────────────┐");
            System.out.println("│           APPOINTMENT MANAGEMENT                  │");
            System.out.println("├──────────────────────────────────────────────────┤");
            System.out.println("│  1. Schedule New Appointment                      │");
            System.out.println("│  2. View All Appointments                         │");
            System.out.println("│  3. View Patient Appointments                     │");
            System.out.println("│  4. View Doctor Appointments                      │");
            System.out.println("│  5. Complete Appointment                          │");
            System.out.println("│  6. Cancel Appointment                            │");
            System.out.println("│  0. Back to Main Menu                             │");
            System.out.println("└──────────────────────────────────────────────────┘");

            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> scheduleAppointment();
                case 2 -> viewAllAppointments();
                case 3 -> viewPatientAppointments();
                case 4 -> viewDoctorAppointments();
                case 5 -> completeAppointment();
                case 6 -> cancelAppointment();
                case 0 -> back = true;
                default -> System.out.println("\n [!] Invalid choice.\n");
            }
        }
    }

    private void scheduleAppointment() {
        System.out.println("\n--- Schedule New Appointment ---");

        viewAllPatients();
        String patientId = readString("Enter patient ID: ");
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        if (patientOpt.isEmpty()) {
            System.out.println("\n [!] Patient not found.");
            return;
        }

        viewAllDoctors();
        String doctorId = readString("Enter doctor ID: ");
        Optional<Doctor> doctorOpt = doctorService.getDoctorById(doctorId);
        if (doctorOpt.isEmpty()) {
            System.out.println("\n [!] Doctor not found.");
            return;
        }

        if (!doctorOpt.get().isAvailable()) {
            System.out.println("\n [!] Doctor is currently unavailable.");
            return;
        }

        String dateStr = readString("Enter date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime dateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("\n [!] Invalid date format. Please use yyyy-MM-dd HH:mm");
            return;
        }

        String description = readString("Enter description/reason: ");

        Appointment appointment = appointmentService.scheduleAppointment(
                patientId, doctorId,
                patientOpt.get().getName(), doctorOpt.get().getName(),
                dateTime, description
        );
        System.out.println("\n [+] Appointment scheduled successfully!");
        System.out.println(" Appointment ID: " + appointment.getAppointmentId());
    }

    private void viewAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("\n [!] No appointments found.");
            return;
        }
        System.out.println("\n--- All Appointments ---");
        System.out.println(Appointment.getTableSeparator());
        System.out.println(Appointment.getTableHeader());
        System.out.println(Appointment.getTableSeparator());
        appointments.forEach(System.out::println);
        System.out.println(Appointment.getTableSeparator());
    }

    private void viewPatientAppointments() {
        String patientId = readString("Enter patient ID: ");
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        if (appointments.isEmpty()) {
            System.out.println("\n [!] No appointments found for patient: " + patientId);
            return;
        }
        System.out.println("\n--- Patient Appointments ---");
        System.out.println(Appointment.getTableSeparator());
        System.out.println(Appointment.getTableHeader());
        System.out.println(Appointment.getTableSeparator());
        appointments.forEach(System.out::println);
        System.out.println(Appointment.getTableSeparator());
    }

    private void viewDoctorAppointments() {
        String doctorId = readString("Enter doctor ID: ");
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        if (appointments.isEmpty()) {
            System.out.println("\n [!] No appointments found for doctor: " + doctorId);
            return;
        }
        System.out.println("\n--- Doctor Appointments ---");
        System.out.println(Appointment.getTableSeparator());
        System.out.println(Appointment.getTableHeader());
        System.out.println(Appointment.getTableSeparator());
        appointments.forEach(System.out::println);
        System.out.println(Appointment.getTableSeparator());
    }

    private void completeAppointment() {
        String id = readString("Enter appointment ID to mark as completed: ");
        if (appointmentService.completeAppointment(id)) {
            System.out.println("\n [+] Appointment marked as completed.");
        } else {
            System.out.println("\n [!] Appointment not found.");
        }
    }

    private void cancelAppointment() {
        String id = readString("Enter appointment ID to cancel: ");
        if (appointmentService.cancelAppointment(id)) {
            System.out.println("\n [+] Appointment cancelled.");
        } else {
            System.out.println("\n [!] Appointment not found.");
        }
    }

    // ======================== BILLING MANAGEMENT ========================

    private void manageBilling() {
        boolean back = false;
        while (!back) {
            System.out.println("\n┌──────────────────────────────────────────────────┐");
            System.out.println("│             BILLING MANAGEMENT                    │");
            System.out.println("├──────────────────────────────────────────────────┤");
            System.out.println("│  1. Generate New Bill                             │");
            System.out.println("│  2. View All Bills                                │");
            System.out.println("│  3. View Patient Bills                            │");
            System.out.println("│  4. View Bill Details                             │");
            System.out.println("│  5. Mark Bill as Paid                             │");
            System.out.println("│  6. View Unpaid Bills                             │");
            System.out.println("│  0. Back to Main Menu                             │");
            System.out.println("└──────────────────────────────────────────────────┘");

            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> generateBill();
                case 2 -> viewAllBills();
                case 3 -> viewPatientBills();
                case 4 -> viewBillDetails();
                case 5 -> markBillAsPaid();
                case 6 -> viewUnpaidBills();
                case 0 -> back = true;
                default -> System.out.println("\n [!] Invalid choice.\n");
            }
        }
    }

    private void generateBill() {
        System.out.println("\n--- Generate New Bill ---");
        viewAllPatients();
        String patientId = readString("Enter patient ID: ");
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        if (patientOpt.isEmpty()) {
            System.out.println("\n [!] Patient not found.");
            return;
        }

        double consultation = readDouble("Enter consultation charge: ");
        double medicine = readDouble("Enter medicine charge: ");
        double room = readDouble("Enter room charge: ");
        double operation = readDouble("Enter operation charge: ");

        Bill bill = billingService.generateBill(patientId, patientOpt.get().getName(),
                consultation, medicine, room, operation);
        System.out.println("\n [+] Bill generated successfully!");
        System.out.println(bill.getDetailedBill());
    }

    private void viewAllBills() {
        List<Bill> bills = billingService.getAllBills();
        if (bills.isEmpty()) {
            System.out.println("\n [!] No bills found.");
            return;
        }
        System.out.println("\n--- All Bills ---");
        System.out.println(Bill.getTableSeparator());
        System.out.println(Bill.getTableHeader());
        System.out.println(Bill.getTableSeparator());
        bills.forEach(System.out::println);
        System.out.println(Bill.getTableSeparator());
    }

    private void viewPatientBills() {
        String patientId = readString("Enter patient ID: ");
        List<Bill> bills = billingService.getBillsByPatient(patientId);
        if (bills.isEmpty()) {
            System.out.println("\n [!] No bills found for patient: " + patientId);
            return;
        }
        System.out.println("\n--- Patient Bills ---");
        System.out.println(Bill.getTableSeparator());
        System.out.println(Bill.getTableHeader());
        System.out.println(Bill.getTableSeparator());
        bills.forEach(System.out::println);
        System.out.println(Bill.getTableSeparator());
    }

    private void viewBillDetails() {
        String billId = readString("Enter bill ID: ");
        Optional<Bill> billOpt = billingService.getBillById(billId);
        if (billOpt.isEmpty()) {
            System.out.println("\n [!] Bill not found.");
            return;
        }
        System.out.println(billOpt.get().getDetailedBill());
    }

    private void markBillAsPaid() {
        String billId = readString("Enter bill ID to mark as paid: ");
        if (billingService.markAsPaid(billId)) {
            System.out.println("\n [+] Bill marked as paid.");
        } else {
            System.out.println("\n [!] Bill not found.");
        }
    }

    private void viewUnpaidBills() {
        List<Bill> unpaidBills = billingService.getUnpaidBills();
        if (unpaidBills.isEmpty()) {
            System.out.println("\n [+] No unpaid bills! All bills are cleared.");
            return;
        }
        System.out.println("\n--- Unpaid Bills ---");
        System.out.println(Bill.getTableSeparator());
        System.out.println(Bill.getTableHeader());
        System.out.println(Bill.getTableSeparator());
        unpaidBills.forEach(System.out::println);
        System.out.println(Bill.getTableSeparator());
        System.out.printf("\n Total Pending: %.2f\n", billingService.getTotalPending());
    }

    // ======================== DASHBOARD ========================

    private void showDashboard() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║              HOSPITAL DASHBOARD                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf("║  Total Patients       : %-24d ║\n", patientService.getTotalPatients());
        System.out.printf("║  Admitted Patients    : %-24d ║\n", patientService.getAdmittedPatients());
        System.out.printf("║  Total Doctors        : %-24d ║\n", doctorService.getTotalDoctors());
        System.out.printf("║  Available Doctors    : %-24d ║\n", doctorService.getAvailableDoctors());
        System.out.printf("║  Total Appointments   : %-24d ║\n", appointmentService.getTotalAppointments());
        System.out.printf("║  Active Appointments  : %-24d ║\n", appointmentService.getScheduledAppointments());
        System.out.printf("║  Total Bills          : %-24d ║\n", billingService.getTotalBills());
        System.out.printf("║  Total Revenue        : %-24.2f ║\n", billingService.getTotalRevenue());
        System.out.printf("║  Pending Payments     : %-24.2f ║\n", billingService.getTotalPending());
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    // ======================== UTILITY METHODS ========================

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println(" [!] Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println(" [!] Please enter a valid number.");
            }
        }
    }
}
