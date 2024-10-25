package com.il.qrgen.controllers;

import com.il.qrgen.dto.TicketRequest;
import com.il.qrgen.dto.TicketResponse;
import com.il.qrgen.models.Ticket;
import com.il.qrgen.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateTicket(@RequestBody TicketRequest request) {
        try {
            if (request.getOib() == null || request.getFirstName() == null || request.getLastName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data.");
            }

            Ticket ticket = ticketService.generateTicket(request);

            String qrCode = ticketService.generateQRCode(ticket);

            return ResponseEntity.ok().body(Collections.singletonMap("qrCode", qrCode));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the ticket.");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getTicketCount() {
        try {
            long count = ticketService.getTicketCount();
            return ResponseEntity.ok().body(Collections.singletonMap("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the ticket count.");
        }
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicketDetails(@PathVariable String ticketId) {
        try {
            Ticket ticket = ticketService.getTicketById(UUID.fromString(ticketId));

            if (ticket == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found.");
            }

            TicketResponse ticketResponse = new TicketResponse(
                    ticket.getOib(),
                    ticket.getFirstName(),
                    ticket.getLastName(),
                    ticket.getCreatedAt()
            );

            return ResponseEntity.ok().body(Collections.singletonMap("ticket", ticketResponse));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ticket ID format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the ticket details.");
        }
    }
}

