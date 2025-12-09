package com.example.blogreader.data.repository

import com.example.blogreader.data.local.BlogDao
import com.example.blogreader.data.local.toDomain
import com.example.blogreader.data.local.toEntity
import com.example.blogreader.data.model.toDomain
import com.example.blogreader.data.remote.ApiService
import com.example.blogreader.domain.model.Blog
import com.example.blogreader.domain.repository.BlogRepository
import java.io.IOException

class BlogRepositoryImpl(
    private val api: ApiService,
    private val dao: BlogDao
) : BlogRepository {

    override suspend fun getBlogsPage(page: Int): List<Blog> {
        return try {

            val dtos = api.getBlogs(page = page)


            println("🌍 Calling API page = $page")
            println("🌍 API returned ${dtos.size} blogs")

            val blogs = dtos.map { it.toDomain(page) }


            dao.insertAll(blogs.map { it.toEntity() })


            blogs

        } catch (e: IOException) {

            val cached = dao.getBlogsByPage(page)

            println("📦 Loading from CACHE page = $page, items = ${cached.size}")

            if (cached.isNotEmpty()) {
                cached.map { it.toDomain() }
            } else {
                throw e // nothing cached → throw original internet error
            }
        }
    }


}
