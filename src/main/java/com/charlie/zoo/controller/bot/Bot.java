package com.charlie.zoo.controller.bot;

import com.charlie.zoo.service.TelegramUserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


public class Bot extends TelegramLongPollingBot {

    private String  password = "62m6Ad0RPWXA/BZmKM2A5Q==";
    private TelegramUserService telegramUserService;


    final int RECONNECT_PAUSE =10000;
    @Setter
    @Getter
    String userName;
    @Setter
    @Getter
    String token;

    @Autowired
    public void setTelegramUserService(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    public Bot( String userName, String token) {
        this.userName = userName;
        this.token = token;
    }


    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = update.getMessage().getChatId();
//        update.getMessage().getContact().
        String inputText = update.getMessage().getText();

        if (inputText.startsWith("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Ведіть ключ доступу");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if(inputText.contains(password)){
            System.out.println("service not null");
            telegramUserService.add(update.getMessage().getContact(), chatId);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Ви додані до групи розсилки");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}