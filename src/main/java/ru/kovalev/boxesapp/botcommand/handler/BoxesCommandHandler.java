package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.controller.ProxyController;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

@RequiredArgsConstructor
public class BoxesCommandHandler implements CommandHandler {

    private final ProxyController proxyController;
    private final TelegramMessageSender sender;

    @Override
    public void handle(Update update) {
        String allBoxes = proxyController.boxes();
        Long chatId = update.getMessage().getChatId();
        sender.sendMessage(chatId, allBoxes);
    }
}
