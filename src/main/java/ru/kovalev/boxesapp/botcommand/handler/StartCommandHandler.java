package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {

    private final TelegramMessageSender messageSender;

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = "Введите команду /help, чтобы узнать как управлять ботом;";
        messageSender.sendMessage(chatId, message);
    }
}
