package dev.ybrmst.dicodingstory

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.*
import dagger.hilt.android.AndroidEntryPoint
import dev.ybrmst.dicodingstory.ui.common.scopedViewModel
import dev.ybrmst.dicodingstory.ui.composables.screens.*
import dev.ybrmst.dicodingstory.ui.theme.DicodingStoryTheme
import dev.ybrmst.dicodingstory.ui.viewmodel.auth.AuthSideEffect
import dev.ybrmst.dicodingstory.ui.viewmodel.auth.AuthViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      DicodingStoryTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = RootRoute.Index) {
          addInit(navController)
          addOnboarding(navController)
          addSignIn(navController)
          addSignUp(navController)
          addHome(navController)
        }
      }
    }
  }
}

private fun NavGraphBuilder.addInit(navController: NavController) {
  composable<RootRoute.Index> {
    val viewModel: AuthViewModel = it.scopedViewModel(navController)
    val context = LocalContext.current

    viewModel.collectSideEffect { effect ->
      when (effect) {
        is AuthSideEffect.NavigateToNext -> {
          navController.navigate(effect.route) {
            popUpTo(RootRoute.Index) { inclusive = true }
          }
        }

        is AuthSideEffect.ShowToast -> {
          Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
        }
      }
    }

    IndexScreen()
  }
}

private fun NavGraphBuilder.addOnboarding(navController: NavController) {
  composable<RootRoute.Onboarding> {
    OnboardingScreen(
      onNavigateToSignIn = {
        navController.navigate(RootRoute.SignIn)
      },
      onNavigateToSignUp = {
        navController.navigate(RootRoute.SignUp)
      }
    )
  }
}

private fun NavGraphBuilder.addSignIn(navController: NavController) {
  composable<RootRoute.SignIn> {
    SignInScreen(
      onBack = {
        navController.navigate(RootRoute.Onboarding) {
          popUpTo(RootRoute.Onboarding) { inclusive = true }
        }
      }
    )
  }
}

private fun NavGraphBuilder.addSignUp(navController: NavController) {
  composable<RootRoute.SignUp> {
    SignUpScreen(
      onBack = {
        navController.navigate(RootRoute.Onboarding) {
          popUpTo(RootRoute.Onboarding) { inclusive = true }
        }
      }
    )
  }
}

@Suppress("UNUSED_PARAMETER")
private fun NavGraphBuilder.addHome(navController: NavController) {
  composable<RootRoute.Home> {
    HomeScreen()
  }
}