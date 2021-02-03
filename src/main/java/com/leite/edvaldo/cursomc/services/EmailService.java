package com.leite.edvaldo.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.leite.edvaldo.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
