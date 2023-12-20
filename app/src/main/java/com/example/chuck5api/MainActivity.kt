package com.example.chuck5api

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.example.chuck5api.ui.theme.AndroidAPITheme
import com.example.chuck5api.ui.theme.Chuck5apiTheme
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chuck5apiTheme {
            }Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    chuckjoke()
                }
            }
        }
    }
}

@Composable
fun chuckjoke(modifier: Modifier = Modifier) {

    val client = OkHttpClient()

    var thejoke by remember { mutableStateOf("joke") }

    var started by remember { mutableStateOf(false) }

    LaunchedEffect(started) {
        fun loadjoke(){

            val req = Request.Builder().url("https:api.chucknorris.io/jokes/random").build()

            client.newCall(req).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOExeption) {
                    Log.i ("APIDEBUG", "Load not ok")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Error $response")

                        var responseString = response.body!!.string()
                        Log.i("APIDEBUG", responseString)
                    }
                }
                override fun onResponse(call: Call, response: Response) {

                val jokedata = Json {ignoreUnknownKeys = true).decodeFromString<Chuckjoke>(response)

                    thejoke = jokedata.value

                        }


                    }
                })
        }

        Column {
            Text(
                text = thejoke,
                modifier = modifier
            )
            Button(onClick = { loadjoke() }) {
                Text(text = "Load joke")
            }
        }
    }



            }

            }
        }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidAPITheme {
        Chuckjoke()
    }
}

        @Serializable
        data class chuckjoke (val: categories : List<String>,
                              val: created_at : String,
                              val: value : String)