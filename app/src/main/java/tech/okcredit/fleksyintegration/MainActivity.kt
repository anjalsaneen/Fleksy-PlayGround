package tech.okcredit.fleksyintegration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.thingthing.fleksy.lib.api.LibraryConfiguration
import co.thingthing.fleksy.lib.model.LanguageFile
import tech.okcredit.fleksyintegration.ui.theme.FleksyIntegrationTheme

class MainActivity : ComponentActivity() {

    private val languageFile = LanguageFile.Asset("encrypted/resourceArchive-en-US.jet")

    private val libraryConfiguration = LibraryConfiguration(
        LibraryConfiguration.LicenseConfiguration(
            "<your-license-key>",
            "<your-license-secret>"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FleksyIntegrationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
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