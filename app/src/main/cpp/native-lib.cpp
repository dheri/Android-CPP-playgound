#include <jni.h>
#include <string>

#include <aaudio/AAudio.h>
#include <android/log.h>


extern "C" JNIEXPORT jstring JNICALL
Java_ca_dheri_native_1cpp_1test_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_ca_dheri_native_1cpp_1test_MainActivity_test2(JNIEnv *env, jobject thiz){
    __android_log_print(ANDROID_LOG_DEBUG, "AAudio", "%s", "Java_ca_dheri_native_1cpp_1test_MainActivity_test2 called");

    AAudioStreamBuilder *builder;
    AAudio_createStreamBuilder(&builder);

    AAudioStream *stream;
    AAudioStreamBuilder_openStream(builder, &stream);


    int32_t  audioStreamSampleRate  = AAudioStream_getSampleRate(stream);
    __android_log_print(ANDROID_LOG_DEBUG, "AAudio audioStreamSampleRate: ", "%d", audioStreamSampleRate);


//    AAudioStreamBuilder_setDeviceId(builder, deviceId);
//    AAudioStreamBuilder_setDirection(builder, direction);
//    AAudioStreamBuilder_setSharingMode(builder, mode);
//    AAudioStreamBuilder_setSampleRate(builder, sampleRate);
//    AAudioStreamBuilder_setChannelCount(builder, channelCount);
//    AAudioStreamBuilder_setFormat(builder, format);
//    AAudioStreamBuilder_setBufferCapacityInFrames(builder, frames);

    AAudioStreamBuilder_delete(builder);
    AAudioStream_close(stream);
    __android_log_print(ANDROID_LOG_DEBUG, "AAudio", "%s"," Java_ca_dheri_native_1cpp_1test_MainActivity_test2 fin");

    std::string sssss = "bye";
    return env->NewStringUTF(sssss.c_str());
}