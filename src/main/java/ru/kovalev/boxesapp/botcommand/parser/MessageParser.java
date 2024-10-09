package ru.kovalev.boxesapp.botcommand.parser;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MessageParser {
    private static final String SPACE = " ";
    private static final int COMMAND_INDEX = 0;
    public String parseCommand(String messageText) {
        return messageText.split(SPACE)[COMMAND_INDEX];
    }

    public List<String> parseParameters(String messageText) {
        return Arrays.stream(messageText.split(SPACE))
                .skip(1)
                .toList();
    }
}
