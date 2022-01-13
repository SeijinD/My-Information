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
fun AddFieldDialog(
    navController: NavController,
    openAddFieldDialog: MutableState<Boolean>,
    viewModel: AppViewModel,
    user: User
) {
    Dialog(
        onDismissRequest = {
            openAddFieldDialog.value = false
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
                text = stringResource(id = R.string.add_field),
                maxFontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colors.onBackground
            )
            Divider()
            AddField(
                navController = navController,
                viewModel = viewModel,
                user = user,
                openAddFieldDialog = openAddFieldDialog
            )
        }
    }
}

@Composable
fun AddField(
    navController: NavController,
    viewModel: AppViewModel,
    user: User,
    openAddFieldDialog: MutableState<Boolean>
) {
    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

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
            value = key,
            onValueChange = { key = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(

            )
        )
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(

            )
        )
        Divider()
        Button(
            onClick = {
                val list = mutableListOf<KeyValue>()
                if (!user.keysValues.isNullOrEmpty()) {
                    list.addAll(user.keysValues!!)
                }
                when {
                    key.isEmpty() -> {
                        errorMessage.value = "The key is empty."
                        openErrorDialog.value = true
                    }
                    value.isEmpty() -> {
                        errorMessage.value = "The value is empty."
                        openErrorDialog.value = true
                    }
                    checkIfExistField(
                        list = list,
                        key = key
                    ) -> {
                        errorMessage.value = "The field already exists."
                        openErrorDialog.value = true
                    }
                    else -> {
                        list.add(KeyValue(key, value))
                        viewModel.updateUserKeysValues(list, user.id)
                        openAddFieldDialog.value = false
                        viewModel.getUserById(user.id)
                        navController.navigate("profile")
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        ) {
            Text(
                text = stringResource(id = R.string.add_field)
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

fun checkIfExistField(
    list: MutableList<KeyValue>,
    key: String
): Boolean {
    if (!list.isNullOrEmpty()) {
        for (keyValue: KeyValue in list) {
            if (keyValue.key == key) {
                return true
            }
            return false
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
fun AddFieldDialogPreview() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val openAddFieldDialogPreview = remember { mutableStateOf(true) }
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas", listOf(
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890")
        ))
        AddFieldDialog(
            navController = navController,
            viewModel = viewModel,
            openAddFieldDialog = openAddFieldDialogPreview,
            user = user
        )
    }
}