package io.ionina.bot.service;

/**
 * Сервис, отвечающий за формирование ответа бота на входящее 
 * текстовое сообщение пользователя, ответ полностью 
 * повторяет текст входящего сообщения.
 */
public class EchoMessageService implements MessageProcessingService {

    /**
     * Формирует текст ответа бота на основе текста входящего сообщения пользователя.
     * @param incomingText текст сообщения, полученного от пользователя; не может быть {@code null}
     * @return текст ответа, который необходимо отправить пользователю (совпадает с {@code incomingText})
     * @throws IllegalArgumentException если {@code incomingText} равен {@code null}
     */
    public String buildEchoResponse(String incomingText) {
        if (incomingText == null) {
            throw new IllegalArgumentException("The text of the incoming message cannot be null.");
        }
        return incomingText;
    }
}
