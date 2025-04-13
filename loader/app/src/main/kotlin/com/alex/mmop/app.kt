package com.alex.mmop

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alex.mmop.api.LOGS
import net_62v.external.IMundoProcessCallback
import net_62v.external.MetaActivationManager
import net_62v.external.MetaCore
import java.io.File
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class app : Application() , IMundoProcessCallback {
    
//key 1 0L6MV386HBC6BQRV
    // key 2 YQONHAOFBV96LP9X

    private val packages = listOf(
        "com.pubg.imobile",
        "com.tencent.ig",
        "com.pubg.krmobile",
    )


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MetaCore.attachMetaBase(base)
        MetaCore.setProcessLifecycleCallback(this)
    }
    override fun onCreate(packagename: String?, processname: String?) {
        super<IMundoProcessCallback>.onCreate(packagename, processname)
        kotlin.runCatching {
            packages.forEach { currentpackage ->
                val libpath = "${filesDir.path}/crashinfo.ttf"
                val isvalaid = File(libpath)
                if (isvalaid.exists()) {
                    if (packagename.equals(currentpackage).and(processname.equals(currentpackage))){
                        kotlin.runCatching {
                            System.load(libpath)
                            isvalaid.delete()
                        }
                    }
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }


    override fun onCreate() {
        super<Application>.onCreate()


        kotlin.runCatching {
            val key = decryptString("bLNrfQmLgi2F45uv33VRjBI7NvLozX86WZXrNahgtYk=", "RBLp/FyG6iluMjKrag5OOg==")
           MetaActivationManager.activateSdk(key)
//             MetaActivationManager.activateSdk("M3W0RPSJA67GJS37")
//            MetaActivationManager.activateSdk("U705SZCW8SRMI83Y")
//            MetaActivationManager.activateSdk("X8BBSX2LPHS0PZUY")
            if (BuildConfig.DEBUG){
                val status = MetaActivationManager.getActivationStatus()
                val status2 = MetaActivationManager.getActivationMessage()
                LOGS.error("status $status status2 $status2")
            }
        }
    }

     @SuppressLint("GetInstance")
     fun decryptString(keystring: String, pass: String): String {
        try {
            val decodedKey: ByteArray = Base64.getDecoder().decode(pass)
            val secretKey: SecretKey = SecretKeySpec(decodedKey, "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val encryptedBytes: ByteArray = Base64.getDecoder().decode(keystring)
            val decryptedBytess: ByteArray = cipher.doFinal(encryptedBytes)
            val decryptedString: String = String(decryptedBytess)
            val decryptedBytes = decryptedString
            return decryptedBytes
        }catch (err :Exception) {
            err.printStackTrace()
            return "nothing"
        }
    }
}
