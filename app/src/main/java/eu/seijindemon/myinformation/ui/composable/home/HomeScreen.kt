package eu.seijindemon.myinformation.ui.composable.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.net.Uri
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

    val users by viewModel.users.observeAsState()

    val context = LocalContext.current
    val packageName = context.packageName

    // Language
    val resetLanguage = remember { mutableStateOf(false) }
    var currentLanguage = languageViewModel.language.observeAsState().value
    SetLanguage(position = currentLanguage!!)

    // Privacy Policy
    val openPrivacyPolicy = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://my-informations.flycricket.io/privacy.html"))
    }
    // Rate
    val openRate1 = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("market://details?id=$packageName"))
    }
    val openRate2 = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://play.google.com/store/apps/details?id=$packageName"))
    }
    // Share
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_SUBJECT, "My Information")
        putExtra(Intent.EXTRA_TEXT, "Download this App now: http://play.google.com/store/apps/details?id=${context.packageName}")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val openShare = remember { shareIntent }
    // Change Language
    val openChangeLanguageDialog = remember { mutableStateOf(false) }
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
                            imageVector = Icons.Filled.Language,
                            contentDescription = stringResource(id = R.string.change_language),
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {
                                    openChangeLanguageDialog.value = true
                                })
                        )
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = stringResource(id = R.string.share),
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {
                                    try {
                                        context.startActivity(openShare)
                                    } catch (e: ActivityNotFoundException) {
                                        // TODO Error Share
                                    }
                                })
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = stringResource(id = R.string.rate),
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {
                                    try {
                                        context.startActivity(openRate1)
                                    } catch (e: ActivityNotFoundException) {
                                        context.startActivity(openRate2)
                                    }
                                })
                        )
                        Icon(
                            imageVector = Icons.Filled.PrivacyTip,
                            contentDescription = stringResource(id = R.string.privacy_policy),
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {
                                    try {
                                        context.startActivity(openPrivacyPolicy)
                                    } catch (e: ActivityNotFoundException) {
                                        // TODO Error Privacy Policy
                                    }

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
            if (openChangeLanguageDialog.value) {
                ChangeLanguageDialog(
                    openChangeLanguageDialog = openChangeLanguageDialog,
                    resetLanguage = resetLanguage,
                    languageViewModel = languageViewModel
                )
            }
            if (openAddUserDialog.value) {
                AddUserDialog(
                    openAddUserDialog = openAddUserDialog,
                    viewModel = viewModel
                )
            }
            if (resetLanguage.value) {
                currentLanguage = languageViewModel.language.observeAsState().value
                SetLanguage(position = currentLanguage!!)
                resetLanguage.value = false
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
private fun SetLanguage(position: Int) {
    val locale = Locale(if (position == 1) "gr" else "en")
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
fun HomeScreenPreview() {
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