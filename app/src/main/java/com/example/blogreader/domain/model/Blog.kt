package com.example.blogreader.domain.model

data class Blog(
    val id: Int,
    val title: String,
    val excerpt: String,
    val url: String,
    val page: Int
)
