package eu.seijindemon.myinformation.ui.composable.home

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
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText
import eu.seijindemon.myinformation.ui.composable.general.ErrorDialog
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel

@Composable
fun AddUserDialog(
    openAddUserDialog: MutableState<Boolean>,
    viewModel: AppViewModel
) {
    Dialog(
        onDismissRequest = {
            openAddUserDialog.value = false
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
                text = stringResource(id = R.string.add_user),
                maxFontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colors.primary
            )
            Divider()
            AddUser(
                viewModel = viewModel,
                openAddUserDialog = openAddUserDialog
            )
        }
    }
}

@Composable
fun AddUser(
    viewModel: AppViewModel,
    openAddUserDialog: MutableState<Boolean>
) {
    val users by viewModel.users.observeAsState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

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
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ),
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
                        users = users!!,
                        firstName = firstName,
                        lastName = lastName
                    ) -> {
                        errorMessage.value = "The user already exists."
                        openErrorDialog.value = true
                    }
                    else -> {
                        val user = User(firstName = firstName, lastName = lastName, keysValues = listOf())
                        viewModel.addUser(user)
                        openAddUserDialog.value = false
                    }
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.add_user)
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
    users: List<User>,
    firstName: String,
    lastName: String
): Boolean {
    if (!users.isNullOrEmpty()) {
        for (user: User in users) {
            if (user.firstName == firstName && user.lastName == lastName) {
                return true
            }
        }
    }
    return false
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_TYPE_NORMAL
)
@Composable
fun AddUserDialogPreview() {
    val viewModel: AppViewModel = viewModel()
    val openAddUserDialogPreview = remember { mutableStateOf(true) }
    MyInformationTheme {
        AddUserDialog(
            viewModel = viewModel,
            openAddUserDialog = openAddUserDialogPreview
        )
    }
}