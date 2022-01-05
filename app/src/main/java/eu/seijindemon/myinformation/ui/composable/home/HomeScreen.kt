package eu.seijindemon.myinformation.ui.composable.home

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel
import eu.seijindemon.myinformation.ui.viewmodel.LanguageViewModel
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AppViewModel,
    languageViewModel: LanguageViewModel
) {
    // Language
    val currentLanguage = languageViewModel.language.observeAsState().value
    SetLanguage(language = currentLanguage!!)

    val users by viewModel.users.observeAsState()

    // Add User
    val openAddUserDialog = remember { mutableStateOf(false) }

    MyInformationTheme(
        darkTheme = false
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(id = R.string.settings),
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {
                                    navController.navigate("settings")
                                })
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    cutoutShape = CircleShape
                ) {

                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        openAddUserDialog.value = true
                    }
                ) {
                    Icon(
                        tint = MaterialTheme.colors.primary,
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_user)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        ) {
            if (users != null) {
                HomeContent(
                    navController = navController,
                    users = users!!,
                    viewModel = viewModel
                )
            }
            if (openAddUserDialog.value) {
                AddUserDialog(
                    openAddUserDialog = openAddUserDialog,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController,
    users: List<User>,
    viewModel: AppViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            all = 5.dp
        )
    ) {
        items(users) { user ->
            UserCard(
                user = user,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun SetLanguage(language: Int) {
    val locale = Locale(
        when(language) {
            0 -> "en"
            1 -> "el"
            else -> "en"
        }
    )
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_TYPE_NORMAL
)
@Composable
fun HomeContentPreview() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val users = listOf(
        User(1, "George", "Karanikolas", null),
        User(2, "Maria", "Lagou", null),
        User(3, "George", "Karanikolas", null),
        User(4, "Maria", "Lagou", null),
        User(5, "George", "Karanikolas", null),
        User(6, "Maria", "Lagou", null),
        User(7, "George", "Karanikolas", null)
    )
    MyInformationTheme {
        HomeContent(
            navController = navController,
            users = users,
            viewModel = viewModel
        )
    }
}