package com.pipiolo.postservice.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity

@Entity
class Post: PanacheEntity() {
    companion object : PanacheCompanion<Post>

    lateinit var title: String
    lateinit var content: String
}