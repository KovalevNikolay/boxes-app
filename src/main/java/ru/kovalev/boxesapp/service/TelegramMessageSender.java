package ru.kovalev.boxesapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kovalev.boxesapp.exception.SendMessageException;

@Service
@RequiredArgsConstructor
public class TelegramMessageSender {

    private final TelegramBotService telegramBotService;

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        send(sendMessage);
    }

    private void send(SendMessage message) {
        try {
            telegramBotService.execute(message);
        } catch (TelegramApiException e) {
            throw new SendMessageException("Произошла ошибка при отправке сообщения.", e);
        }
    }
}
