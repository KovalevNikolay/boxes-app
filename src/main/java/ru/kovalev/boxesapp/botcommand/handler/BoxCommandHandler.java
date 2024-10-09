package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

@RequiredArgsConstructor
public class BoxCommandHandler implements CommandHandler {

    private final BoxesService boxesService;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;

    @Override
    public void handle(Update update) {
        String text = update.getMessage().getText();
        String boxName = messageParser.parseParameters(text).getFirst();
        String message = boxesService.getByName(boxName)
                .map(BoxDto::toString)
                .orElse(String.format("Посылка с именем '%s' не найдена.", boxName));

        Long chatId = update.getMessage().getChatId();
        messageSender.sendMessage(chatId, message);
    }
}
