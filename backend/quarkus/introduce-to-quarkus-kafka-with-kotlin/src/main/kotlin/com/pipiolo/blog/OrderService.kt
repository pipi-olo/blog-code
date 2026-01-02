package com.pipiolo.blog

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.util.concurrent.ConcurrentLinkedQueue

@ApplicationScoped
class OrderService(@Channel("order-out") private val emitter: Emitter<Order>) {

    private val messages = ConcurrentLinkedQueue<Order>()

    fun placeOrder(order: Order) {
        emitter.send(order)
    }

    fun getOrder(): Order {
        return messages.poll()
    }

    @Incoming("order-in")
    fun consumeOrder(order: Order) {
        messages.add(order)
    }
}