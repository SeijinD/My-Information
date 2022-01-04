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
fun UpdateFieldDialog(
    navController: NavController,
    openUpdateFieldDialog: MutableState<Boolean>,
    viewModel: AppViewModel,
    user: User,
    selectedFieldKey: MutableState<String>,
    selectedFieldValue: MutableState<String>
) {
    Dialog(
        onDismissRequest = {
            openUpdateFieldDialog.value = false
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
                text = stringResource(id = R.string.update_field),
                maxFontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Divider()
            UpdateField(
                navController = navController,
                viewModel = viewModel,
                user = user,
                openUpdateFieldDialog = openUpdateFieldDialog,
                selectedFieldKey = selectedFieldKey,
                selectedFieldValue = selectedFieldValue
            )
        }
    }
}

@Composable
fun UpdateField(
    navController: NavController,
    viewModel: AppViewModel,
    user: User,
    openUpdateFieldDialog: MutableState<Boolean>,
    selectedFieldKey: MutableState<String>,
    selectedFieldValue: MutableState<String>
) {
    var key by remember { mutableStateOf(selectedFieldKey.value) }
    var value by remember { mutableStateOf(selectedFieldValue.value) }

    val openErrorDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val list = mutableListOf<KeyValue>()
    if (!user.keysValues.isNullOrEmpty()) {
        list.addAll(user.keysValues!!)
    }

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
            enabled = false
        )
        OutlinedTextField(
            value = value,
            onValueChange = { value = it }
        )
        Divider()
        Button(
            onClick = {
                when {
                    key.isEmpty() -> {
                        errorMessage.value = "The key is empty."
                        openErrorDialog.value = true
                    }
                    value.isEmpty() -> {
                        errorMessage.value = "The value is empty."
                        openErrorDialog.value = true
                    }
                    else -> {
                        for (item: KeyValue in list) {
                            if (item.key == key) {
                                val updatedItem: KeyValue = KeyValue(key = item.key, value = value)
                                list.remove(item)
                                list.add(updatedItem)
                                viewModel.updateUserKeysValues(list, user.id)
                                openUpdateFieldDialog.value = false
                                viewModel.getUserById(user.id)
                                navController.navigate("profile")
                            }
                        }
                    }
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.update_field)
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

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_TYPE_NORMAL
)
@Composable
fun UpdateFieldDialogPreview() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val openUpdateFieldDialogPreview = remember { mutableStateOf(true) }
    val selectedFieldKey = remember { mutableStateOf("") }
    val selectedFieldValue = remember { mutableStateOf("") }
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas", listOf(
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890")
        ))
        UpdateFieldDialog(
            navController = navController,
            viewModel = viewModel,
            openUpdateFieldDialog = openUpdateFieldDialogPreview,
            user = user,
            selectedFieldKey = selectedFieldKey,
            selectedFieldValue = selectedFieldValue
        )
    }
}