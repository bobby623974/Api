package com.alex.mmop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.alex.mmop.common.appdata
import com.alex.mmop.kuroapi.secrets.Sapi
import com.alex.mmop.kuroapi.secrets.enc.enc
import com.alex.mmop.ui.theme.ImguiloderTheme
import com.alex.mmop.ui.theme.navigation.NavigationGraph
import kotlinx.coroutines.launch


/**
 * @author alex5402
 * Created 9/28/24 at 12:12 PM
 * Mainactivity
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class Mainactivity : ComponentActivity() {

    init {
        System.loadLibrary("mmco")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appdata by viewModels<appdata>()

        lifecycleScope.launch {
            val url = Sapi.getbaseurl()
            val mainurl: String? = enc.decryptString(
                url[0],
                url[1]
            )
            mainurl?.let { appdata.setUrl(it) }

//                val key = enc.encryptString("https://api.just-panel.fun/SEPAX/connect")
//                LOGS.error("key1 :${key.first}  key2: ${key.second}")


        }

        setContent {
            ImguiloderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Scaffold { innerpadding ->
                        NavigationGraph(
                            padding = innerpadding,
                            appdata
                        )
                    }
                }
            }
        }
    }
}
