package ru.kostapo.tennisscoreboard.utils;

import ru.kostapo.tennisscoreboard.common.exception.BadParameterException;

import java.util.UUID;

public class StringUtil {

    public static int validateAndGetNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new BadParameterException(String.format("%s - НЕ КОРРЕКТНОЕ ЧИСЛО", number));
        }
    }

    public static UUID validateAndGetUUID(String uuidStr) {
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            throw new BadParameterException(String.format("%s - НЕ UUID", uuidStr));
        }
    }

    public static boolean validatePlayerName(String name) {
        if (name.length() > 16) {
            throw new BadParameterException(String.format("%s - СЛИШКОМ ДЛИННОЕ ИМЯ ИГРОКА", name));
        }
        for (char ch : name.toCharArray()) {
            if (!Character.isLetter(ch) || !isCharacterInCyrillicOrLatinBlock(ch)) {
                throw new BadParameterException(String.format("%s - НЕ КОРРЕКТНОЕ ИМЯ ИГРОКА", name));
            }
        }
        return true;
    }

    private static boolean isCharacterInCyrillicOrLatinBlock(char ch) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
        return block == Character.UnicodeBlock.CYRILLIC || block == Character.UnicodeBlock.BASIC_LATIN;
    }
}
