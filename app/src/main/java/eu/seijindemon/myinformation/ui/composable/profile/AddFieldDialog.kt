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
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AutoSizeText(
                text = stringResource(id = R.string.add_field),
                maxFontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1
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
    var keyString by remember { mutableStateOf("Type here...") }
    var valueString by remember { mutableStateOf("Type here...") }

    Column(
        modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = keyString,
            onValueChange = { keyString = it }
        )
        OutlinedTextField(
            value = valueString,
            onValueChange = { valueString = it }
        )
        Button(
            onClick = {
                val list = mutableListOf<KeyValue>()
                if (!user.keysValues.isNullOrEmpty()) {
                    list.addAll(user.keysValues!!)
                }
                list.add(KeyValue(keyString, valueString))
                viewModel.updateUserKeysValues(list, user.id)
                openAddFieldDialog.value = false
                viewModel.getUserById(user.id)
                navController.navigate("profile")
            }
        ) {
            Text(
                text = stringResource(id = R.string.add_field)
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