package com.wearhacks.panic.panic;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

public class AudioRecording {

    private MediaRecorder mRecorder;
    private String fileName;
    private Context context;
    private MediaPlayer mPlayer;
    private File audioFile;

    public AudioRecording(Context context) {
        mRecorder = null;
        this.context = context;
        audioFile = null;
        fileName = null;
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

        audioFile = new File(context.getCacheDir().getPath() + fileName);
        if(audioFile.exists()) {
            System.out.println("Successfully saved audioFile!");
        }

    }

    private void stopRecording() {
        // mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        System.out.println("Success!");
    }

    public File getAudioFile() {
        return audioFile;
    }

    public String getFileName() {
        return fileName;
    }

}
