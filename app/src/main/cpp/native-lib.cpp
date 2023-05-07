#include <jni.h>
#include <string>

#include <aaudio/AAudio.h>
#include <android/log.h>


#define AAUDIO_NANOS_PER_MILLISECOND 1000000L

extern "C" JNIEXPORT jstring JNICALL
Java_ca_dheri_native_1cpp_1test_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_ca_dheri_native_1cpp_1test_MainActivity_test2(
        JNIEnv *env, jobject thiz){
    __android_log_print(ANDROID_LOG_DEBUG, "AAudio", "%s", "Java_ca_dheri_native_1cpp_1test_MainActivity_test2 called");

    AAudioStreamBuilder *builder;
    AAudio_createStreamBuilder(&builder);

    AAudioStream *stream;
    AAudioStreamBuilder_setSharingMode(builder, AAUDIO_SHARING_MODE_EXCLUSIVE);
    AAudioStreamBuilder_setDirection(builder, AAUDIO_DIRECTION_OUTPUT);



    AAudioStreamBuilder_openStream(builder, &stream);


    __android_log_print(ANDROID_LOG_DEBUG, "AAStream", "SampleRate : %d", AAudioStream_getSampleRate(stream));
    __android_log_print(ANDROID_LOG_DEBUG, "AAStream", ":Direction : %d", AAudioStream_getDirection(stream));
    __android_log_print(ANDROID_LOG_DEBUG, "AAStream", "SharingMode : %d", AAudioStream_getSharingMode(stream));
    __android_log_print(ANDROID_LOG_DEBUG, "AAStream", "ChannelCount : %d", AAudioStream_getChannelCount(stream));
    __android_log_print(ANDROID_LOG_DEBUG, "AAStream", "Format : %d", AAudioStream_getFormat(stream));
    __android_log_print(ANDROID_LOG_DEBUG, "AAStream", "DeviceId : %d", AAudioStream_getDeviceId(stream));

    // we have a stream by now.

    AAudioStream_requestStart(stream);
    __android_log_print(ANDROID_LOG_DEBUG, "AAStream requestStart: ", "%s", "requested Start");
    __android_log_print(ANDROID_LOG_DEBUG, "AAStream state: ", "%s", AAudio_convertStreamStateToText(AAudioStream_getState(stream)));


    aaudio_stream_state_t inputState = AAUDIO_STREAM_STATE_STARTING;
    aaudio_stream_state_t nextState = AAUDIO_STREAM_STATE_STARTED;
    int64_t timeoutNanos = 300 * AAUDIO_NANOS_PER_MILLISECOND;


    aaudio_result_t result = AAudioStream_waitForStateChange(stream, inputState, &nextState,
                                                             timeoutNanos);
    if (result != AAUDIO_OK) {
        __android_log_print(ANDROID_LOG_DEBUG, "AAStream ","Error starting stream. %s", AAudio_convertResultToText(result));
    }

    __android_log_print(ANDROID_LOG_DEBUG, "AAStream state: ", "%s", AAudio_convertStreamStateToText(AAudioStream_getState(stream)));
    void *audioData;
    int32_t numFrames = 25;
    int64_t timeout = 500;
    aaudio_result_t frameCount  = AAudioStream_read(stream, audioData, numFrames, timeout);
    __android_log_print(ANDROID_LOG_DEBUG, "AAudioStream_read", "frameCount  =%d" , frameCount );

    if (frameCount  < 0) {
        // Error!
    }
    if (frameCount  != numFrames) {
//        // pad the buffer with zeros
//        memset(static_cast<sample_type*>(audioData) + result * samplesPerFrame, 0,
//               sizeof(sample_type) * (numFrames - result) * samplesPerFrame);
        __android_log_print(ANDROID_LOG_DEBUG, "AAudioStream_read", "frameCount  != numFrames");
    }



    // cleanup
    AAudioStreamBuilder_delete(builder);
    AAudioStream_close(stream);
    __android_log_print(ANDROID_LOG_DEBUG, "AAudio", "%s"," Java_ca_dheri_native_1cpp_1test_MainActivity_test2 fin");

    std::string sssss = "bye";
    return env->NewStringUTF(sssss.c_str());
}