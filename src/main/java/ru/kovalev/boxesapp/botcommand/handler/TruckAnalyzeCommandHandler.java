package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.controller.ProxyController;
import ru.kovalev.boxesapp.io.TelegramFileDownloader;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.nio.file.Path;

@RequiredArgsConstructor
public class TruckAnalyzeCommandHandler implements CommandHandler {

    private final ProxyController proxyController;
    private final TelegramMessageSender messageSender;
    private final TelegramFileDownloader telegramFileDownloader;

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        if (!update.getMessage().hasDocument()) {
            messageSender.sendMessage(chatId, "Вы не прикрепили файл с посылками.");
            return;
        }
        Path path = telegramFileDownloader.downloadFile(update.getMessage().getDocument());
        String message = proxyController.truckAnalyze(path);
        messageSender.sendMessage(chatId, message);
    }
}
