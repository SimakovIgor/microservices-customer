package ru.simakov.contract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.messaging.Message;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
class RabbitMessageVerifier implements MessageVerifierReceiver<Message<?>> {

    private final LinkedBlockingQueue<Message<?>> queue = new LinkedBlockingQueue<>();

    @Override
    public Message<?> receive(final String destination, final long timeout, final TimeUnit timeUnit, final YamlContract contract) {
        try {
            return queue.poll(timeout, timeUnit);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @RabbitListener(id = "internal.exchange", queues = "internal.exchange.queue")
    public void listen(final Message<?> message) {
        log.info("Got a message! [{}]", message);
        queue.add(message);
    }

    @Override
    public Message<?> receive(final String destination, final YamlContract contract) {
        return receive(destination, 1, TimeUnit.SECONDS, contract);
    }

}
