package com.example.beneapp

import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.beneapp.databinding.ActivityBeneficiaryDetailsBinding
import com.example.beneapp.ui.theme.BeneAppTheme
import org.json.JSONObject

class BeneficiaryDetails : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBeneficiaryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonRow = intent.extras?.getString("row")
        val beneficiary = JSONObject(jsonRow)


        setContent {
            BeneAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (jsonRow != null) {
                        Text(text = jsonRow, modifier = Modifier.clickable { finish() })
                    }
                }
            }
        }
    }
}