import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Калькулятор палива (Варіант 8) - Windows",
        state = rememberWindowState(width = 500.dp, height = 800.dp)
    ) {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                FuelCalculatorScreen()
            }
        }
    }
}

@Composable
fun FuelCalculatorScreen() {
    var hp by remember { mutableStateOf("1.4") }
    var cp by remember { mutableStateOf("70.5") }
    var sp by remember { mutableStateOf("1.7") }
    var np by remember { mutableStateOf("0.8") }
    var op by remember { mutableStateOf("1.9") }
    var wp by remember { mutableStateOf("7.0") }
    var ap by remember { mutableStateOf("16.7") }
    var result1 by remember { mutableStateOf("Результати з'являться тут...") }

    var wr by remember { mutableStateOf("2.0") }
    var ad by remember { mutableStateOf("0.15") }
    var qDaf by remember { mutableStateOf("40.4") }
    var result2 by remember { mutableStateOf("Результати мазуту...") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Завдання 1: Склад палива", style = MaterialTheme.typography.h5)

        FuelInputField("H% (Водень)", hp) { hp = it }
        FuelInputField("C% (Вуглець)", cp) { cp = it }
        FuelInputField("W% (Волога)", wp) { wp = it }
        FuelInputField("A% (Зола)", ap) { ap = it }

        Button(onClick = {
            val h = hp.toDoubleOrNull() ?: 0.0
            val c = cp.toDoubleOrNull() ?: 0.0
            val s = sp.toDoubleOrNull() ?: 1.7
            val o = op.toDoubleOrNull() ?: 1.9
            val w = wp.toDoubleOrNull() ?: 0.0
            val a = ap.toDoubleOrNull() ?: 0.0

            val kpc = 100.0 / (100.0 - w)
            val kpg = 100.0 / (100.0 - w - a)
            val qhp = (339.0 * c + 1030.0 * h - 108.8 * (o - s) - 25.0 * w) / 1000.0

            result1 = "KPC: ${"%.4f".format(kpc)}\nKPG: ${"%.4f".format(kpg)}\nQHP: ${"%.4f".format(qhp)} МДж/кг"
        }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Розрахувати завдання 1")
        }
        Text(result1)

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Завдання 2: Розрахунок мазуту", style = MaterialTheme.typography.h5)
        FuelInputField("W_r% (Волога)", wr) { wr = it }
        FuelInputField("A_d% (Зола суха)", ad) { ad = it }

        Button(onClick = {
            val wrVal = wr.toDoubleOrNull() ?: 0.0
            val adVal = ad.toDoubleOrNull() ?: 0.0
            val qDafVal = qDaf.toDoubleOrNull() ?: 40.4

            val ar = adVal * (100.0 - wrVal) / 100.0
            val kGr = (100.0 - wrVal - ar) / 100.0
            val qr = qDafVal * kGr - 0.025 * wrVal

            result2 = "Ar (Зольність робоча): ${"%.2f".format(ar)}%\nQr (Теплота робоча): ${"%.2f".format(qr)} МДж/кг"
        }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Розрахувати мазут")
        }
        Text(result2)
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