package com.alex.mmop

/**
 * @author alex5402
 * Created 9/28/24 at 4:01 PM
 * GameInfo
 */

// for now i am just using this pre defined data for game info you can also use a json data for this

enum class SociaApps(val packagename : String , val  imageid : Int) {
    //Facebook("com.facebook.katana" ,  R.drawable.facebook),
    Twitter("com.twitter.android", R.drawable.twitter)
}

sealed class GameInfo(
    val packagename : String,
    val downloadurl : String,
    val imageid : Int?,
    val zippass : String,
    val isenabled : Boolean
) {

    companion object{
        external fun geturlbgmi() : String
        external fun geturlglobal() : String
        external fun geturlkorea() : String
        external fun passkorea() : String
        external fun passbgmi() : String
        external fun passglobal() : String
    }
/*
    class PubgGlobal : GameInfo(
        versioncode = "0.0.0",
        packagename = "com.tencent.ig",
        gamename = "PUBG Mobile",
        status = "none",
        downloadurl = geturlglobal(),
        zippass = passglobal(),
        imageid = R.drawable.globalpubg,
        isenabled = false
    )*/
    class BgmiIndia : GameInfo(
        packagename = "com.pubg.imobile",
        downloadurl = geturlbgmi(),
        zippass = passbgmi(),
        imageid = R.drawable.bgmi_icon,
        isenabled = true
    )/*
    class PubgKorea : GameInfo(
        versioncode = "0.0.0",
        packagename = "com.pubg.krmobile",
        gamename = "PUBG Mobile",
        status = "none",
        zippass = passkorea(),
        downloadurl = geturlkorea(),
        imageid = R.drawable.icon_foreground,
        isenabled = false
    )*/

}
