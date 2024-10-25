package com.il.qrgen.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.il.qrgen.dto.TicketRequest;
import com.il.qrgen.repository.TicketRepository;
import com.il.qrgen.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public Ticket generateTicket(TicketRequest request) {
        String oib = request.getOib();

        // Check if the OIB has already generated 3 tickets
        long ticketCount = ticketRepository.countByOib(oib);
        if (ticketCount >= 3) {
            throw new IllegalArgumentException("Cannot generate more than 3 tickets for the given OIB.");
        }

        Ticket ticket = new Ticket();
        ticket.setOib(oib);
        ticket.setFirstName(request.getFirstName());
        ticket.setLastName(request.getLastName());
        ticket.setCreatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        return ticket;
    }
    public String generateQRCode(Ticket ticket) throws Exception {
        String ticketUrl = baseUrl + "/tickets/" + ticket.getId();  // Use base URL from environment variable

        ByteArrayOutputStream qrCodeStream = new ByteArrayOutputStream();
        BitMatrix bitMatrix = new QRCodeWriter().encode(ticketUrl, BarcodeFormat.QR_CODE, 300, 300);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qrCodeStream);

        byte[] qrCodeBytes = qrCodeStream.toByteArray();
        return Base64.getEncoder().encodeToString(qrCodeBytes); // Return as Base64 string
    }
    public long getTicketCount() {
        return ticketRepository.count();
    }
    public Ticket getTicketById(UUID ticketId) {
        return ticketRepository.findById(ticketId).orElse(null);
    }
}


