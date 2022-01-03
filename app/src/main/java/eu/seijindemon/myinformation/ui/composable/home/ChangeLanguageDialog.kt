package eu.seijindemon.myinformation.ui.composable.home

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.LanguageViewModel
import kotlinx.coroutines.launch

@Composable
fun ChangeLanguageDialog(
    openChangeLanguageDialog: MutableState<Boolean>,
    resetLanguage: MutableState<Boolean>,
    languageViewModel: LanguageViewModel
) {
    val languageScope = rememberCoroutineScope()

    AlertDialog(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        contentColor = Color.Black,
        onDismissRequest = {
            openChangeLanguageDialog.value = false
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .padding(5.dp),
                    onClick = {
                        languageScope.launch {
                            languageViewModel.saveLanguage(0)
                            resetLanguage.value = true
                            openChangeLanguageDialog.value = false
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.english)
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(5.dp),
                    onClick = {
                        languageScope.launch {
                            languageViewModel.saveLanguage(1)
                            resetLanguage.value = true
                            openChangeLanguageDialog.value = false
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.greek)
                    )
                }
            }
        },
        title = {
            AutoSizeText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "${stringResource(id = R.string.change_language)}:",
                maxFontSize = 18.sp,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_TYPE_NORMAL
)
@Composable
fun ChangeLanguageDialogPreview() {
    MyInformationTheme {
        val openChangeLanguageDialogPreview = remember { mutableStateOf(true) }
        val resetLanguagePreview = remember { mutableStateOf(false) }
        val languageViewModel: LanguageViewModel = viewModel()
        ChangeLanguageDialog(
            openChangeLanguageDialog = openChangeLanguageDialogPreview,
            resetLanguage = resetLanguagePreview,
            languageViewModel = languageViewModel
        )
    }
}