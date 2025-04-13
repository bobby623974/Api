package com.alex.mmop.ui.screens

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alex.mmop.GameInfo
import com.alex.mmop.R
import com.alex.mmop.SociaApps
import com.alex.mmop.api.LOGS
import com.alex.mmop.common.ExpiryTime
import com.alex.mmop.common.appdata
import com.alex.mmop.common.others
import com.alex.mmop.common.others.isexternalObb
import com.alex.mmop.composable.AlertProgressBar2
import com.alex.mmop.composable.GameComponent
import com.alex.mmop.composable.SocialApps
import com.alex.mmop.databinding.AnimationBinding
import com.alex.mmop.kuroapi.KuroApi
import com.alex.mmop.kuroapi.secrets.Sapi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author alex5402
 * Created 9/28/24 at 4:28 PM
 * LoderUi
 */


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType", "InflateParams")
@Composable
fun LoderUi(
    appdata: appdata,
    expiryTime: ExpiryTime = viewModel()
) {
    var showprogress by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val binding = AnimationBinding.inflate(LayoutInflater.from(context))
    val baseurl by appdata.url.collectAsState()
    val userkey by appdata.key.collectAsState()
    var keystatus by remember { mutableStateOf(false) }
    var modstatus by remember { mutableStateOf("") }
    var announcement by remember { mutableStateOf("") }
    var copystatus by remember { mutableStateOf("") }
    if (showprogress) {
        AlertProgressBar2(loadingmessage = copystatus)
    }

    LaunchedEffect(Unit) {
        val kuroapi1 = async {
            KuroApi(
                baseurl = baseurl,
                Lisence = Sapi.getbaseurl()[2],
                userkey = userkey
            )
        }
        val kuroapi = kuroapi1.await()
        val database = kuroapi.getDatabase()?.data
        keystatus =  kuroapi.IsUSerValaid()
        database?.EXP?.let {
            expiryTime.setExpiryTimeAndStartTimer(
                it
            )
            LOGS.error("EXPIRY : $it")
        }
        delay(3000)
        if (!keystatus){
            System.exit(1)
        }
        modstatus = database?.statusText ?: "Nothing...  :)"
        announcement = database?.Announcement ?: "No Announcement... :)"
    }


    Scaffold { innetpadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innetpadding)) {
            AndroidView(
                factory = { ctx ->
                    binding.root
                },
                modifier = Modifier.fillMaxSize()
            )
            val gamelist = listOf(
                GameInfo.BgmiIndia(),
               // GameInfo.PubgGlobal(),
              //  GameInfo.PubgKorea()
            )

            LazyColumn {
                items(gamelist) { gameInfo: GameInfo ->
                    var installtext by remember { mutableStateOf("INSTALL") }
                     if (others.isAppInstalled(gameInfo.packagename)) installtext = "START" else installtext = "INSTALL"

                    GameComponent(
                        imageid = gameInfo.imageid!!,
                    //    gamename = gameInfo.gamename,
                     //   Versioncode = gameInfo.versioncode,
                    //    status = gameInfo.status,
                        buttonText = installtext,
                        appclosed = {
                            others.Killapp(gameInfo.packagename)
                            Toast.makeText(
                                context,
                                "ALL APP KILLED",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onclick = {

                            scope.launch {
                                showprogress = true
                        //        installtext = "Installing... ${gameInfo.gamename}"

                                if (!others.ispackage_installed_on_system(context, gameInfo.packagename)) {
                                    withContext(Dispatchers.Main) {
                                        showprogress = false
                                        installtext = "INSTALL"
                                        Toast.makeText(
                                            context,
                                            R.string.app_not_installed,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    return@launch
                                }
                                if (!isexternalObb(gameInfo.packagename)) {
                                    withContext(Dispatchers.Main) {
                                        showprogress = false
                                        installtext = "INSTALL"
                                        Toast.makeText(
                                            context,
                                            R.string.obb_not_found,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    return@launch
                                }

                                if (others.isAppInstalled(gameInfo.packagename)) {
                                    withContext(Dispatchers.Main) {
                                        installtext = "OPEN GAME"
                                        others.SetupLoader(
                                            context = context,
                                            downloadUrl = gameInfo.downloadurl,
                                            onCopyProcess = {
                                                copystatus = "Downloading... $it%"
                                            },
                                            onSuccess = {
                                                showprogress = false
                                                LOGS.info("Setup loader success")
                                                if (!others.isinternalObb(gameInfo.packagename)){
                                                    scope.launch {
                                                        showprogress = true
                                                        others.CopyObb(
                                                            packageName = gameInfo.packagename,
                                                            onFailure = { error ->
                                                                showprogress = false
                                                                scope.launch {
                                                                    withContext(Dispatchers.Main){
                                                                        Toast.makeText(
                                                                            context,
                                                                            "${context.getString(R.string.error_obb)} : $error",
                                                                            Toast.LENGTH_LONG
                                                                        ).show(
                                                                        )
                                                                    }
                                                                }
                                                            },
                                                            copySuccess = {
                                                                showprogress = false
                                                                LOGS.error("COPY OBB SUCCESS")
                                                                others.Launchapp(gameInfo.packagename)
                                                            },
                                                            copyProgress = { progress ->
                                                                copystatus = "Copying OBB.. $progress%"
                                                            }
                                                        )

                                                    }
                                                }else{
                                                    showprogress = false
                                                    others.Launchapp(gameInfo.packagename)
                                                }
                                            },
                                            zippass = gameInfo.zippass,
                                            onFailure = { err ->
                                                showprogress = false
                                                scope.launch {
                                                    withContext(Dispatchers.Main){
                                                        Toast.makeText(
                                                            context,
                                                            "${context.getString(R.string.err_download)} : $err",
                                                            Toast.LENGTH_LONG
                                                        ).show(
                                                        )
                                                    }
                                                }

                                            }
                                        )
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        others.installpackage(
                                            gameInfo.packagename,
                                            sucess = {
                                                showprogress = false
                                                installtext = "OPEN GAME"
                                            },
                                            fail = { throwable ->
                                                showprogress = false
                                                installtext = "INSTALL"
                                            }
                                        )
                                    }
                                }
                            }
                        },
                        isenabled = gameInfo.isenabled
                    )

                }
                item {
                    LazyRow {
                        items(SociaApps.entries.toTypedArray()) { socialapp: SociaApps ->
                            SocialApps(
                                onclick = {
                                    showprogress = true
                                    if (!others.ispackage_installed_on_system(context , socialapp.packagename))
                                        return@SocialApps Toast.makeText(
                                            context,
                                            R.string.app_not_installed,
                                            Toast.LENGTH_SHORT
                                        ).show().also {
                                            showprogress = false
                                        }
                                    others.RunSocialApps(packageName = socialapp.packagename , onSuccess = {showprogress = false}, onFailure = {showprogress = false})
                                },
                                appname = socialapp.name,
                                imageid = socialapp.imageid
                            )
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            )
                            .background(
                                color = Color(0xFF0483E8),
                                shape = RoundedCornerShape(20)
                            )
                            .defaultMinSize(
                                minHeight = 40.dp
                            )

                    ) {
                        val hoursLeft by expiryTime.hoursLeft.collectAsState()
                        val minutesLeft by expiryTime.minutesLeft.collectAsState()
                        val secondsLeft by expiryTime.secondsLeft.collectAsState()
                        val daysLeft by expiryTime.daysLeft.collectAsState()
                        Text(
                            "Expire Date : $daysLeft:Day   $hoursLeft:Hour  $minutesLeft:Minute  $secondsLeft:Second ",
                            style = Textstyles.whitetext,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .basicMarquee()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 5.dp,
                                    bottom = 5.dp
                                )
                                .align(Alignment.Center)

                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun LoderPreview() {
    LoderUi(
        appdata = appdata()
    )
}
