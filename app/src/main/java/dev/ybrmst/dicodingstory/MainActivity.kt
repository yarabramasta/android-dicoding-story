package dev.ybrmst.dicodingstory

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.*
import dagger.hilt.android.AndroidEntryPoint
import dev.ybrmst.dicodingstory.ui.common.*
import dev.ybrmst.dicodingstory.ui.composables.screens.*
import dev.ybrmst.dicodingstory.ui.theme.DicodingStoryTheme
import dev.ybrmst.dicodingstory.ui.viewmodel.auth.AuthSideEffect
import dev.ybrmst.dicodingstory.ui.viewmodel.auth.AuthViewModel
import dev.ybrmst.dicodingstory.ui.viewmodel.signup.SignUpSideEffect
import dev.ybrmst.dicodingstory.ui.viewmodel.signup.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      DicodingStoryTheme {
        val navController = rememberNavController()

        NavHost(
          navController,
          startDestination = RootRoute.Index,
          enterTransition = { EnterTransition.None },
          exitTransition = { ExitTransition.None }
        ) {
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
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { effect ->
      when (effect) {
        is AuthSideEffect.NavigateToNext -> {
          navController.navigate(effect.route) {
            popUpTo(RootRoute.Index) { inclusive = true }
          }
        }
      }
    }

    LaunchedEffect(state.status) {
      if (state.status !is UiStatus.Loading) {
        viewModel.redirect(
          if (state.user != null) RootRoute.Home
          else RootRoute.Onboarding
        )
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
      },
      onNavigateToSignUp = {
        navController.navigate(RootRoute.SignUp) {
          popUpTo(RootRoute.Onboarding) { inclusive = true }
        }
      },
      onSignIn = {
        navController.navigate(RootRoute.Home)
      },
    )
  }
}

private fun NavGraphBuilder.addSignUp(navController: NavController) {
  composable<RootRoute.SignUp> {
    val viewModel = it.scopedViewModel<SignUpViewModel>(navController)
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { effect ->
      when (effect) {
        is SignUpSideEffect.OnSuccessNavigate -> {
          navController.navigate(RootRoute.SignIn)
        }

        is SignUpSideEffect.ShowMessage -> {
          Toast.makeText(
            context,
            getLocalizedString(context, effect.message),
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }

    SignUpScreen(
      state = state,
      onBack = {
        navController.navigate(RootRoute.Onboarding) {
          popUpTo(RootRoute.Onboarding) { inclusive = true }
        }
      },
      onNavigateToSignIn = {
        navController.navigate(RootRoute.SignUp) {
          popUpTo(RootRoute.Onboarding) { inclusive = true }
        }
      },
      onNameInputChanged = viewModel::onNameChanged,
      onEmailInputChanged = viewModel::onEmailChanged,
      onPasswordInputChanged = viewModel::onPasswordChanged,
      onSignUp = viewModel::onSubmit,
    )
  }
}

private fun NavGraphBuilder.addHome(navController: NavController) {
  composable<RootRoute.Home> {
    val viewModel: AuthViewModel = it.scopedViewModel(navController)
    val state by viewModel.collectAsState()

    HomeScreen(user = state.user)
  }
}