package com.example.fuelcalculator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FuelCalculatorScreen(modifier: Modifier = Modifier) {
    // --- ЗАВДАННЯ 1 (Дані Варіанту 8) ---
    var hp by remember { mutableStateOf("1.4") }
    var cp by remember { mutableStateOf("70.5") }
    var sp by remember { mutableStateOf("1.7") }
    var np by remember { mutableStateOf("0.8") }
    var op by remember { mutableStateOf("1.9") }
    var wp by remember { mutableStateOf("7.0") }
    var ap by remember { mutableStateOf("16.7") }
    var result1 by remember { mutableStateOf("Результати завдання 1...") }

    // --- ЗАВДАННЯ 2 (Мазут) ---
    var cDaf by remember { mutableStateOf("85.5") }
    var hDaf by remember { mutableStateOf("11.2") }
    var oDaf by remember { mutableStateOf("0.8") }
    var sDaf by remember { mutableStateOf("2.5") }
    var qDaf by remember { mutableStateOf("40.4") }
    var wr by remember { mutableStateOf("2.0") }
    var ad by remember { mutableStateOf("0.15") }
    var result2 by remember { mutableStateOf("Результати завдання 2...") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Завдання 1: Склад палива", style = MaterialTheme.typography.headlineSmall)

        // Поля вводу (можна додавати інші за потреби)
        FuelInputField("H% (Водень)", hp) { hp = it }
        FuelInputField("C% (Вуглець)", cp) { cp = it }
        FuelInputField("W% (Вологість)", wp) { wp = it }
        FuelInputField("A% (Зольність)", ap) { ap = it }

        Button(onClick = {
            val h = hp.toDoubleOrNull() ?: 0.0
            val c = cp.toDoubleOrNull() ?: 0.0
            val s = sp.toDoubleOrNull() ?: 1.7
            val o = op.toDoubleOrNull() ?: 1.9
            val n = np.toDoubleOrNull() ?: 0.8
            val w = wp.toDoubleOrNull() ?: 0.0
            val a = ap.toDoubleOrNull() ?: 0.0

            // ВИПРАВЛЕНО: додано .0 для точності
            val kpc = 100.0 / (100.0 - w)
            val kpg = 100.0 / (100.0 - w - a)

            val qhp = (339.0 * c + 1030.0 * h - 108.8 * (o - s) - 25.0 * w) / 1000.0
            val qd = (qhp + 0.025 * w) * kpc
            val qdaf = (qhp + 0.025 * w) * kpg

            result1 = """
                KPC: ${"%.4f".format(kpc)}
                KPG: ${"%.4f".format(kpg)}
                Q_p_h: ${"%.4f".format(qhp)} МДж/кг
                Q_суха: ${"%.4f".format(qd)} МДж/кг
                Q_горюча: ${"%.4f".format(qdaf)} МДж/кг
            """.trimIndent()
        }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Розрахувати завдання 1")
        }
        Text(result1)

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // --- РОЗРАХУНОК МАЗУТУ ---
        Text("Завдання 2: Склад мазуту", style = MaterialTheme.typography.headlineSmall)
        FuelInputField("W_r % (Волога)", wr) { wr = it }
        FuelInputField("A_d % (Зола)", ad) { ad = it }

        Button(onClick = {
            val wrV = wr.toDoubleOrNull() ?: 0.0
            val adV = ad.toDoubleOrNull() ?: 0.0
            val qDafV = qDaf.toDoubleOrNull() ?: 40.4

            // Формули мазуту
            val ar = adV * (100.0 - wrV) / 100.0
            val kGr = (100.0 - wrV - ar) / 100.0
            val qr = qDafV * kGr - 0.025 * wrV

            result2 = """
                A_r (зольність робоча): ${"%.2f".format(ar)}%
                Коефіцієнт K_gr: ${"%.4f".format(kGr)}
                Q_r (теплота робоча): ${"%.2f".format(qr)} МДж/кг
            """.trimIndent()
        }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Розрахувати мазут")
        }
        Text(result2)

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun FuelInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    )
}