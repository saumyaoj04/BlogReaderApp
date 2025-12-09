package com.example.blogreader.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.blogreader.domain.model.Blog

@Entity(tableName = "blogs")
data class BlogEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val excerpt: String,
    val url: String,
    val page: Int
)

fun BlogEntity.toDomain(): Blog =
    Blog(id, title, excerpt, url, page)

fun Blog.toEntity(): BlogEntity =
    BlogEntity(id, title, excerpt, url, page)
