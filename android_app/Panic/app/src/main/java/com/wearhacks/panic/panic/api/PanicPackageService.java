package com.wearhacks.panic.panic.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface PanicPackageService {

    //http://panicapp.herokuapp.com/panicpackages
    @POST("/panicpackages")
    void submitPackage(@Body PanicPackage pkg, Callback<String> cb);

}
