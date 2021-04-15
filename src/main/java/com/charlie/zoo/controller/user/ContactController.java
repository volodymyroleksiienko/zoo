package com.charlie.zoo.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class ContactController {
    private JavaMailSender javaMailSender;

    @ResponseBody
    @PostMapping("/sendCallback")
    public void sendCallback(String name, String phone, String message){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("chinchillareal@gmail.com");
        msg.setSubject("Charlie zoo: Заявка на зворотній зв'язок");
        msg.setText("Ім'я: "+name+"\nТелефон: "+phone+"\nПовідомлення: "+message);
        javaMailSender.send(msg);
    }
}
