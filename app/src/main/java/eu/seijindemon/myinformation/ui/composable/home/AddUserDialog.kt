package eu.seijindemon.myinformation.ui.composable.home

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AutoSizeText(
                text = stringResource(id = R.string.add_user),
                maxFontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1
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
    var firstName by remember { mutableStateOf("Type here...") }
    var lastName by remember { mutableStateOf("Type here...") }

    Column(
        modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                val user = User(firstName = firstName, lastName = lastName, keysValues = listOf())
                viewModel.addUser(user)
                openAddUserDialog.value = false

            }
        ) {
            Text(
                text = stringResource(id = R.string.add_user)
            )
        }
    }
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
        val user = User(1, "George", "Karanikolas", null)
        AddUserDialog(viewModel = viewModel, openAddUserDialog = openAddUserDialogPreview)
    }
}