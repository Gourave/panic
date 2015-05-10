package com.wearhacks.panic.panic;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class AudioRecording {

    private MediaRecorder mRecorder;
    private String fileName;
    private Context context;
    private MediaPlayer mPlayer;

    public AudioRecording(Context context) {
        mRecorder = null;
        this.context = context;
    }

    public void onRecord(boolean start) {
        if ( start )
            startRecording();
        else
            stopRecording();
    }

    private void startRecording() {

        mRecorder = new MediaRecorder();

        fileName = "/" + Long.toString(System.currentTimeMillis()) + "3.gp";

        try {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(context.getCacheDir().getPath() + fileName);

            System.out.println(context.getCacheDir().getPath() + fileName);

            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.setMaxDuration(15000);

            System.out.println("setOutputFile");

            mRecorder.prepare();
            System.out.println("prepare() finished");
            mRecorder.start();
            System.out.println("start() finished");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File(context.getCacheDir().getPath() + fileName);
        if(file.exists()) {
            System.out.println("its here!");
        }

    }

    private void stopRecording() {
//        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        System.out.println("Success!");
    }

}
