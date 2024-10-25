package com.il.qrgen.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String oib;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Ticket(){}
    public Ticket(String oib, String firstName, String lastName, LocalDateTime createdAt) {
        this.oib = oib;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }
    public void setOib(String oib) {
        this.oib = oib;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getOib() { return oib; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public UUID getId() {
        return id;
    }
}

