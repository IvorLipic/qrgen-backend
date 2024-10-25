package com.il.qrgen.dto;
import java.time.LocalDateTime;
public class TicketResponse {
    private String oib;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    public TicketResponse(String oib, String firstName, String lastName, LocalDateTime createdAt) {
        this.oib = oib;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }
    public String getOib() { return oib; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

