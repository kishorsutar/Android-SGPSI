//
// Created by Kishor Sutar on 2/8/17.
//

#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_ks_ap_sgpsi_SGPSIReaderActivity_getApiKey(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env, "quZMY0KZdI0oaw82bAFrfNGnk7GU1Ywp");
}


