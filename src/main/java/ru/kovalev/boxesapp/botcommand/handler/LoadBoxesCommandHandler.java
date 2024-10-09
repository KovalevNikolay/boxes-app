package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.controller.ProxyController;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.util.List;

@RequiredArgsConstructor
public class LoadBoxesCommandHandler implements CommandHandler {

    private static final int TRUCKS_INDEX = 1;
    private static final int LOAD_STRATEGY_INDEX = 2;

    private final ProxyController proxyController;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;

    @Override
    public void handle(Update update) {
        String text = update.getMessage().getText();
        List<String> parameters = messageParser.parseParameters(text);
        String boxes = parameters.getFirst();
        String trucks = parameters.get(TRUCKS_INDEX);
        String strategy = parameters.get(LOAD_STRATEGY_INDEX);
        Long chatId = update.getMessage().getChatId();
        String message = proxyController.loadBoxes(boxes, trucks, strategy);
        messageSender.sendMessage(chatId, message);
    }
}
