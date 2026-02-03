package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async // Esto hace que el hilo principal no se bloquee enviando el mail
    public void enviarCorreoReserva(String destinatario, String remitente, String fecha, String hora) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(remitente);
        message.setTo(destinatario);
        message.setSubject("Confirmación de Reserva");
        message.setText("Hola!\n" +
                "Tu reserva ha sido confirmada para el día " + fecha + " a las " + hora + ".\n\n" +
                "¡Gracias por elegirnos!\n\n" +
                "Saludos,\n" +
                "El equipo de la Peluquería");

        javaMailSender.send(message);
        System.out.println("Correo enviado a " + destinatario +"desde " + remitente);
    }
}
