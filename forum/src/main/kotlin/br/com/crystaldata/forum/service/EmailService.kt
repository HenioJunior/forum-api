package br.com.crystaldata.forum.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
) {

    fun notificar(emailUsuario: String) {
        val message =  SimpleMailMessage()

        message.setSubject("[Alura] Resposta Recebida")
        message.setText("Olá, o seu tópico foi respondido. Vamos lá conferir?")
        message.setTo(emailUsuario)

        javaMailSender.send(message)
    }
}