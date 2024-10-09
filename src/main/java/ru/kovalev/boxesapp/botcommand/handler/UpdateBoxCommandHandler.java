package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.controller.ProxyController;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.util.List;

@RequiredArgsConstructor
public class UpdateBoxCommandHandler implements CommandHandler {
    private static final int BOX_BODY_INDEX = 1;
    private static final int BOX_BODY_MARKER_INDEX = 2;

    private final ProxyController proxyController;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;

    @Override
    public void handle(Update update) {
        String text = update.getMessage().getText();
        List<String> parameters = messageParser.parseParameters(text);
        String name = parameters.getFirst();
        String body = parameters.get(BOX_BODY_INDEX);
        String marker = parameters.get(BOX_BODY_MARKER_INDEX);
        String message = proxyController.updateBox(name, body, marker);
        Long chatId = update.getMessage().getChatId();
        messageSender.sendMessage(chatId, message);
    }
}
