package eu.seijindemon.myinformation.ui.composable.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme

@Composable
fun ChangeLanguageDialog(
    openChangeLanguageDialog: MutableState<Boolean>
) {
    AlertDialog(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(12.dp),
        contentColor = Color.Black,
        onDismissRequest = {
            openChangeLanguageDialog.value = false
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .padding(5.dp),
                    onClick = {
                        // TODO Change language English
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
                        // TODO Change language Greek
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
    showBackground = true,
    backgroundColor = 0x989a82
)
@Composable
fun ChangeLanguageDialogPreview() {
    MyInformationTheme {
        val openChangeLanguageDialogPreview = remember { mutableStateOf(true) }
        ChangeLanguageDialog(
            openChangeLanguageDialog = openChangeLanguageDialogPreview
        )
    }
}