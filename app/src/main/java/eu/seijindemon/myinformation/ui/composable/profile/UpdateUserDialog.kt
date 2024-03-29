package eu.seijindemon.myinformation.ui.composable.profile

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.data.model.KeyValue
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText
import eu.seijindemon.myinformation.ui.composable.general.ErrorDialog
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel

@Composable
fun UpdateUserDialog(
    navController: NavController,
    openUpdateUserDialog: MutableState<Boolean>,
    viewModel: AppViewModel,
    user: User
) {
    Dialog(
        onDismissRequest = {
            openUpdateUserDialog.value = false
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AutoSizeText(
                text = stringResource(id = R.string.update_user),
                maxFontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colors.onBackground
            )
            Divider()
            UpdateUser(
                user = user,
                viewModel = viewModel,
                openUpdateUserDialog = openUpdateUserDialog,
                navController = navController
            )
        }
    }
}

@Composable
fun UpdateUser(
    user: User,
    viewModel: AppViewModel,
    openUpdateUserDialog: MutableState<Boolean>,
    navController: NavController,
) {
    val users by viewModel.users.observeAsState()
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }

    val openErrorDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(

            )
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(

            )
        )
        Divider()
        Button(
            onClick = {
                when {
                    firstName.isEmpty() -> {
                        errorMessage.value = "The firstName is empty."
                        openErrorDialog.value = true
                    }
                    lastName.isEmpty() -> {
                        errorMessage.value = "The lastName is empty."
                        openErrorDialog.value = true
                    }
                    checkIfExistUser(
                        alreadyUser = user,
                        users = users!!,
                        firstName = firstName,
                        lastName = lastName
                    ) -> {
                        errorMessage.value = "The user already exists."
                        openErrorDialog.value = true
                    }
                    else -> {
                        val updatedUser = User(id = user.id, firstName = firstName, lastName = lastName, keysValues = user.keysValues)
                        viewModel.updateUser(updatedUser)
                        openUpdateUserDialog.value = false
                        navController.navigate("home")
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        ) {
            Text(
                text = stringResource(id = R.string.update_user)
            )
        }
    }

    if (openErrorDialog.value) {
        ErrorDialog(
            errorMessage = errorMessage,
            openErrorDialog = openErrorDialog
        )
    }
}

fun checkIfExistUser(
    alreadyUser: User,
    users: List<User>,
    firstName: String,
    lastName: String
): Boolean {
    if (!users.isNullOrEmpty()) {
        for (user: User in users) {
            if (user.firstName == firstName &&
                user.lastName == lastName) {
                if (user.firstName == alreadyUser.firstName &&
                    user.lastName == alreadyUser.lastName) {
                    return false
                }
                return true
            }
        }
        return false
    }
    return false
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_TYPE_NORMAL
)
@Composable
fun UpdateUserDialogPreview() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val openUpdateUserDialogPreview = remember { mutableStateOf(true) }
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas", listOf(
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890")
        ))
        UpdateUserDialog(
            navController = navController,
            viewModel = viewModel,
            openUpdateUserDialog = openUpdateUserDialogPreview,
            user = user
        )
    }
}