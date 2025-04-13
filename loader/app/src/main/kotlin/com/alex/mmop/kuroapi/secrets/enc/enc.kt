package com.alex.mmop.kuroapi.secrets.enc

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * @author alex5402
 * Created 9/20/24 at 9:04 PM
 * enc
 */
object enc {
    @SuppressLint("GetInstance")
    @TargetApi(Build.VERSION_CODES.O)
   suspend fun encryptString(input: String): Pair<String?, String?> {
     try {
         val pair: Pair<String?, String?> = withContext(Dispatchers.IO) {
             val keyGenerator = KeyGenerator.getInstance("AES")
             keyGenerator.init(128)
             val secretKey: SecretKey = keyGenerator.generateKey()
             val cipher = Cipher.getInstance("AES")
             cipher.init(Cipher.ENCRYPT_MODE, secretKey)
             val encryptedBytes: ByteArray = cipher.doFinal(input.toByteArray())
             val encryptedString: String = Base64.getEncoder().encodeToString(encryptedBytes)
             val keyString: String = Base64.getEncoder().encodeToString(secretKey.encoded)
             Pair(encryptedString, keyString)
         }
         return pair
     }catch (err :Exception) {
         err.printStackTrace()
         return Pair(null, null)
     }
    }

    @SuppressLint("GetInstance")
    @TargetApi(Build.VERSION_CODES.O)
   suspend fun decryptString(keystring: String, pass: String): String? {
      try {
          val decryptedBytes = withContext(Dispatchers.IO) {
              val decodedKey: ByteArray = Base64.getDecoder().decode(pass)
              val secretKey: SecretKey = SecretKeySpec(decodedKey, "AES")
              val cipher = Cipher.getInstance("AES")
              cipher.init(Cipher.DECRYPT_MODE, secretKey)
              val encryptedBytes: ByteArray = Base64.getDecoder().decode(keystring)
              val decryptedBytes: ByteArray = cipher.doFinal(encryptedBytes)
              val decryptedString: String = String(decryptedBytes)
              decryptedString
          }
         return decryptedBytes
      }catch (err :Exception) {
         err.printStackTrace()
         return null
      }
    }
}