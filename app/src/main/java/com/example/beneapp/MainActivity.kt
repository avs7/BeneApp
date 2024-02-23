package com.example.beneapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beneapp.ui.theme.BeneAppTheme
import org.json.JSONArray
import java.io.InputStreamReader
import java.io.StringReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // reading/parsing json data
        val br = baseContext.assets.open("Beneficiaries.json").bufferedReader()
        val beneficiariesJson = br.use { it.readText() }
        val beneficiaries = JSONArray(beneficiariesJson)

        setContent {
            BeneAppTheme {
                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Beneficiaries",
                            fontSize = 30.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
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
        // looping over beneficiaries in JSONArray
        items(beneficiaries.length()) { index ->
            val row = beneficiaries.getJSONObject(index)
            val firstName = row.getString("firstName")
            val lastName = row.getString("lastName")
            val beneType = row.getString("beneType")
            val designation = row.getString("designationCode")

            // setting up intent to pass to BeneficiaryDetails activity
            val detailsIntent = Intent(ctx, BeneficiaryDetails::class.java)
            detailsIntent.putExtra("row", row.toString())
            Card(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clickable {
                    ctx.startActivity(detailsIntent)
                }
            ) {


                Text(
                    text = "$firstName $lastName",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text(
                    text = "Type: $beneType",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text(
                    text = "Designation: ${
                        when (designation) {
                            "P" -> "Primary"
                            "C" -> "Contingent"
                            else -> ""
                        }
                    }",
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }


        }
    }
}