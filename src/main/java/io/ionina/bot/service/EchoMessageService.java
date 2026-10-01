package io.ionina.bot.service;

import io.ionina.bot.models.MessageContent;

/**
 * Сервис, отвечающий за формирование ответа бота на входящее 
 * сообщение пользователя, ответ полностью 
 * повторяет входящее сообщение.
 */
public class EchoMessageService implements MessageProcessingService {

    /**
     * Формирует ответ бота на основе входящего сообщения пользователя.
     * @param incomingContent сообщение, полученное от пользователя; не может быть {@code null}
     * @return ответ, который необходимо отправить пользователю (совпадает с {@code incomingContent})
     * @throws IllegalArgumentException если {@code incomingContent} равен {@code null}
     */
    public MessageContent buildResponse(MessageContent incomingContent) {
        if (incomingContent == null) {
            throw new IllegalArgumentException("The incoming message cannot be null.");
        }
        return incomingContent;
    }
}
