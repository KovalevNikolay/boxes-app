package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.controller.ProxyController;
import ru.kovalev.boxesapp.io.TelegramFileDownloader;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class LoadBoxesFromFileCommandHandler implements CommandHandler {

    private static final int LOAD_STRATEGY_INDEX = 1;
    private static final int NEEDED_COUNT_PARAMETERS = 2;

    private final ProxyController proxyController;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;
    private final TelegramFileDownloader telegramFileDownloader;


    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        List<String> parameters = messageParser.parseParameters(update.getMessage().getCaption());

        if (parameters.size() != NEEDED_COUNT_PARAMETERS) {
            messageSender.sendMessage(chatId, "Вы не передали необходимое количество параметров.");
            return;
        }

        if (!update.getMessage().hasDocument()) {
            messageSender.sendMessage(chatId, "Вы не прикрепили файл с посылками.");
            return;
        }

        Path path = telegramFileDownloader.downloadFile(update.getMessage().getDocument());
        String trucks = parameters.getFirst();
        String strategy = parameters.get(LOAD_STRATEGY_INDEX);
        String message = proxyController.loadBoxesFromFile(path.toString(), trucks, strategy);
        messageSender.sendMessage(chatId, message);
    }
}
