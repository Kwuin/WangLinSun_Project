package com.example.cs501finalproject.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R

@Composable
fun Profile(navController: NavController) {
        Column(){
            TopSection()
            AccountSection(navController)
            GeneralSection(navController)
            AboutSection(navController)
            Spacer(modifier = Modifier.weight(1f))

            //NavigationBar(navController)
        }

}

@Composable
fun TopSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(height = 130.dp)
            .background(
                brush = Brush.linearGradient(
                    0f to Color(0xffe6d6f0),
                    1f to Color(0xff9f88ae)))
    ) {
        Text(
            text = stringResource(R.string.Settings_Settings),
            // M3/title/large
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),),
            modifier = Modifier
                .padding(horizontal = 36.dp, vertical = 20.dp)
        )
        Text(
            text = "Welcome, Tom",
            // M3/title/medium
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
                letterSpacing = 0.15.sp,),
            modifier = Modifier
                .padding(horizontal = 36.dp, vertical = 55.dp)
        )
    }
}


@Composable
fun SettingItem(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = TextFieldValue(value),
            onValueChange = { newValue ->
                onValueChange(newValue.text)
            },
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AccountSection(navController: NavController) {
    Surface() {
        Column() {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(color = Color(0xFFD4CADC)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.Settings_Account),

                    // M3/label/large
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(0xFFF3EDF7))
            ){
                Text(
                    text = stringResource(R.string.Settings_Profile),
                    // M3/title/large
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(0xFFF3EDF7))
            ){
                Text(
                    text = stringResource(R.string.Settings_Instagram_Binding),
                    // M3/title/large
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
        }
    }
}
@Composable
fun GeneralSection(navController: NavController) {
    Surface() {
        Column() {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(color = Color(0xFFD4CADC))
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.Settings_General),

                    // M3/label/large
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(0xFFF3EDF7))
                    .clickable { navController.navigate("settingsLanguage") }
            ){
                Text(
                    text = stringResource(R.string.Settings_Language),
                    // M3/title/large
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(0xFFF3EDF7))
                    .clickable { navController.navigate("settingsTheme") }
            ){
                Text(
                    text = stringResource(R.string.Settings_Theme),
                    // M3/title/large
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(0xFFF3EDF7))
            ){
                Text(
                    text = stringResource(R.string.Settings_Cloud_Sync),
                    // M3/title/large
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(0xFFF3EDF7))
            ){
                Text(
                    text = stringResource(R.string.Settings_Notification),
                    // M3/title/large
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
        }

    }
}

@Composable
fun AboutSection(navController: NavController){
    Surface() {
        Column() {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(color = Color(0xFFD4CADC)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.Settings_About),

                    // M3/label/large
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color(0xFFF3EDF7))
                    .clickable { navController.navigate("settingsAbout") }
            ) {
                Text(
                    text = stringResource(R.string.Settings_About),
                    // M3/title/large
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileSettingsPage() {
//    GradientBackground()
    Profile(rememberNavController())
}
