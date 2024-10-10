package ru.kovalev.boxesapp.io;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kovalev.boxesapp.exception.FileDownloadingException;
import ru.kovalev.boxesapp.service.TelegramBotService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class TelegramFileDownloader {

    private final TelegramBotService bot;

    public Path downloadFile(Document document) {
        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());
        try {
            File telegramFile = bot.execute(getFile);
            java.io.File downloadedFile = bot.downloadFile(telegramFile);
            Path destination = Paths.get("downloads/" + document.getFileName());
            Files.createDirectories(destination.getParent());
            Files.copy(downloadedFile.toPath(), destination);
            return destination;
        } catch (TelegramApiException | IOException e) {
            throw new FileDownloadingException("Произошла ошибка при скачивании файла.", e);
        }
    }
}
