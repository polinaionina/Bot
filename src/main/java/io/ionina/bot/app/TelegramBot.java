package io.ionina.bot.app;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import io.ionina.bot.config.BotConfig;
import io.ionina.bot.models.GIFContent;
import io.ionina.bot.models.MessageContent;
import io.ionina.bot.models.StickerContent;
import io.ionina.bot.models.TextContent;
import io.ionina.bot.service.EchoMessageService;

/**
 * Telegram-бот, отправляющий пользователю в ответ то же сообщение, которое он прислал (эхо-бот).
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
        if (!update.hasMessage()) {
            return;
        }

        var message = update.getMessage();
        Long chatId = message.getChatId();

        MessageContent incomingContent = extractContent(message);
        if (incomingContent == null) {
            return;
        }

        MessageContent responseContent = echoMessageService.buildResponse(incomingContent);

        try {
            sendContent(chatId, responseContent);
        } catch (TelegramApiException exception) {
            throw new RuntimeException("Failed to send a reply message.", exception);
        }
    }

    /**
     * Определяет тип входящего сообщения и оборачивает его в соответствующий {@link MessageContent}.
     *
     * @param message входящее сообщение Telegram
     * @return содержимое сообщения, либо {@code null}, если тип не поддерживается
     */
    private MessageContent extractContent(org.telegram.telegrambots.meta.api.objects.Message message) {
        if (message.hasText()) {
            return new TextContent(message.getText());
        }
        if (message.hasSticker()) {
            return new StickerContent(message.getSticker().getFileId());
        }
        if (message.hasAnimation()) {
            return new GIFContent(message.getAnimation().getFileId());
        }

        return null;
    }

    /**
     * Отправляет содержимое ответа в чат, выбирая нужный метод Telegram Bot API
     * в зависимости от конкретного типа содержимого.
     *
     * @param chatId идентификатор чата
     * @param content содержимое ответа
     * @throws TelegramApiException если отправка не удалась
     */
    private void sendContent(Long chatId, MessageContent content) throws TelegramApiException {
        if (content instanceof TextContent textContent) {
            SendMessage response = new SendMessage();
            response.setChatId(chatId.toString());
            response.setText(textContent.text());
            execute(response);

        } else if (content instanceof StickerContent stickerContent) {
            SendSticker response = new SendSticker();
            response.setChatId(chatId.toString());
            response.setSticker(new InputFile(stickerContent.fileId()));
            execute(response);

        } else if (content instanceof GIFContent gifContent) {
            SendAnimation response = new SendAnimation();
            response.setChatId(chatId.toString());
            response.setAnimation(new InputFile(gifContent.fileId()));
            execute(response);
        }
    }
}
