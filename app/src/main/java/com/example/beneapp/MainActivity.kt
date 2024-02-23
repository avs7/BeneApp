package com.example.beneapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.beneapp.ui.theme.BeneAppTheme
import org.json.JSONArray
import java.io.InputStreamReader
import java.io.StringReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val br = baseContext.assets.open("Beneficiaries.json").bufferedReader()
        val beneficiariesJson = br.use { it.readText() }
        val beneficiaries = JSONArray(beneficiariesJson)

        setContent {
            BeneAppTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    BeneList(beneficiaries)
                }
            }
        }
    }
}

@Composable
fun BeneList(beneficiaries: JSONArray, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current

    LazyColumn {
        items(beneficiaries.length()) { index ->
            val row = beneficiaries.getJSONObject(index)
            val firstName = row.getString("firstName")
            val lastName = row.getString("lastName")

            val detailsIntent = Intent(ctx, BeneficiaryDetails::class.java)
            detailsIntent.putExtra("row", row.toString())


            Text(
                text = "$firstName $lastName",
                modifier = Modifier.clickable { ctx.startActivity(detailsIntent) }
            )
        }
    }
}