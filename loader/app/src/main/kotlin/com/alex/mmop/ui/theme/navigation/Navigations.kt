package com.alex.mmop.ui.theme.navigation

/**
 * @author alex5402
 * Created 9/28/24 at 12:31 PM
 * Navigations
 */

sealed class Navigations(val route : String) {
    object Login : Navigations("login")
    object Loder : Navigations("main_menu")
}