package com.example.tugas6pbb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas6pbb.ui.theme.Tugas6PBBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tugas6PBBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    var idrAmount by remember { mutableStateOf("10000") }
    var selectedCurrency by remember { mutableStateOf("USD") }

    val usdRate = 0.000061 // 8 Mei, 13.09 UTC
    val yuanRate = 0.00044 // 8 Mei, 13.09 UTC

    val input = idrAmount.toDoubleOrNull() ?: 0.0
    val resultValue = if (selectedCurrency == "USD") input * usdRate else input * yuanRate
    val symbol = if (selectedCurrency == "USD") "$" else "¥"
    val resultLabel = if (selectedCurrency == "USD") "US Dollar (USD)" else "Chinese Yuan (CNY)"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Konverter mata uang rupiah", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Konversi Rupiah ke USD dan Yuan", fontSize = 16.sp, modifier = Modifier.padding(bottom = 24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CurrencyToggleButton("USD", "$", selectedCurrency == "USD") { selectedCurrency = "USD" }
            Spacer(modifier = Modifier.width(8.dp))
            CurrencyToggleButton("CNY", "¥", selectedCurrency == "CNY") { selectedCurrency = "CNY" }
        }

        Text("Indonesian Rupiah (IDR)", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
        OutlinedTextField(
            value = idrAmount,
            onValueChange = { idrAmount = it },
            leadingIcon = { Text("Rp") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(resultLabel, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = "%.2f".format(resultValue),
            onValueChange = {},
            readOnly = true,
            leadingIcon = { Text(symbol) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CurrencyToggleButton(label: String, symbol: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Text("$symbol $label")
    }
}

@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    Tugas6PBBTheme {
        AppContent()
    }
}
