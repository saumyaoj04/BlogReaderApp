package com.example.blogreader.domain.repository

import com.example.blogreader.domain.model.Blog

interface BlogRepository {
    suspend fun getBlogsPage(page: Int): List<Blog>
}
