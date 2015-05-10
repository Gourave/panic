package com.wearhacks.panic.panic.api;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;

//Adapted from: http://pedroevaristogs.com/en/upload-file-to-remote-server-in-android/
public class HttpMultipartUploader extends AsyncTask <File, Void, Void> {

    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(File... file) {
        // Your upload Server SCRIPT
        String urlString = "http://panicapp.herokuapp.com/panicpackages/upload";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(urlString);

        MultipartEntity reqEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE);

        // There are more examples above
        FileBody fb = new FileBody(file[0], "audio/3gpp");

        FormBodyPart bodyPart = new FormBodyPart("filedata",fb);
        reqEntity.addPart(bodyPart);
        httppost.setEntity(reqEntity);

        // SERVER RESPONSE
        try {
            HttpResponse response = httpclient.execute(httppost);
            Log.i("HTTP", response.getStatusLine().toString());
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
    }
}