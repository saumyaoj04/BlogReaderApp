package com.example.blogreader.presentation.navigation

sealed class Screens(val route: String) {
    object BlogList : Screens("blog_list")
    object BlogDetail : Screens("blog_detail/{url}") {
        fun createRoute(url: String) = "blog_detail/$url"
    }
}


