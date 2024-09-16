package com.example.praktikumpapb1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.praktikumpapb1.ui.theme.PraktikumPAPB1Theme
import com.example.praktikumpapb1.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PraktikumPAPB1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    var nameInput by remember { mutableStateOf("") }
    var nimInput by remember { mutableStateOf("") }
    var submittedName by remember { mutableStateOf("") }
    var submittedNim by remember { mutableStateOf("") }
    var isButtonClicked by remember { mutableStateOf(false) }
    var showCat by remember { mutableStateOf(false) }

    val buttonColor by animateColorAsState(
        targetValue = if (isButtonClicked) Color.Green else Color.LightGray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = submittedName,
            onValueChange = {},
            label = { Text("Submitted Name") },
            readOnly = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = submittedNim,
            onValueChange = {},
            label = { Text("Submitted NIM") },
            readOnly = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Isi nama di sini") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nimInput,
            onValueChange = { newText ->
                if (newText.all { it.isDigit() }) {
                    nimInput = newText
                }
            },
            label = { Text("Isi NIM di sini") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                submittedName = nameInput  // Simpan input nama ke output
                submittedNim = nimInput    // Simpan input NIM ke output
                isButtonClicked = !isButtonClicked
                showCat = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor), // Apply animated color
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = showCat,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "Cat Greeting",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PraktikumPAPB1Theme {
        MyScreen()
    }
}
