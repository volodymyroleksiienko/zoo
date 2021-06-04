package com.charlie.zoo.service;

import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.TelegramUser;
import org.telegram.telegrambots.api.objects.Contact;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    TelegramUser add(Contact contact,Long chatId);
    void sendInfo(OrderInfo orderInfo);
    Optional<TelegramUser> findByChatId(Long id);
    List<TelegramUser> findAll();
    void deleteById(int id);
}
