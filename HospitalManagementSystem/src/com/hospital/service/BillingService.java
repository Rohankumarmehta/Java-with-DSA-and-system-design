package com.hospital.service;

import com.hospital.model.Bill;
import com.hospital.util.IDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BillingService {
    private final List<Bill> bills;
    private final IDGenerator idGenerator;

    public BillingService(IDGenerator idGenerator) {
        this.bills = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    public Bill generateBill(String patientId, String patientName,
                             double consultationCharge, double medicineCharge,
                             double roomCharge, double operationCharge) {
        String id = idGenerator.generateBillId();
        Bill bill = new Bill(id, patientId, patientName, consultationCharge,
                medicineCharge, roomCharge, operationCharge);
        bills.add(bill);
        return bill;
    }

    public Optional<Bill> getBillById(String billId) {
        return bills.stream()
                .filter(b -> b.getBillId().equalsIgnoreCase(billId))
                .findFirst();
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>(bills);
    }

    public List<Bill> getBillsByPatient(String patientId) {
        return bills.stream()
                .filter(b -> b.getPatientId().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }

    public boolean markAsPaid(String billId) {
        Optional<Bill> billOpt = getBillById(billId);
        if (billOpt.isPresent()) {
            billOpt.get().setPaymentStatus(Bill.PaymentStatus.PAID);
            return true;
        }
        return false;
    }

    public List<Bill> getUnpaidBills() {
        return bills.stream()
                .filter(b -> b.getPaymentStatus() == Bill.PaymentStatus.UNPAID)
                .collect(Collectors.toList());
    }

    public double getTotalRevenue() {
        return bills.stream()
                .filter(b -> b.getPaymentStatus() == Bill.PaymentStatus.PAID)
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }

    public double getTotalPending() {
        return bills.stream()
                .filter(b -> b.getPaymentStatus() == Bill.PaymentStatus.UNPAID)
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }

    public int getTotalBills() {
        return bills.size();
    }
}
