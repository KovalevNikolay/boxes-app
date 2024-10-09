package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

@RequiredArgsConstructor
public class DeleteBoxCommandHandler implements CommandHandler {

    private final BoxesService boxesService;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;

    @Override
    public void handle(Update update) {
        String text = update.getMessage().getText();
        String boxName = messageParser.parseParameters(text).getFirst();
        String message = boxesService.delete(boxName)
                ? String.format("Посылка '%s' удалена.", boxName)
                : String.format("Посылка '%s' не найдена.", boxName);

        Long chatId = update.getMessage().getChatId();
        messageSender.sendMessage(chatId, message);
    }
}
