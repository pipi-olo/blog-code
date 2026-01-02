package com.pipiolo.blog

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OrderResource(private val orderService: OrderService) {
    @POST
    fun place(order: Order): Response {
        orderService.placeOrder(order)
        return Response.status(Response.Status.CREATED).entity(order).build()
    }

    @GET
    fun get(): Response {
        val order = orderService.getOrder()
        return Response.ok(order).build()
    }
}