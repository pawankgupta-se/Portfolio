package com.example.portfolio.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.portfolio.presentation.AppRoutes.SIGN_UP
import com.example.portfolio.presentation.AppRoutes.SIGN_UP_SUCCESS
import com.example.portfolio.presentation.signup.SignUpScreen
import com.example.portfolio.presentation.signup.SignUpViewModel
import com.example.portfolio.presentation.signupsuccess.SignUpSuccessScreen
import com.example.portfolio.presentation.signupsuccess.SignUpSuccessViewModel


object AppRoutes{
    const val SIGN_UP =  "signup"
    const val SIGN_UP_SUCCESS = "signupsuccess"
}

@Composable
fun AppHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SIGN_UP) {
        composable(route = SIGN_UP) {
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                viewModel = viewModel,
                onSignUpSuccess = { navController.navigate(route = SIGN_UP_SUCCESS) })
        }

        composable(route = SIGN_UP_SUCCESS) {
            val viewModel: SignUpSuccessViewModel = hiltViewModel()
            SignUpSuccessScreen(viewModel = viewModel)
        }
    }
}