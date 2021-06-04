package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.controller.bot.Bot;
import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.TelegramUser;
import com.charlie.zoo.jpa.TelegramUserJPA;
import com.charlie.zoo.service.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {
    private TelegramUserJPA telegramUserJPA;
    private Bot bot;

    @Override
    public TelegramUser add(Contact contact, Long chatId) {
        if(findByChatId(chatId).isPresent()) return null;

        TelegramUser  user = new TelegramUser();
        if(contact!=null) {
            user.setFirstName(contact.getFirstName());
            user.setLastName(contact.getLastName());
            user.setPhone(contact.getPhoneNumber());
            user.setUserId(contact.getUserID());
        }
        user.setChatId(chatId);
        return telegramUserJPA.save(user);
    }

    @Override
    public void sendInfo(OrderInfo orderInfo) {
        for(TelegramUser user:findAll()) {
            SendMessage message = new SendMessage();
            message.setChatId(user.getChatId());
            String text = "Нове замовлення #"+orderInfo.getId();
            text+="\n Ім'я: "+orderInfo.getClient().getName();
            text+="\n Телефон: "+orderInfo.getClient().getPhones().get(0).getPhone();
            text+="\n Cума замовлення: "+orderInfo.getSumPrice();
            for(OrderDetails details:orderInfo.getOrderDetails()){
                text+="\n\n Товар: "+details.getPackageType().getProduct().getName();
                text+="\n Фасування: "+details.getPackageType().getPackSize()+" "+details.getPackageType().getPackType();
                text+="\n Кількість: "+details.getCount();
                text+="\n Ціна: "+details.getPrice();
            }
            message.setText(text);
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<TelegramUser> findByChatId(Long id) {
        return telegramUserJPA.findByChatId(id);
    }

    @Override
    public List<TelegramUser> findAll() {
        return telegramUserJPA.findAll();
    }

    @Override
    public void deleteById(int id) {
        telegramUserJPA.deleteById(id);
    }
}
