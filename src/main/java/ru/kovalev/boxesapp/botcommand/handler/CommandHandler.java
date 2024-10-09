package ru.kovalev.boxesapp.botcommand.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void handle(Update update);
}
