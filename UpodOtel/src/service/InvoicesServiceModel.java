package service;

import model.Customer;
import model.Reservations;
import model.Services;

import java.time.LocalDateTime;
import java.util.Date;

public class InvoicesServiceModel {
    private int invoiceId;
    private int reservationId;
    private int customerId;
    private double totalAmount;
    private Date date;
    private int serviceId;


    public InvoicesServiceModel(int invoiceId, int reservationId, int customerId, double totalAmount, Date date, int serviceId) {
        this.invoiceId = invoiceId;
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.date = date;
        this.serviceId = serviceId;

    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getReservation() {
        return reservationId;
    }

    public void setReservation(Reservations reservation) {
        this.reservationId = reservationId;
    }

    public int getCustomer() {
        return customerId;
    }

    public void setCustomer(Customer customer) {
        this.customerId = customerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;

    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getServices() {
        return serviceId;
    }

    public void setServices(Services services) {
        this.serviceId = serviceId;
    }
}


