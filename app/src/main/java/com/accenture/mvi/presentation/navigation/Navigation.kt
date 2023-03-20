package com.accenture.mvi.presentation.navigation

enum class Navigation(val route: String) {

    ListScreen("list"),
    DetailScreen("detail")
}

enum class NavArgs(val key: String) {

    Detail("detailArg")
}