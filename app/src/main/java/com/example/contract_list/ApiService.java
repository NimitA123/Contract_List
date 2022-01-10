package com.example.contract_list;

import org.intellij.lang.annotations.JdkConstants;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("android/API/test/get_data")
   public void data( @Body List<List<String>> contract);

}
