package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.util.List;

@RequiredArgsConstructor
public class UpdateBoxCommandHandler implements CommandHandler {
    private static final int BOX_NAME_INDEX = 0;
    private static final int BOX_BODY_INDEX = 1;
    private static final int BOX_BODY_MARKER_INDEX = 2;

    private final BoxesService boxesService;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;

    @Override
    public void handle(Update update) {
        String text = update.getMessage().getText();
        List<String> parameters = messageParser.parseParameters(text);
        String name = parameters.get(BOX_NAME_INDEX);
        String body = parameters.get(BOX_BODY_INDEX);
        String marker = parameters.get(BOX_BODY_MARKER_INDEX);
        String message = boxesService.update(name, body, marker)
                ? String.format("Посылка '%s' обновлена.", name)
                : String.format("Посылки '%s' не существует.", name);

        Long chatId = update.getMessage().getChatId();
        messageSender.sendMessage(chatId, message);
    }
}
