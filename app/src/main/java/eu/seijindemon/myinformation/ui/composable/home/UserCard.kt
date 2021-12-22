package eu.seijindemon.myinformation.ui.composable.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme
import eu.seijindemon.myinformation.ui.viewmodel.AppViewModel

@Composable
fun UserCard(
    user: User,
    navController: NavController,
    viewModel: AppViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 5.dp,
                end = 5.dp,
                bottom = 5.dp
            )
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .padding(all = 5.dp)
        ) {
            AutoSizeText(
                text = "${user.firstName} ${user.lastName}",
                maxFontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(all = 5.dp)
                .clip(RoundedCornerShape(8.dp)),
            onClick = {
                viewModel.getUserById(user.id)
                navController.navigate("profile")
            }
        ) {
            AutoSizeText(
                text = stringResource(id = R.string.profile),
                maxFontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x989a82
)
@Composable
fun UserCardPreview() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas")
        UserCard(
            user = user,
            navController = navController,
            viewModel = viewModel
        )
    }
}