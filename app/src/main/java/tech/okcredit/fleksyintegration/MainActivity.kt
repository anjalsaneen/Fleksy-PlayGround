package tech.okcredit.fleksyintegration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.thingthing.fleksy.lib.api.FleksyLib
import co.thingthing.fleksy.lib.api.LibraryConfiguration
import co.thingthing.fleksy.lib.model.Candidate
import co.thingthing.fleksy.lib.model.LanguageFile
import co.thingthing.fleksy.lib.model.TypingContext
import tech.okcredit.fleksyintegration.ui.theme.FleksyIntegrationTheme

class MainActivity : ComponentActivity() {

    private val languageFile = LanguageFile.Asset("encrypted/resourceArchive-en-US.jet")

    private val libraryConfiguration = LibraryConfiguration(
        LibraryConfiguration.LicenseConfiguration(
            BuildConfig.LICENCE_KEY,
            BuildConfig.LICENCE_SECRET
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fleksyLib = FleksyLib(this.applicationContext, languageFile, libraryConfiguration)

        enableEdgeToEdge()
        setContent {


            FleksyIntegrationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp) // If innerPadding and 16.dp are both needed
                    ) {
                        val text = remember { mutableStateOf(TextFieldValue("")) }

                        val nextWordsList = remember { mutableStateOf(emptyList<Candidate>()) }
                        val currentWords = remember { mutableStateOf(emptyList<Candidate>()) }

                        LaunchedEffect(text.value.text) {
                            val typingContext = TypingContext(text.value.text, text.value.text.length)

                            nextWordsList.value = fleksyLib.nextWordPrediction(typingContext).getOrNull() ?: emptyList()
                            currentWords.value = fleksyLib.currentWordPrediction(typingContext).getOrNull() ?: emptyList()
                        }

                        OutlinedTextField(
                            value = text.value,
                            placeholder = { Text("Enter text") },
                            onValueChange = { newText ->
                                text.value = newText
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            "Current word predictions:",
                            fontWeight = FontWeight.Bold
                        )
                        currentWords.value.forEach {
                            Text(it.type.name + ": " + it.label)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            "Next word predictions:",
                            fontWeight = FontWeight.Bold
                        )
                        nextWordsList.value.forEach {
                            Text(it.type.name + ": " + it.label)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FleksyIntegrationTheme {
        Greeting("Android")
    }
}