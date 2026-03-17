package com.example.emissioncalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    EmissionCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun EmissionCalculatorScreen() {
    var coalMass by remember { mutableStateOf("1096363") }
    var oilMass by remember { mutableStateOf("70945") }
    var resultText by remember { mutableStateOf("Результати з'являться тут") }

    Column(
        modifier = Modifier.padding(20.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Розрахунок викидів (ПР2)", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = coalMass, onValueChange = { coalMass = it }, label = { Text("Вугілля (т)") })
        OutlinedTextField(value = oilMass, onValueChange = { oilMass = it }, label = { Text("Мазут (т)") })

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val bC = coalMass.toDoubleOrNull() ?: 0.0
            val bO = oilMass.toDoubleOrNull() ?: 0.0

            val kC = (1000000 / 20.47) * 0.8 * (25.2 / (100 - 1.5)) * (1 - 0.985)
            val eC = 0.000001 * kC * 20.47 * bC
            val kO = 0.57
            val eO = 0.000001 * kO * 39.48 * bO

            resultText = "Показник вугілля: ${"%.2f".format(kC)}\n" +
                    "Викид вугілля: ${"%.2f".format(eC)} т\n" +
                    "Показник мазуту: ${"%.2f".format(kO)}\n" +
                    "Викид мазуту: ${"%.2f".format(eO)} т\n" +
                    "РАЗОМ: ${"%.2f".format(eC + eO)} т"
        }) {
            Text("ОБЧИСЛИТИ")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(resultText, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        EmissionCalculatorScreen()
    }
}