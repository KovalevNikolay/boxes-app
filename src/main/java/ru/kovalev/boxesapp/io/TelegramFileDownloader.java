package ru.kovalev.boxesapp.io;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kovalev.boxesapp.exception.FileDownloadingException;
import ru.kovalev.boxesapp.service.TelegramBotService;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class TelegramFileDownloader {

    private final TelegramBotService bot;

    public Path downloadFile(Document document) {
        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());
        try {
            File telegramFile = bot.execute(getFile);
            return bot.downloadFile(telegramFile).toPath();
        } catch (TelegramApiException e) {
            throw new FileDownloadingException("Произошла ошибка при скачивании файла.", e);
        }
    }
}
