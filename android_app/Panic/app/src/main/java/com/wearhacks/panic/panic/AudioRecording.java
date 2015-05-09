package com.wearhacks.panic.panic;

import android.media.MediaRecorder;

import java.io.IOException;

public class AudioRecording {

    private MediaRecorder mRecorder;

    public AudioRecording() {
        mRecorder = null;
    }

    public void onRecord(boolean start) {
        if ( start )
            startRecording();
        else
            stopRecording();
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void stopRecording() {
//        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        System.out.println("Success!");
    }

}
