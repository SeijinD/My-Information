package eu.seijindemon.myinformation.ui.composable.profile

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.data.model.KeyValue
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel

@Composable
fun UpdateFieldDialog(
    navController: NavController,
    openUpdateFieldDialog: MutableState<Boolean>,
    viewModel: AppViewModel,
    user: User
) {
    Dialog(
        onDismissRequest = {
            openUpdateFieldDialog.value = false
        }
    ) {
        var key by remember { mutableStateOf("") }
        var value by remember { mutableStateOf("") }

        val list = mutableListOf<KeyValue>()
        if (!user.keysValues.isNullOrEmpty()) {
            list.addAll(user.keysValues!!)
        }

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
                text = stringResource(id = R.string.update_field),
                maxFontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Divider()
            OutlinedTextField(
                value = key,
                onValueChange = { key = it }
            )
            OutlinedTextField(
                value = value,
                onValueChange = { value = it }
            )
            Divider()
            Button(
                onClick = {
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
            ) {
                Text(
                    text = stringResource(id = R.string.update_field)
                )
            }
        }
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
            user = user
        )
    }
}