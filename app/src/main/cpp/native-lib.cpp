#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_ca_dheri_native_1cpp_1test_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jint JNICALL
Java_ca_dheri_native_1cpp_1test_MainActivity_test2(){
    return -1;
}