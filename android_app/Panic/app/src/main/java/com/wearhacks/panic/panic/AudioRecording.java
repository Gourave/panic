package com.wearhacks.panic.panic;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.IOException;

public class AudioRecording {

    private static final int RECORD_DURATION = 15000;
    private static final int RECORD_DURATION_MAX = 30000;

    private MediaRecorder mRecorder;
    private String filePath;
    private String fileName;
    private Context context;
    private File audioFile;
    private OnAudioRecordingCompleteListener listener;

    public AudioRecording(Context context) {
        mRecorder = null;
        this.context = context;
        audioFile = null;
        fileName = null;
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/panicapp";

        //Create directory structure if it doesn't exist
        File file = new File(filePath);
        file.mkdirs();
    }

    public void onRecord(boolean start) {
        if ( start )
            startRecording();
        else
            stopRecording();
    }

    private void startRecording() {

        mRecorder = new MediaRecorder();

        fileName = "/" + Long.toString(System.currentTimeMillis()) + ".3gp";

        try {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(filePath + fileName);
            System.out.println(filePath + fileName);

            mRecorder.setMaxDuration(RECORD_DURATION_MAX);

            System.out.println("setOutputFile");

            mRecorder.prepare();
            System.out.println("prepare() finished");
            mRecorder.start();
            System.out.println("start() finished");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRecording();

                audioFile = new File(filePath + fileName);
                if(audioFile.exists()) {
                    System.out.println("Successfully saved audioFile! Len: " + audioFile.length());
                }

                if (listener != null) listener.recordingComplete();
            }
        }, RECORD_DURATION);
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        System.out.println("Audio recording stopped.");
    }

    public File getAudioFile() {
        return audioFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setOnAudioRecordingCompleteListener (OnAudioRecordingCompleteListener listener) {
        this.listener = listener;
    }

    public interface OnAudioRecordingCompleteListener {
        void recordingComplete();
    }
}
