package com.sprintweek.marvin.sugarassistant.mobile.botservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BotService {

    // todo: check the URL path
    @POST("/askmarvin")
    Call<List<BotResponse>> askQuestion(@Body BotRequest botRequest);

}
