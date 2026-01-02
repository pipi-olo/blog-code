package com.pipiolo.blog

import com.pipiolo.blog.entity.Post
import io.quarkus.panache.common.Page
import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PostResource {

    @GET
    fun listAll(
        @QueryParam("index") @DefaultValue("0") index: Int,
        @QueryParam("size") @DefaultValue("20") size: Int
    ): List<Post> {
        return Post.findAll().page(Page.of(index, size)).list()
    }

    @GET
    @Path("/{id}")
    fun get(id: Long): Response {
        val post = Post.findById(id) ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(post).build()
    }

    @POST
    @Transactional
    fun create(post: Post): Response {
        post.persist()
        return Response.status(Response.Status.CREATED).entity(post).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    fun delete(id: Long): Response {
        val post = Post.findById(id) ?: return Response.status(Response.Status.NOT_FOUND).build()
        post.delete()
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}