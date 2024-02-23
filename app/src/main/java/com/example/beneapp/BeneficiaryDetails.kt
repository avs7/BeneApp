package com.example.beneapp

import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.beneapp.databinding.ActivityBeneficiaryDetailsBinding
import com.example.beneapp.ui.theme.BeneAppTheme
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class BeneficiaryDetails : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBeneficiaryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //getting data from MainActivity
        val jsonRow = intent.extras?.getString("row")

        val beneficiary = JSONObject(jsonRow)
        val firstName = beneficiary.getString("firstName")
        val lastName = beneficiary.getString("lastName")
        val ssn = beneficiary.getString("socialSecurityNumber")

        //formatting dob
        val dobInput = beneficiary.getString("dateOfBirth")
        val dob =  dobInput.replace(Regex("(\\d{2})(\\d{2})(\\d{4})"), "$1/$2/$3")

        //formatting phone
        val phoneInput = beneficiary.getString("phoneNumber")
        val phone = phoneInput.replace(Regex("(\\d{3})(\\d{3})(\\d{4})"), "($1) $2-$3")

        //parsing address from JSONObject
        val fullAddress = JSONObject(beneficiary.getString("beneficiaryAddress"))
        val addressFirst = fullAddress.getString("firstLineMailing")
        val addressCity = fullAddress.getString("city")
        val addressState = fullAddress.getString("stateCode")
        val addressZip = fullAddress.getString("zipCode")


        setContent {
            BeneAppTheme {
                    if (jsonRow != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "$firstName $lastName",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                            Text(
                                text = "SSN: $ssn",
                                style = TextStyle(
                                    color = Color.DarkGray,
                                    fontSize = 16.sp,
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Text(
                                text = "DOB: $dob",
                                style = TextStyle(
                                    color = Color.DarkGray,
                                    fontSize = 16.sp,
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Text(
                                text = "Phone: $phone",
                                style = TextStyle(
                                    color = Color.DarkGray,
                                    fontSize = 16.sp,
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                            )

                            Text(
                                text = "Address:\n$addressFirst\n$addressCity, $addressState $addressZip",
                                style = TextStyle(
                                    color = Color.DarkGray,
                                    fontSize = 16.sp,
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                            )

                            Button( onClick = { finish() }, modifier = Modifier.padding(10.dp)) {
                                Text(text = "Back")
                            }
                        }
                    }
                }
            }
        }
    }
