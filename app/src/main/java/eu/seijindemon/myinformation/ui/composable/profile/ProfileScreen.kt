package eu.seijindemon.myinformation.ui.composable.profile

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.data.model.KeyValue
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: AppViewModel) {

    val user by  viewModel.user.observeAsState()

    // Add Field
    val openAddFieldDialog = remember { mutableStateOf(false) }

    // Delete User
    val openDeleteUserDialog = remember { mutableStateOf(false) }

    // Update User
    val openUpdateUserDialog = remember { mutableStateOf(false) }

    // Delete Field
    val openDeleteFieldDialog = remember { mutableStateOf(false) }

    // Update Field
    val openUpdateFieldDialog = remember { mutableStateOf(false) }

    MyInformationTheme(
        darkTheme = false
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
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
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = {
                            openUpdateUserDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Update,
                            contentDescription = ""
                        )
                    }
                    IconButton(
                        onClick = {
                            openDeleteUserDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PersonRemove,
                            contentDescription = ""
                        )
                    }

                    Spacer(Modifier.weight(1f, true))

                    IconButton(
                        onClick = {
                            openUpdateFieldDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChangeCircle,
                            contentDescription = ""
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(end = 10.dp),
                        onClick = {
                            openDeleteFieldDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.RemoveCircle,
                            contentDescription = ""
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        openAddFieldDialog.value = true
                    }
                ) {
                    Icon(
                        tint = MaterialTheme.colors.primary,
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_field)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        ) {
            if (user != null) {
                ProfileContent(
                    user = user!!
                )
            }
            if (openAddFieldDialog.value && user != null) {
                AddFieldDialog(
                    navController = navController,
                    openAddFieldDialog = openAddFieldDialog,
                    viewModel = viewModel,
                    user = user!!
                )
            }
            if (openDeleteUserDialog.value && user != null) {
                DeleteUserDialog(
                    navController = navController,
                    openDeleteUserDialog = openDeleteUserDialog,
                    viewModel = viewModel,
                    user = user!!
                )
            }
            if (openUpdateUserDialog.value && user != null) {
                UpdateUserDialog(
                    navController = navController,
                    openUpdateUserDialog = openUpdateUserDialog,
                    viewModel = viewModel,
                    user = user!!
                )
            }
            if (openDeleteFieldDialog.value && user != null) {
                DeleteFieldDialog(
                    navController = navController,
                    openDeleteFieldDialog = openDeleteFieldDialog,
                    viewModel = viewModel,
                    user = user!!
                )
            }
            if (openUpdateFieldDialog.value && user != null) {
                UpdateFieldDialog(
                    navController = navController,
                    openUpdateFieldDialog = openUpdateFieldDialog,
                    viewModel = viewModel,
                    user = user!!
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    user: User
) {
    ProfileCard(user = user)
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_TYPE_NORMAL
)
@Composable
fun ProfileContentPreview() {
    val user = User(1, "George", "Karanikolas", listOf(
        KeyValue("AMKA","1234567890"),
        KeyValue("AMKA","1234567890"),
        KeyValue("AMKA","1234567890")
    ))
    MyInformationTheme {
        ProfileContent(
            user = user
        )
    }
}