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
import ru.kovalev.boxesapp.botcommand.handler.TruckAnalyzeCommandHandler;
import ru.kovalev.boxesapp.botcommand.handler.UpdateBoxCommandHandler;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;
import ru.kovalev.boxesapp.io.TelegramFileDownloader;
import ru.kovalev.boxesapp.controller.ProxyController;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

@Service
@RequiredArgsConstructor
public class BotCommandHandlerFactory {

    private final TelegramMessageSender sender;
    private final MessageParser parser;
    private final TelegramFileDownloader downloader;
    private final ProxyController proxy;

    public CommandHandler getHandler(String command) {
        return switch (command) {
            case "/start" -> new StartCommandHandler(sender);
            case "/boxes" -> new BoxesCommandHandler(proxy, sender);
            case "/box" -> new BoxCommandHandler(proxy, sender, parser);
            case "/addBox" -> new CreateBoxCommandHandler(proxy, sender, parser);
            case "/updateBox" -> new UpdateBoxCommandHandler(proxy, sender, parser);
            case "/deleteBox" -> new DeleteBoxCommandHandler(proxy, sender, parser);
            case "/loadBoxes" -> new LoadBoxesCommandHandler(proxy, sender, parser);
            case "/loadBoxesFromFile" -> new LoadBoxesFromFileCommandHandler(proxy, sender, parser, downloader);
            case "/truckAnalyze" -> new TruckAnalyzeCommandHandler(proxy, sender, downloader);
            default -> new HelpCommandHandler(sender);
        };
    }
}
