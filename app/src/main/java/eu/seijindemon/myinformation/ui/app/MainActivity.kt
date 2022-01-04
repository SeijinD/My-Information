package eu.seijindemon.myinformation.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import eu.seijindemon.myinformation.ui.composable.home.HomeScreen
import eu.seijindemon.myinformation.ui.composable.profile.ProfileScreen
import eu.seijindemon.myinformation.ui.composable.splash.SplashScreen
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel
import eu.seijindemon.myinformation.ui.viewmodel.LanguageViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyInformationTheme {
                NavigationComponent()
            }
        }
    }
}

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val languageViewModel: LanguageViewModel = viewModel()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController, viewModel, languageViewModel) }
        composable("profile") { ProfileScreen(navController, viewModel) }
    }
}