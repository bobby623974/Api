package com.alex.mmop.ui.screens


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alex.mmop.BuildConfig
import com.alex.mmop.R
import com.alex.mmop.api.LOGS
import com.alex.mmop.common.appdata
import com.alex.mmop.common.others
import com.alex.mmop.composable.PermissonDialog
import com.alex.mmop.composable.TransparentButton
import com.alex.mmop.composable.themedtextcolour
import com.alex.mmop.databinding.AnimationBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * @author alex5402
 * Created 9/28/24 at 4:28 PM
 * LoginUi
 */



@SuppressLint("CommitPrefEdits", "UnrememberedMutableState")
@Composable
fun LoginUi(
    innerpadding: PaddingValues,
    loginlcicked: (loginkey: String?) -> Unit,
    viewmodel: appdata = viewModel()
) {
    val boldtitle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = themedtextcolour(),
    )
    val normal = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        color = themedtextcolour()
    )
    var storagepermission by mutableStateOf(false)
    var installpermisson by mutableStateOf(false)
    val currentkey by viewmodel.key.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val compositon by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loginanimation))

    val binding = AnimationBinding.inflate(LayoutInflater.from(context))
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding),
        bottomBar = {
            Box(
                modifier = Modifier.background(
                    Color(0xFF23262a)
                )
            ) {
                Text(
                    "Version : ${BuildConfig.VERSION_NAME}",
                    style = Textstyles.caption,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                )
            }
        }
    ) { innerpadding ->
        LaunchedEffect(key1 = true) {
            val isgranted = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            if (!isgranted) {
                storagepermission = true
            }
            val isgranted2 = context.packageManager.canRequestPackageInstalls()
            if (!isgranted2) {
                installpermisson = true
            }
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (installpermisson) {
                PermissonDialog(
                    onDismiss = {
                        Toast.makeText(context, "You need to allow permission to get Obb file path access", Toast.LENGTH_SHORT).show()
                    },
                    onconfirm = {
                        installpermisson = false
                        Toast.makeText(context, "Please Relaunch the app to take effect", Toast.LENGTH_SHORT).show()
                        others.checkAndRequestUnknownSourcesPermission(activity = context as Activity)
                    },
                    oncancel = {
                        Toast.makeText(context, "You need to allow permission to install apps", Toast.LENGTH_SHORT).show()
                    },
                    title = "Obb File Permission",
                    message = "This app needs install permission to install manage OBB files",
                    confirmText = "Allow"
                )
            }
        }

        if (storagepermission) {
            PermissonDialog(
                onDismiss = {
                    Toast.makeText(context, "You need to allow permission to use the app", Toast.LENGTH_SHORT).show()
                },
                onconfirm = {
                    storagepermission = false
                    others.manageFilePermission(context as Activity)
                },
                oncancel = {
                    Toast.makeText(context, "You need to allow permission to use the app", Toast.LENGTH_SHORT).show()
                },
                title = "Storage Permission",
                message = "This app needs storage permission to download setup files",
                confirmText = "Allow"
            )
        }

        Box(
            modifier = Modifier.padding(innerpadding)
        ) {
            AndroidView(
                factory = { ctx -> binding.root },
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                LottieAnimation(
                    composition = compositon,
                    isPlaying = true,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                        .height(300.dp),
                    renderMode = RenderMode.AUTOMATIC,
                    alignment = Alignment.BottomCenter,
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = stringResource(R.string.loader_name),
                    style = boldtitle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(5.dp)
                )

                var isFocused by remember { mutableStateOf(false) }
                val focusRequester = remember { FocusRequester() }
                val focusManager = LocalFocusManager.current
                var passwordVisible by remember { mutableStateOf(false) }
                val clipboardManager = LocalClipboardManager.current
                val prefs = context.getSharedPreferences(
                    "Settings", Context.MODE_PRIVATE
                )

                LaunchedEffect(key1 = true) {
                    val key = prefs.getString("user_key", "")
                    if (key!!.isEmpty()) return@LaunchedEffect
                    viewmodel.setKey(key)
                }

                OutlinedTextField(
                    maxLines = 1,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusEvent { focusEvent -> isFocused = focusEvent.isFocused }
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth(1f),
                    value = currentkey,
                    placeholder = { Text("Enter your password") },
                    onValueChange = {
                        viewmodel.setKey(it)
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.pasteicon),
                            contentDescription = "Paste",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    clipboardManager
                                        .getText()
                                        .let { viewmodel.setKey(it.toString()) }
                                }
                        )
                    },
                    label = {
                        Text(text = "Paste or Enter Key", fontFamily = FontFamily.Monospace, fontSize = 15.sp)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = normal,
                )

                LaunchedEffect(key1 = isFocused) {
                    if (!isFocused) focusManager.clearFocus()
                }

                LaunchedEffect(key1 = passwordVisible) {
                    if (!passwordVisible) {
                        focusManager.clearFocus()
                    } else {
                        focusRequester.requestFocus()
                    }
                }

                Row(
                    modifier = Modifier
                        .clickable { passwordVisible = !passwordVisible }
                        .padding(start = 15.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
                ) {
                    Checkbox(checked = passwordVisible, onCheckedChange = null)
                    Text(
                        text = "Show Password",
                        style = normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 15.sp
                    )
                }

                TransparentButton(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(1f),
                    buttonName = "LOGIN",
                    onClick = {
                        focusManager.clearFocus()
                        if (currentkey.isEmpty()) {
                            Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                        } else {
                            loginlcicked(currentkey)
                        }
                    },
                    textColour = Color(0xFF1FC49B),
                )
            }
        }
    }
}


object Textstyles {

    val greenboldtext = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Green
    )
    val boldOrangeText = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFFA500)
    )
    val whitetext = TextStyle(
        fontSize = 18.sp,
        color = Color.White
    )

    val graytext = TextStyle(
        fontSize = 16.sp,
        color = Color.Gray
    )

    val caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
    )
}
