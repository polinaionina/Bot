package io.ionina.bot.config;

/**
 * Хранит параметры конфигурации Telegram-бота: токен доступа и имя пользователя бота.
 * Экземпляр этого класса является неизменяемым объектом-значением:
 * после создания его состояние не меняется.
 */
public class BotConfig {

    /** Токен доступа к Telegram Bot API */
    private final String botToken;

    /** Имя пользователя, username бота в Telegram */
    private final String botUsername;

    /** Создаёт конфигурацию бота с указанными параметрами */
    public BotConfig(String botToken, String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    /** Возвращает токен доступа к Telegram Bot API */
    public String getBotToken() {
        return botToken;
    }

    /** Возвращает имя пользователя бота в Telegram. */
    public String getBotUsername() {
        return botUsername;
    }
}
