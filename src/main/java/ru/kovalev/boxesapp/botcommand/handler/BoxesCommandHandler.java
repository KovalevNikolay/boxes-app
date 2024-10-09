package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BoxesCommandHandler implements CommandHandler {

    private final BoxesService boxesService;
    private final TelegramMessageSender sender;

    @Override
    public void handle(Update update) {
        String allBoxes = boxesService.getAll().stream()
                .map(BoxDto::toString)
                .collect(Collectors.joining());

        Long chatId = update.getMessage().getChatId();
        sender.sendMessage(chatId, allBoxes);
    }
}
