package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label("emitPublishCustomerNotificationEvent")
    input {
        triggeredBy("emitPublishCustomerNotificationEvent()")
    }
    outputMessage {
        sentTo("internal.exchange")
        headers {
            header("amqp_receivedRoutingKey", "#")
        }
        body([
                sender         : "contract test",
                toCustomerId   : 1,
                toCustomerEmail: "email.contract@mail.ru"
        ])
    }
}
