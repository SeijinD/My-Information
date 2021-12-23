package eu.seijindemon.myinformation.ui.composable.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel

@Composable
fun AddFieldDialog(
    openAddFieldDialog: MutableState<Boolean>,
    viewModel: AppViewModel
) {
    Dialog(
        onDismissRequest = {
            openAddFieldDialog.value = false
        }
    ) {

    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x989a82
)
@Composable
fun AddFieldDialogPreview() {
    val viewModel: AppViewModel = viewModel()
    val openAddFieldDialogPreview = remember { mutableStateOf(true) }
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas")
        AddFieldDialog(viewModel = viewModel, openAddFieldDialog = openAddFieldDialogPreview)
    }
}