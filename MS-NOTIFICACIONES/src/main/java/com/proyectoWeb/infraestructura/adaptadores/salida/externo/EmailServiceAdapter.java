package com.proyectoWeb.infraestructura.adaptadores.salida.externo;

import com.proyectoWeb.dominio.puertos.salida.EmailServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class EmailServiceAdapter implements EmailServicePort {
	private final JavaMailSender mailSender;

	@Override
	public Mono<Void> enviarCorreo(String destinatario, String asunto, String mensaje) {
		return Mono.fromRunnable(() -> {
			try {
				System.out.println("SST-INFO: Intentando enviar correo a " + destinatario);

				SimpleMailMessage email = new SimpleMailMessage();

				email.setFrom("sistema@handfast.com");
				email.setTo(destinatario);
				email.setSubject(asunto);
				email.setText(mensaje);

				mailSender.send(email);

				System.out.println("SST-INFO: Correo enviado exitosamente a Mailtrap");
			} catch (Exception e) {
				System.err.println("SST-ERROR: Falló el envío de correo: " + e.getMessage());
				e.printStackTrace();
				throw e;
			}
		}).subscribeOn(Schedulers.boundedElastic()).then();
	}
}
