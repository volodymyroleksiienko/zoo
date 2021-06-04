package com.charlie.zoo;

import com.charlie.zoo.controller.bot.Bot;
import com.charlie.zoo.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableTransactionManagement
public class ZooApplication implements CommandLineRunner {

	@Autowired
	TelegramUserService  telegramUserService;

	@Bean
	public Bot getBot(){
		Bot bot = new Bot("62m6Ad0RPWXA/BZmKM2A5Q==",telegramUserService,"CharlieZooServiceBot", "1781017340:AAHbCNfdxCM63L5kyQyBU0BCUtEvsjXWBwE");
		bot.botConnect();
		return bot;
	}

	@Override
	public void run(String... args) throws Exception {
		getBot();
	}

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(ZooApplication.class, args);
	}

}
