package com.wearhacks.panic.panic;

import android.media.MediaRecorder;

public class AudioRecording {

    private MediaRecorder mRecorder;

    public AudioRecording() {
        mRecorder = null;
    }

    private void onRecord(boolean start) {
        if (start)
            startRecording();
        else
            stopRecording();
    }

    private void startRecording() {

    }

    private void stopRecording() {
        
    }

}
