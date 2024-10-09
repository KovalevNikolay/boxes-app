package ru.kovalev.boxesapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.botcommand.factory.BotCommandHandlerFactory;
import ru.kovalev.boxesapp.botcommand.handler.CommandHandler;
import ru.kovalev.boxesapp.botcommand.parser.MessageParser;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String username;
    private final BotCommandHandlerFactory handlerFactory;
    private final MessageParser messageParser;

    public TelegramBotService(@Value("${TELEGRAM_BOT_TOKEN}") String token,
                              @Lazy BotCommandHandlerFactory handlerFactory,
                              MessageParser messageParser)
    {
        super(token);
        this.handlerFactory = handlerFactory;
        this.messageParser = messageParser;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String command = messageParser.parseCommand(message.getText());
            CommandHandler handler = handlerFactory.getHandler(command);
            handler.handle(update);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
