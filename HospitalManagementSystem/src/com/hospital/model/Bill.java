package com.hospital.model;

import java.time.LocalDate;

public class Bill {
    public enum PaymentStatus {
        UNPAID, PAID
    }

    private String billId;
    private String patientId;
    private String patientName;
    private double consultationCharge;
    private double medicineCharge;
    private double roomCharge;
    private double operationCharge;
    private double totalAmount;
    private LocalDate billDate;
    private PaymentStatus paymentStatus;

    public Bill(String billId, String patientId, String patientName,
                double consultationCharge, double medicineCharge,
                double roomCharge, double operationCharge) {
        this.billId = billId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.consultationCharge = consultationCharge;
        this.medicineCharge = medicineCharge;
        this.roomCharge = roomCharge;
        this.operationCharge = operationCharge;
        this.totalAmount = consultationCharge + medicineCharge + roomCharge + operationCharge;
        this.billDate = LocalDate.now();
        this.paymentStatus = PaymentStatus.UNPAID;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public double getConsultationCharge() {
        return consultationCharge;
    }

    public void setConsultationCharge(double consultationCharge) {
        this.consultationCharge = consultationCharge;
        recalculateTotal();
    }

    public double getMedicineCharge() {
        return medicineCharge;
    }

    public void setMedicineCharge(double medicineCharge) {
        this.medicineCharge = medicineCharge;
        recalculateTotal();
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(double roomCharge) {
        this.roomCharge = roomCharge;
        recalculateTotal();
    }

    public double getOperationCharge() {
        return operationCharge;
    }

    public void setOperationCharge(double operationCharge) {
        this.operationCharge = operationCharge;
        recalculateTotal();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    private void recalculateTotal() {
        this.totalAmount = consultationCharge + medicineCharge + roomCharge + operationCharge;
    }

    @Override
    public String toString() {
        return String.format(
            "| %-10s | %-10s | %-15s | %-12.2f | %-12.2f | %-12.2f | %-12.2f | %-12.2f | %-10s | %-8s |",
            billId, patientId, patientName, consultationCharge,
            medicineCharge, roomCharge, operationCharge, totalAmount,
            billDate, paymentStatus
        );
    }

    public static String getTableHeader() {
        return String.format(
            "| %-10s | %-10s | %-15s | %-12s | %-12s | %-12s | %-12s | %-12s | %-10s | %-8s |",
            "Bill ID", "Patient", "Name", "Consult", "Medicine",
            "Room", "Operation", "Total", "Date", "Status"
        );
    }

    public static String getTableSeparator() {
        return "+------------+------------+-----------------+--------------+--------------+--------------+--------------+--------------+------------+----------+";
    }

    public String getDetailedBill() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════════════╗\n");
        sb.append("║              HOSPITAL BILL                       ║\n");
        sb.append("╠══════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  Bill ID       : %-30s ║\n", billId));
        sb.append(String.format("║  Patient ID    : %-30s ║\n", patientId));
        sb.append(String.format("║  Patient Name  : %-30s ║\n", patientName));
        sb.append(String.format("║  Bill Date     : %-30s ║\n", billDate));
        sb.append("╠══════════════════════════════════════════════════╣\n");
        sb.append("║  CHARGES BREAKDOWN                               ║\n");
        sb.append("╠══════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  Consultation  : ₹ %-28.2f ║\n", consultationCharge));
        sb.append(String.format("║  Medicine      : ₹ %-28.2f ║\n", medicineCharge));
        sb.append(String.format("║  Room          : ₹ %-28.2f ║\n", roomCharge));
        sb.append(String.format("║  Operation     : ₹ %-28.2f ║\n", operationCharge));
        sb.append("╠══════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  TOTAL AMOUNT  : ₹ %-28.2f ║\n", totalAmount));
        sb.append(String.format("║  STATUS        : %-30s ║\n", paymentStatus));
        sb.append("╚══════════════════════════════════════════════════╝\n");
        return sb.toString();
    }
}
