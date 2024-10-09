package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.controller.BoxesLoadController;
import ru.kovalev.boxesapp.printer.TruckListPrinter;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.util.List;

@RequiredArgsConstructor
public class LoadBoxesCommandHandler implements CommandHandler {

    private static final int BOX_NAMES_INDEX = 0;
    private static final int TRUCKS_INDEX = 1;
    private static final int LOAD_STRATEGY_INDEX = 2;

    private final BoxesLoadController boxesLoadController;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;
    private final TruckListPrinter truckListPrinter;


    @Override
    public void handle(Update update) {
        String text = update.getMessage().getText();
        List<String> parameters = messageParser.parseParameters(text);
        String boxes = parameters.get(BOX_NAMES_INDEX);
        String trucks = parameters.get(TRUCKS_INDEX);
        String strategy = parameters.get(LOAD_STRATEGY_INDEX);

        Long chatId = update.getMessage().getChatId();
        String message = truckListPrinter.print(boxesLoadController.loadBoxes(boxes, trucks, strategy));
        messageSender.sendMessage(chatId, message);
    }
}
