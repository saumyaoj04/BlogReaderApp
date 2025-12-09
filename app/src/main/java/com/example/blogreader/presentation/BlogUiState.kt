package com.example.blogreader.presentation

import com.example.blogreader.domain.model.Blog

data class BlogUiState(
    val blogs: List<Blog> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val endReached: Boolean = false,
    val lastUpdated: Long? = null

)
