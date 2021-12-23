package eu.seijindemon.myinformation.ui.composable.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: AppViewModel) {

    // Add User
    val openAddFieldDialog = remember { mutableStateOf(false) }

    MyInformationTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    actions = {
                        Icon(
                            Icons.Filled.Language,
                            contentDescription = stringResource(id = R.string.change_language),
                            tint = Color.White,
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {

                                })
                        )
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = stringResource(id = R.string.share),
                            tint = Color.White,
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {

                                })
                        )
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = stringResource(id = R.string.rate),
                            tint = Color.White,
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {

                                })
                        )
                        Icon(
                            Icons.Filled.PrivacyTip,
                            contentDescription = stringResource(id = R.string.privacy_policy),
                            tint = Color.White,
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .clickable(onClick = {

                                })
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
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
                        openAddFieldDialog.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_field)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        ) {
            ProfileContent(
                viewModel = viewModel
            )
            if (openAddFieldDialog.value) {
                AddFieldDialog(openAddFieldDialog = openAddFieldDialog, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ProfileContent(
    viewModel: AppViewModel
) {
    val user by  viewModel.user.observeAsState()
    Text(
        text = user!!.firstName,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = user!!.lastName,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0x989a82
)
@Composable
fun ProfileContentPreview() {
    val viewModel: AppViewModel = viewModel()
    MyInformationTheme {
        ProfileContent(
            viewModel = viewModel
        )
    }
}