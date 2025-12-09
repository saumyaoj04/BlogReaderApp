package com.example.blogreader
import android.net.Uri


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.room.Room
import com.example.blogreader.data.local.BlogDatabase
import com.example.blogreader.data.remote.RetrofitClient
import com.example.blogreader.data.repository.BlogRepositoryImpl
import com.example.blogreader.presentation.BlogViewModel
import com.example.blogreader.presentation.navigation.Screens
import com.example.blogreader.presentation.screens.BlogDetailScreen
import com.example.blogreader.presentation.screens.BlogListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            BlogDatabase::class.java,
            "blog_db"
        ).build()

        val repository = BlogRepositoryImpl(
            api = RetrofitClient.api,
            dao = db.blogDao()
        )

        setContent {
            val navController = rememberNavController()
            val vm: BlogViewModel = remember { BlogViewModel(repository) }

            MaterialTheme {
                NavHost(navController = navController, startDestination = Screens.BlogList.route) {
                    composable(Screens.BlogList.route) {
                        BlogListScreen(
                            viewModel = vm,
                            onBlogClick = { url ->
                                navController.navigate(Screens.BlogDetail.createRoute(url))
                            }
                        )
                    }
                    composable(Screens.BlogDetail.route) { backStackEntry ->
                        val url = Uri.decode(backStackEntry.arguments?.getString("url") ?: "")

                        BlogDetailScreen(url)
                    }
                }
            }
        }
    }
}
