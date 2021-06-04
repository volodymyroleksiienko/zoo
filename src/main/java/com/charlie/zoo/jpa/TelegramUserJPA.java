package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelegramUserJPA extends JpaRepository<TelegramUser,Integer> {
    Optional<TelegramUser> findByChatId(Long id);
}
