package ru.kovalev.boxesapp.botcommand.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kovalev.boxesapp.service.TelegramMessageSender;

@RequiredArgsConstructor
public class HelpCommandHandler implements CommandHandler {

    private final TelegramMessageSender messageSender;

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = """
                /boxes - выводит список всех посылок;
                                
                /box - выводит конкретную посылку по названию. Синтаксис: "/box письмо"
                                
                /addBox - добавляет посылку в базу данных. Синтаксис: "/addBox"
                                
                /updateBox - обновляет посылку в базе данных. Синтаксис: "/updateBox письмо #,#;#,# #"
                                
                /deleteBox - удаляет посылку из базы данных. Синтаксис: "/deleteBox письмо"
                                
                /loadBoxes - загружает посылки в грузовики. Принимает имена посылок через запятую, размеры грузовиков, а также стратегию загрузки (QUALITY/UNIFORM). Синтаксис: "/loadBoxes письмо,коробка 5x5,4x6 QUALITY"
                                
                /loadBoxesFromFile - загружает посылки из файла в грузовики. Принимает размеры грузовиков,стратегию загрузки (QUALITY/UNIFORM), а также прикрепленный к сообщению JSON файл с посылками.
                Синтаксис: "/loadBoxesFromFile 3х3,4x4,5x5 UNIFORM" + json-файл
                                
                /truckAnalyze - анализирует посылки, находящихся в кузове грузовика. Принимает прикрепленный JSON-файл с грузовиками. Синтаксис "/truckAnalyze" + json-файл
                """;

        messageSender.sendMessage(chatId, message);
    }
}
