package com.wearhacks.panic.panic;

import android.media.MediaRecorder;
import android.os.Debug;
import android.util.Log;

import java.io.IOException;

public class AudioRecording {

    private MediaRecorder mRecorder;

    public AudioRecording() {
        mRecorder = null;
    }

    private MediaRecorder onRecord(boolean start) {
        if (start) {
            return startRecording();
        }
        else
            return stopRecording();
    }

    private MediaRecorder startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        mRecorder.start();

        return null;
    }

    private MediaRecorder stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        return mRecorder;
    }

}
