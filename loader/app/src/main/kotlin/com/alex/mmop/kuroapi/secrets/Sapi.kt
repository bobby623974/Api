package com.alex.mmop.kuroapi.secrets

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import java.io.FileInputStream
import java.security.MessageDigest

/**
 * @author alex5402
 * Created 9/20/24 at 9:58 AM
 * Sapi
 */
object Sapi {
    external fun getbaseurl(): List<String>
    external fun getheaders() : List<String>

    fun getApkSha256Hash(packageName: String, packageManager: PackageManager): String? {
        return try {
            val packageInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
            val apkFilePath = packageInfo.applicationInfo.sourceDir
            val digest = MessageDigest.getInstance("SHA-256")
            FileInputStream(apkFilePath).use { fis ->
                val buffer = ByteArray(1024)
                var bytesRead: Int

                while (fis.read(buffer).also { bytesRead = it } != -1) {
                    digest.update(buffer, 0, bytesRead)
                }
            }
            val hashBytes = digest.digest()
            hashBytes.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}