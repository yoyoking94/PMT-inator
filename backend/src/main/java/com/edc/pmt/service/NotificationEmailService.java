package com.edc.pmt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationEmailService {

    private final JavaMailSender serviceEnvoiEmail;

    @Value("${spring.mail.username}")
    private String adresseEmailExpediteur;

    public NotificationEmailService(JavaMailSender serviceEnvoiEmail) {
        this.serviceEnvoiEmail = serviceEnvoiEmail;
    }

    public void envoyerEmailAssignation(String adresseEmailDestinataire,
            String nomTache,
            String nomProjet) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(adresseEmailExpediteur);
        message.setTo(adresseEmailDestinataire);
        message.setSubject("Nouvelle tâche assignée sur le projet : " + nomProjet);

        String texteMessage = """
                Bonjour,

                Une nouvelle tâche vous a été assignée sur le projet : %s

                Nom de la tâche : %s

                Merci de vous connecter à la plateforme pour consulter les détails.

                Cordialement,
                L'équipe PMT
                """.formatted(nomProjet, nomTache);

        message.setText(texteMessage);

        serviceEnvoiEmail.send(message);
    }
}
