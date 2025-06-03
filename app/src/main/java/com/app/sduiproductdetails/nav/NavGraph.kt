package com.app.sduiproductdetails.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.sduiproductdetails.ProductDetailsScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "productDetail") {
        composable("productDetail") {
            ProductDetailsScreen(navController = navController)
        }
    }
}
