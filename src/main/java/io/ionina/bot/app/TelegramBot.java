package io.ionina.bot.app;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.ionina.bot.config.BotConfig;
import io.ionina.bot.service.EchoMessageService;

/**
 * Telegram-бот, отправляющий пользователю в ответ то же сообщение, которое он прислал (эхо-бот).
 * <p>
 * Класс отвечает только за взаимодействие с Telegram Bot API: приём обновлений и
 * отправку ответа. Формирование текста ответа делегировано {@link EchoMessageService}.
 */
public class TelegramBot extends TelegramLongPollingBot {

    /** Конфигурация бота: токен и имя пользователя. */
    private final BotConfig botConfig;

    /** Сервис, формирующий текст ответа на основе входящего сообщения. */
    private final EchoMessageService echoMessageService;

    /**
     * Создаёт эхо-бота с заданной конфигурацией и сервисом формирования ответов.
     *
     * @param botConfig          конфигурация бота (токен и имя пользователя)
     * @param echoMessageService сервис, формирующий текст ответа на входящее сообщение
     */
    public TelegramBot(BotConfig botConfig, EchoMessageService echoMessageService) {
        this.botConfig = botConfig;
        this.echoMessageService = echoMessageService;
    }

    /**
     * Возвращает имя пользователя бота, используемое Telegram Bot API.
     *
     * @return username бота
     */
    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    /**
     * Возвращает токен доступа бота, используемый Telegram Bot API.
     *
     * @return токен бота
     */
    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    /**
     * Обрабатывает входящее обновление от Telegram: если в нём содержится текстовое
     * сообщение, отправляет пользователю ответ с тем же текстом.
     *
     * @param update входящее обновление от Telegram Bot API
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String incomingText = update.getMessage().getText();
            String responseText = echoMessageService.buildEchoResponse(incomingText);

            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText(responseText);

            try {
                execute(response);
            } catch (TelegramApiException exception) {
                throw new RuntimeException("Не удалось отправить ответное сообщение", exception);
            }
        }
    }
}
