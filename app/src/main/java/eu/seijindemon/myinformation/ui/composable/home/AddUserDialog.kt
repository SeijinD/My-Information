package eu.seijindemon.myinformation.ui.composable.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                .fillMaxWidth()
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AutoSizeText(
                text = stringResource(id = R.string.add_user),
                maxFontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Divider()
            AddUser(viewModel = viewModel)
        }
    }
}

@Composable
fun AddUser(
    viewModel: AppViewModel
) {
    var firstName by remember { mutableStateOf("Type here...") }
    var lastName by remember { mutableStateOf("Type here...") }

    Column(
        modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it }
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it }
        )
        Button(
            onClick = {
                val user = User(firstName = firstName, lastName = lastName)
                viewModel.addUser(user)
            }
        ) {
            Text(
                text = stringResource(id = R.string.add_user)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x989a82
)
@Composable
fun AddUserDialogPreview() {
    val viewModel: AppViewModel = viewModel()
    val openAddUserDialogPreview = remember { mutableStateOf(true) }
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas")
        AddUserDialog(viewModel = viewModel, openAddUserDialog = openAddUserDialogPreview)
    }
}