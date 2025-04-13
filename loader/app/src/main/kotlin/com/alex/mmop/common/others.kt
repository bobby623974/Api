package com.alex.mmop.common

import android.Manifest
import android.Manifest.permission.REQUEST_INSTALL_PACKAGES
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alex.mmop.api.LOGS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import net_62v.external.MetaActivityManager
import net_62v.external.MetaApplicationInstaller
import net_62v.external.MetaPackageManager
import net_62v.external.MetaStorageManager
import okio.IOException
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author alex5402
 * Created 9/30/24 at 8:18 AM
 */
object others {

    private const val REQUEST_CODE_UNKNOWN_SOURCES = 2001

    fun Killapp(packageName: String) {
        MetaActivityManager.killAllApps()
    }

    fun RunSocialApps(packageName: String , onSuccess: () -> Unit , onFailure: (String?) -> Unit) {
        kotlin.runCatching {
            if (isAppInstalled(packageName)) {
                onSuccess.invoke()
                Launchapp(packageName)
            }else{
                installpackage(packageName , sucess = {
                    onSuccess.invoke()
                    Launchapp(packageName)
                } , fail = {
                    onFailure.invoke("App not found")
                })
            }
        }
    }

    suspend fun SetupLoader(
        context: Context,
        downloadUrl: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        zippass : String ,
        onCopyProcess: (Int) -> Unit = {}
    ) {
        withContext(Dispatchers.IO) {
            runCatching {
                val url = URL(downloadUrl)
                val timestamp = System.currentTimeMillis()
                val outputFile = File(context.cacheDir.path, "$timestamp.zip")
                (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "GET"
                    connectTimeout = 5000
                    readTimeout = 5000

                    if (responseCode != HttpURLConnection.HTTP_OK) {
                        throw IOException("Failed to download file: HTTP $responseCode")
                    }

                    val fileLength = contentLength
                    if (fileLength <= 0) {
                        throw IOException("Invalid file length received.")
                    }
                    inputStream.use { input ->
                        BufferedInputStream(input).use { bufferedInput ->
                            FileOutputStream(outputFile).use { output ->
                                val dataBuffer = ByteArray(1024)
                                var bytesRead: Int
                                var totalBytesRead = 0

                                while (bufferedInput.read(dataBuffer).also { bytesRead = it } != -1) {
                                    output.write(dataBuffer, 0, bytesRead)
                                    totalBytesRead += bytesRead
                                    onCopyProcess.invoke((totalBytesRead * 100) / fileLength)
                                }
                                output.flush()
                                outputFile.setExecutable(true, false)
                              outputFile.let { thefile ->
                                  kotlin.runCatching {
                                      val zipfile = ZipFile(thefile)
                                      if (zipfile.isEncrypted){
                                          zipfile.setPassword(zippass.toCharArray())
                                      }
                                      zipfile.extractAll(context.filesDir.path)
                                      RenameLib(context)
                                      onSuccess.invoke()
                                  }.onFailure {
                                      onFailure.invoke(it.message)
                                  }
                              }
                            }
                        }
                    }
                }
            }.onFailure {
                LOGS.error("Download Error: ${it.message}")
                onFailure.invoke(it.message)
            }
        }
    }
    fun RenameLib(context: Context){
        kotlin.runCatching {
            val getlibs = File(context.filesDir.path).listFiles()
            getlibs?.forEach { currentfile ->
                if (currentfile.name.endsWith(".so")){
                    val newnamefile = File(context.filesDir.path ,"crashinfo.ttf")
                    currentfile.renameTo(newnamefile)
                    currentfile.setExecutable(true , false)
                    currentfile.setReadable(true , false)
                    currentfile.setWritable(true , false)
                }
            }
        }
    }





    fun manageFilePermission(activity: Activity) {
         val REQUEST_CODE_STORAGE_PERMISSIONS = 1001
        // List of permissions for Android versions below Android 11 (Scoped Storage)
        val permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val missingPermissions = permissions.filter { permission ->
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                missingPermissions.toTypedArray(),
                REQUEST_CODE_STORAGE_PERMISSIONS
            )
        }

        // For Android 11 (API 30) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    // Request MANAGE_EXTERNAL_STORAGE permission
                    val intent =
                        Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                            data = Uri.parse("package:${activity.packageName}")
                        }
                    activity.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    activity.startActivity(intent)
                }
            }
        }
    }


    @SuppressLint("ObsoleteSdkInt")
    fun checkAndRequestUnknownSourcesPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canInstallPackages = activity.packageManager.canRequestPackageInstalls()
            if (canInstallPackages) {
                Log.i("Permission", "Unknown sources permission is already granted.")
            } else {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                        data = Uri.parse("package:${activity.packageName}")
                    }
                    activity.startActivityForResult(intent, REQUEST_CODE_UNKNOWN_SOURCES)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("Permission", "Error while requesting unknown sources permission.")
                }
            }
        } else {
            try {
                val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
                activity.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Permission", "Error while opening security settings.")
            }
        }
    }

    fun isexternalObb(packageName: String): Boolean {
        runCatching {
            val obbpath = "${Environment.getExternalStorageDirectory().path}/Android/obb/$packageName"
            LOGS.warn("obb path is: $obbpath")
            val file = File(obbpath)
            val allfiles = file.listFiles()
            allfiles?.forEach { currentFile ->
                if (currentFile.name.endsWith(".obb")) {
                    LOGS.info("Found OBB file: ${currentFile.name}")
                    return true
                }
            }
        }.onFailure {
            return false
        }
        return false
    }
    fun isinternalObb(packageName: String): Boolean {
        runCatching {
            val metastoragepath = MetaStorageManager.obtainAppExternalStorageDir(packageName)
            val obpath = "$metastoragepath/Android/obb/$packageName"
            val getfiles = File(obpath)
            val allfiles : Array<out File>? = getfiles.listFiles()
            allfiles?.forEach { currentFile ->
                if (currentFile.name.endsWith(".obb")) {
                    LOGS.info("Found OBB file: ${currentFile.name}")
                    return true
                }
            }

        }.onFailure {
            return false
        }
        return false
    }

    suspend fun CopyObb(
        packageName: String,
        copySuccess: () -> Unit,
        onFailure: (Throwable?) -> Unit,
        copyProgress: (Int) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val metaStoragePath = MetaStorageManager.obtainAppExternalStorageDir(packageName)
                LOGS.info("MetaStorage path: $metaStoragePath")
                val obbDestinationPath = "$metaStoragePath/Android/obb/$packageName"
                val obbSourcePath = "${Environment.getExternalStorageDirectory().path}/Android/obb/$packageName"

                val sourceObbDir = File(obbSourcePath)
                if (!sourceObbDir.exists() || !sourceObbDir.isDirectory) {
                    onFailure.invoke(IOException("Source OBB directory does not exist"))
                    return@withContext
                }
                val sourceObbFiles = sourceObbDir.listFiles { _, name -> name.endsWith(".obb") } ?: emptyArray()
                if (sourceObbFiles.isEmpty()) {
                    onFailure.invoke(IOException("No OBB files found in the source directory"))
                    return@withContext
                }

                val destinationDir = File(obbDestinationPath)
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs()
                }

                sourceObbFiles.forEach { sourceObbFile ->
                    val destinationFile = File(destinationDir, sourceObbFile.name)

                    FileInputStream(sourceObbFile).use { inputStream ->
                        FileOutputStream(destinationFile).use { outputStream ->
                            val buffer = ByteArray(8192)
                            var bytesRead: Int
                            var totalBytesCopied: Long = 0
                            val totalFileSize = sourceObbFile.length()

                            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                                outputStream.write(buffer, 0, bytesRead)
                                totalBytesCopied += bytesRead

                                val progress = ((totalBytesCopied * 100) / totalFileSize).toInt()
                                copyProgress.invoke(progress)
                            }

                            outputStream.flush()
                        }
                    }
                }

                copySuccess.invoke()
            } catch (exception: Exception) {
                exception.printStackTrace()
                onFailure.invoke(exception)
            }
        }
    }

    fun Launchapp(packageName: String) {
        runCatching {
            MetaActivityManager.launchApp(packageName)
            LOGS.info("App launched: $packageName")
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun isAppInstalled(packageName: String): Boolean {
        return try {
            MetaPackageManager.isInnerAppInstalled(packageName)
//            MetaPackageManager.isAppInstalledAsInternal(packageName)
        } catch (e: Exception) {
            false
        }
    }


    fun installpackage(packageName: String, sucess: () -> Unit, fail: (Throwable?) -> Unit) {
        runCatching {
            val status = MetaApplicationInstaller.cloneApp(packageName)
            if (status == 0) {
                fail.invoke(null)
            } else if (status == 1) {
                sucess.invoke()
            }
        }.onFailure {
            fail.invoke(it)
        }
    }

    fun ispackage_installed_on_system(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}