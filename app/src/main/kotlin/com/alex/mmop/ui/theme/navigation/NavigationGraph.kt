package com.alex.mmop.ui.theme.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alex.mmop.BuildConfig
import com.alex.mmop.R
import com.alex.mmop.api.LOGS
import com.alex.mmop.common.appdata
import com.alex.mmop.common.others
import com.alex.mmop.composable.AlertProgressBar
import com.alex.mmop.composable.PermissonDialog
import com.alex.mmop.composable.UpdateDialog
import com.alex.mmop.kuroapi.KuroApi
import com.alex.mmop.kuroapi.secrets.Sapi
import com.alex.mmop.ui.screens.LoderUi
import com.alex.mmop.ui.screens.LoginUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author alex5402
 * Created 9/29/24 at 8:55 AM
 * NavigationGraph
 */

@Composable
fun NavigationGraph(
    padding: PaddingValues,
    viewmodel: appdata
) {
    val baseurl by viewmodel.url.collectAsState()

    val context = LocalContext.current
    val prefs = context.getSharedPreferences(
        "Settings", Context.MODE_PRIVATE
    )
    var progressdialog by remember { mutableStateOf(false) }
    val coritinescope = rememberCoroutineScope()

    val navController = rememberNavController()
    NavHost(navController, startDestination = Navigations.Login.route) {
        composable(Navigations.Login.route) {
            LoginUi(
                innerpadding = padding,
                loginlcicked = { key ->
                    progressdialog = true
                    if (key!!.isEmpty())
                        return@LoginUi Toast.makeText(
                            context,
                            R.string.enter_key,
                            Toast.LENGTH_SHORT
                        ).show()
                    viewmodel.setKey(key)
                    coritinescope.launch {
                        val kuroApi1 = async {
                            KuroApi(
                                baseurl = baseurl,
                                userkey = key,
                                Lisence = Sapi.getbaseurl()[2]
                            )
                        }
                        val kuroApi = kuroApi1.await()
                        val database = kuroApi.getDatabase()
                        if (database == null) {
                            withContext(Dispatchers.Main) {
                                progressdialog = false
                                Toast.makeText(
                                    context,
                                    R.string.error_data,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            return@launch
                        }
                        if (database.status == true) {
                            progressdialog = false
                            prefs.edit().putString("user_key", key).apply()
                            withContext(Dispatchers.Main) {
                                progressdialog = false
                                navController.navigate(Navigations.Loder.route) {
                                    popUpTo(Navigations.Login.route) { inclusive = true }
                                }
                            }
                        } else {
                            progressdialog = false
                            Toast.makeText(
                                context,
                                "${context.getString(R.string.lfailed)} ${database.reason}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                viewmodel = viewmodel // Keep this line
            )

            if (progressdialog) {
                AlertProgressBar(loadingmessage = "Please Wait")
            }
        }
        composable(Navigations.Loder.route) {
            LoderUi(
                appdata = viewmodel
            )
        }
    }

    var updatedialog by remember { mutableStateOf(false) }
    var updatemessage by remember { mutableStateOf("") }
    var updatetitle by remember { mutableStateOf("") }
    var downloadlink by remember { mutableStateOf("") }

    if (updatedialog) {
        UpdateDialog(
            onDismiss = {
                System.exit(1)
            },
            onconfirm = {
                openurl(downloadlink, context)
            },
            oncancel = {
                System.exit(1)
            },
            title = updatetitle,
            message = updatemessage,
            confirmText = "Download"
        )
    }

    LaunchedEffect(key1 = true) {
        kotlin.runCatching {
            val key = prefs.getString("user_key", "")
            if (key?.isEmpty() == true)
                return@runCatching
            val kuroApi = KuroApi(
                baseurl = baseurl,
                userkey = key,
                Lisence = Sapi.getbaseurl()[2]
            )
            val database = kuroApi.getDatabase()
            val checkupdate = database?.data?.updateversion?.toIntOrNull()
            val ddd = BuildConfig.VERSION_CODE
            if (checkupdate != null && checkupdate > ddd) {
                updatedialog = true
                updatetitle = database.data.updatetitle ?: "Update Available"
                updatemessage = database.data.updateinfo ?: "Fixed Old Bugs"
                downloadlink = database.data.updateapklink ?: "null"
            }
        }
    }
}

fun openurl(url: String, context: Context) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}
