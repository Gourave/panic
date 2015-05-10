package com.wearhacks.panic.panic.api;

import java.io.File;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface PanicPackageService {

    // http://panicapp.herokuapp.com/panicpackages
    @POST("/panicpackages")
    void submitPackage(@Body PanicPackage pkg, Callback<String> cb);

    // http://panicapp.herokuapp.com/panicpackages/upload
    @Multipart
    @POST("/panicpackages/upload")
    void upload(@Part("filedata") PaniacPackageAudio audio,
                @Part("description") String description,
                Callback<String> cb);

}
