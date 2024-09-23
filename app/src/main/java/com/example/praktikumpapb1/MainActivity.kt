package com.example.praktikumpapb

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikumpapb.ui.theme.PraktikumPAPBTheme
import com.example.praktikumpapb.R
import androidx.compose.foundation.ExperimentalFoundationApi 

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PraktikumPAPBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen { name, nim ->
                        Toast.makeText(this, "Name: $name, NIM: $nim", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class) 
@Composable
fun MyScreen(onLongClick: (String, String) -> Unit) {
    var nameInput by remember { mutableStateOf("") }
    var nimInput by remember { mutableStateOf("") }
    var submittedName by remember { mutableStateOf("") }
    var submittedNim by remember { mutableStateOf("") }
    var isButtonClicked by remember { mutableStateOf(false) }
    var showCat by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current

    val isFormValid = nameInput.isNotBlank() && nimInput.isNotBlank()

    val buttonColor by animateColorAsState(
        targetValue = if (isFormValid) Color.Green else Color.Gray
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
                if (isFormValid) {
                    submittedName = nameInput
                    submittedNim = nimInput
                    isButtonClicked = !isButtonClicked
                    showCat = true
                }
            },
            enabled = isFormValid, 
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .padding(16.dp)
                .combinedClickable(
                    onClick = {
                        if (isFormValid) {
                            submittedName = nameInput
                            submittedNim = nimInput
                            isButtonClicked = !isButtonClicked
                            showCat = true
                        }
                    },
                    onLongClick = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

                            Toast.makeText(context, "Name: $nameInput, NIM: $nimInput", Toast.LENGTH_LONG).show()

                            onLongClick(nameInput, nimInput)
                    }
                )
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
    PraktikumPAPBTheme {
        MyScreen { _, _ -> }
    }
}
