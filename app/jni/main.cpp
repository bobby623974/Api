#include "Includes.h"



extern "C"
JNIEXPORT jobject JNICALL
Java_com_alex_mmop_kuroapi_secrets_Sapi_getbaseurl(JNIEnv *env, jobject thiz) {

//   "https://mprmods.x-keys.xyz/connect"
    std::vector<std::string> headers = {
            OBFUSCATE("ROZyczPZSDNQZFyYFOHQ2lCD7cv1b7JemyyUKCKyMUi0Y+XxzSDbu6SuGbcSXICk"), // key string
            OBFUSCATE("3XzCpmXoszJJE7FNTVLn0w=="), // pass string

//            OBFUSCATE("JoInTEl3graMEx0Di4") // lisence
//            OBFUSCATE("kDC+HUroP62dSeLepXrAg9q6VGhfMS9UCTnc9/ONn4iH6HkLJo+vgIwQmC2NYJxx"), // key string
//            OBFUSCATE("Jpfjzu2nBbNRAQB5qO+48Q=="), // pass string
            OBFUSCATE("Vm8Lk7Uj2JmsjCPVPVjrLa7zgfx3uz9E") // lisence
    };

    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    jobject headerList = env->NewObject(arrayListClass, arrayListConstructor);
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
    for (const auto &header: headers) {
        jstring jheader = env->NewStringUTF(header.c_str());
        env->CallBooleanMethod(headerList, addMethod, jheader);
        env->DeleteLocalRef(jheader);
    }
    return headerList;
}


extern "C" JNIEXPORT jobject JNICALL
Java_com_alex_mmop_kuroapi_secrets_Sapi_getheaders(JNIEnv *env, jobject /* this */) {
    std::vector<std::string> headers = {
            OBFUSCATE("Content-Type"),
            OBFUSCATE("application/x-www-form-urlencoded"),
            OBFUSCATE("Accept"),
            OBFUSCATE("application/json"),
            OBFUSCATE("Charset"),
            OBFUSCATE("UTF-8"),
            OBFUSCATE("User-Agent"),
            OBFUSCATE("public-loder"),
            OBFUSCATE("PUBG"),
            OBFUSCATE("user_key"),
            OBFUSCATE("serial"),
    };
    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    jobject headerList = env->NewObject(arrayListClass, arrayListConstructor);
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
    for (const auto &header: headers) {
        jstring jheader = env->NewStringUTF(header.c_str());
        env->CallBooleanMethod(headerList, addMethod, jheader);
        env->DeleteLocalRef(jheader);
    }

    return headerList;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alex_mmop_GameInfo_00024Companion_geturlbgmi(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBFUSCATE("https://github.com/bheemrajpoot/Nagin/raw/refs/heads/main/libpubgm.zip"));
                                     // https://github.com/jasmintyr6756/onlinelib/releases/download/bgmi/bgmi.zip
//    return env->NewStringUTF(OBFUSCATE("https://biestore.co/MAMACX/bgmi.zip"));
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alex_mmop_GameInfo_00024Companion_geturlglobal(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBFUSCATE("https://biestore.co/MAMACX/global.zip"));
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_alex_mmop_GameInfo_00024Companion_geturlkorea(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBFUSCATE("https://biestore.co/MAMACX/korea.zip"));
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_alex_mmop_GameInfo_00024Companion_passbgmi(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBFUSCATE("nagin")); //2021-02-18
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alex_mmop_GameInfo_00024Companion_passglobal(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBFUSCATE("M180218")); //2021-02-18
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_alex_mmop_GameInfo_00024Companion_passkorea(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBFUSCATE("M180218")); //2021-02-18
}
