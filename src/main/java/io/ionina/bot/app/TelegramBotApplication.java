package io.ionina.bot.app;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import io.ionina.bot.config.BotConfig;
import io.ionina.bot.config.ConfigLoader;
import io.ionina.bot.service.EchoMessageService;

/**
 * Точка входа в приложение эхо-бота.
 * <p>
 * Загружает конфигурацию из переменных окружения {@code BOT_TOKEN} и {@code BOT_USERNAME},
 * создаёт бота и регистрирует его в Telegram Bot API с использованием длинного опроса
 * (long polling).
 */
public class TelegramBotApplication {

    /**
     * Запускает приложение: собирает конфигурацию, создаёт и регистрирует бота.
     * <p>
     * Метод объявлен {@code static}, так как это единственная сигнатура точки входа,
     * которую допускает виртуальная машина Java для запуска программы.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader();
        BotConfig botConfig = configLoader.loadConfig(System.getenv());

        EchoMessageService echoMessageService = new EchoMessageService();
        TelegramBot echoTelegramBot = new TelegramBot(botConfig, echoMessageService);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(echoTelegramBot);
            System.out.println("Бот успешно запущен");
        } catch (TelegramApiException exception) {
            throw new RuntimeException("The bot could not be registered.", exception);
        }
    }
}
