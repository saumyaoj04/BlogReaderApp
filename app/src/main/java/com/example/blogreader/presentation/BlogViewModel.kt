package com.example.blogreader.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogreader.domain.repository.BlogRepository
import kotlinx.coroutines.launch

class BlogViewModel(
    private val repository: BlogRepository
) : ViewModel() {

    var state by mutableStateOf(BlogUiState())
        private set


    fun loadBlogs() {
        if (state.isLoading || state.endReached) return

        viewModelScope.launch {
            println("🌍 Loading page: ${state.currentPage}")

            try {
                state = state.copy(isLoading = true)

                val blogs = repository.getBlogsPage(state.currentPage)
                println("📩 Blogs received: ${blogs.size}")

                state = state.copy(
                    blogs = state.blogs + blogs,
                    currentPage = state.currentPage + 1,
                    isLoading = false,
                    endReached = blogs.isEmpty(),
                    lastUpdated = System.currentTimeMillis()   // ⬅ NEW
                )

            } catch (e: Exception) {
                println("❌ ERROR: ${e.message}")
                state = state.copy(error = e.message, isLoading = false)
            }
        }
    }


    fun refreshBlogs() {
        viewModelScope.launch {
            println("🔄 Refreshing blogs...")


            state = BlogUiState(lastUpdated = System.currentTimeMillis())


            loadBlogs()
        }
    }
}
