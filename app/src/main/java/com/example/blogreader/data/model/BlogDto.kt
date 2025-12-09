package com.example.blogreader.data.model

import com.google.gson.annotations.SerializedName
import com.example.blogreader.domain.model.Blog

data class BlogDto(
    val id: Int,
    val link: String,
    @SerializedName("title") val titleRendered: RenderedText,
    @SerializedName("excerpt") val excerptRendered: RenderedText
)

data class RenderedText(val rendered: String)

fun BlogDto.toDomain(page: Int) = Blog(
    id = id,
    title = titleRendered.rendered.replace("<[^>]*>".toRegex(), ""),
    excerpt = excerptRendered.rendered.replace("<[^>]*>".toRegex(), "").take(150) + "...",
    url = link,
    page = page
)
