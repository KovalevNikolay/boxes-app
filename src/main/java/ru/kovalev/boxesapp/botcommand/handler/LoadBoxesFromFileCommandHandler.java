package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.controller.BoxesLoadController;
import ru.kovalev.boxesapp.io.TelegramFileDownloader;
import ru.kovalev.boxesapp.printer.TruckListPrinter;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class LoadBoxesFromFileCommandHandler implements CommandHandler {

    private static final int TRUCKS_INDEX = 0;
    private static final int LOAD_STRATEGY_INDEX = 1;
    private static final int NEEDED_COUNT_PARAMETERS = 2;

    private final TelegramFileDownloader telegramFileDownloader;
    private final BoxesLoadController boxesLoadController;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;
    private final TruckListPrinter truckListPrinter;


    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        List<String> parameters = messageParser.parseParameters(update.getMessage().getText());

        if (parameters.size() != NEEDED_COUNT_PARAMETERS) {
            messageSender.sendMessage(chatId, "Вы не передали необходимое количество параметров.");
            return;
        }

        if (!update.getMessage().hasDocument()) {
            messageSender.sendMessage(chatId, "Вы не прикрепили файл с посылками.");
            return;
        }

        Document document = update.getMessage().getDocument();
        Path path = telegramFileDownloader.downloadFile(document);
        String trucks = parameters.get(TRUCKS_INDEX);
        String strategy = parameters.get(LOAD_STRATEGY_INDEX);
        String message = truckListPrinter.print(boxesLoadController.loadBoxesFromFile(path.toString(), trucks, strategy));
        messageSender.sendMessage(chatId, message);
    }
}
