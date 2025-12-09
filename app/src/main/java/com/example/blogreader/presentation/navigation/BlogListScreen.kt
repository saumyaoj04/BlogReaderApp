package com.example.blogreader.presentation.screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blogreader.presentation.BlogViewModel
import com.example.blogreader.presentation.components.BlogShimmerItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun BlogListScreen(
    viewModel: BlogViewModel,
    onBlogClick: (String) -> Unit
) {
    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)


    LaunchedEffect(Unit) {
        viewModel.loadBlogs()
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refreshBlogs() }
    ) {

        Column(modifier = Modifier.fillMaxSize()) {


            if (state.lastUpdated != null && state.blogs.isNotEmpty()) {
                val minutesAgo = (System.currentTimeMillis() - state.lastUpdated!!) / 60000
                Text(
                    text = "Last updated ${if (minutesAgo == 0L) "just now" else "$minutesAgo min ago"}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }


            if (state.isLoading && state.blogs.isEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                    items(6) { BlogShimmerItem() }
                }
                return@SwipeRefresh
            }


            LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                items(state.blogs) { blog ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                // 🔥 FIX — Encode URL before navigation to avoid crash
                                onBlogClick(Uri.encode(blog.url))
                            }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = blog.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(6.dp))
                            Text(text = blog.excerpt)
                        }
                    }
                }


                item {
                    if (state.isLoading && state.blogs.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }


    state.error?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(12.dp)
        )
    }
}
