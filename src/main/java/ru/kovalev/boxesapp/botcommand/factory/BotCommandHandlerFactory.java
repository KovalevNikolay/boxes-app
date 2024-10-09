package ru.kovalev.boxesapp.botcommand.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.botcommand.handler.BoxCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.BoxesCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.CommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.CreateBoxCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.DeleteBoxCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.HelpCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.LoadBoxesCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.LoadBoxesFromFileCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.StartCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.UpdateBoxCommandHandler;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.controller.BoxesLoadController;
import ru.kovalev.boxesapp.io.TelegramFileDownloader;
import ru.kovalev.boxesapp.printer.TruckListPrinter;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

@Service
@RequiredArgsConstructor
public class BotCommandHandlerFactory {

    private final BoxesService boxesService;
    private final TelegramMessageSender messageSender;
    private final MessageParser messageParser;
    private final TruckListPrinter truckPrinter;
    private final BoxesLoadController loadController;
    private final TelegramFileDownloader fileDownloader;

    public CommandHandler getHandler(String command) {
        return switch (command) {
            case "/start" -> new StartCommandHandler();
            case "/boxes" -> new BoxesCommandHandler(boxesService, messageSender);
            case "/box" -> new BoxCommandHandler(boxesService, messageSender, messageParser);
            case "/addBox" -> new CreateBoxCommandHandler(boxesService, messageSender, messageParser);
            case "/updateBox" -> new UpdateBoxCommandHandler(boxesService, messageSender, messageParser);
            case "/deleteBox" -> new DeleteBoxCommandHandler(boxesService, messageSender, messageParser);
            case "/loadBoxes" -> new LoadBoxesCommandHandler(loadController, messageSender, messageParser, truckPrinter);
            case "/loadBoxesFromFile" -> new LoadBoxesFromFileCommandHandler(fileDownloader, loadController, messageSender, messageParser, truckPrinter);
            default -> new HelpCommandHandler();
        };
    }
}
