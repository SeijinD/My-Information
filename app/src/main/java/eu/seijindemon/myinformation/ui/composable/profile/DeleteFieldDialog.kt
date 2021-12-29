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
fun DeleteFieldDialog(
    navController: NavController,
    openDeleteFieldDialog: MutableState<Boolean>,
    viewModel: AppViewModel,
    user: User
) {
    Dialog(
        onDismissRequest = {
            openDeleteFieldDialog.value = false
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
                text = stringResource(id = R.string.delete_field),
                maxFontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Divider()
            DeleteField(
                navController = navController,
                viewModel = viewModel,
                user = user,
                openDeleteFieldDialog = openDeleteFieldDialog
            )
        }
    }
}

@Composable
fun DeleteField(
    navController: NavController,
    viewModel: AppViewModel,
    user: User,
    openDeleteFieldDialog: MutableState<Boolean>
) {
    var key by remember { mutableStateOf("") }

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
            onValueChange = { key = it }
        )
        Divider()
        Button(
            onClick = {
                for (item: KeyValue in list) {
                    if (item.key == key) {
                        list.remove(item)
                        viewModel.updateUserKeysValues(list, user.id)
                        openDeleteFieldDialog.value = false
                        viewModel.getUserById(user.id)
                        navController.navigate("profile")
                    }
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.delete_field)
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
fun DeleteFieldDialogPreview() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val openDeleteFieldDialogPreview = remember { mutableStateOf(true) }
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas", listOf(
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890")
        ))
        DeleteFieldDialog(
            navController = navController,
            viewModel = viewModel,
            openDeleteFieldDialog = openDeleteFieldDialogPreview,
            user = user
        )
    }
}