package com.sprintweek.marvin.sugarassistant.mobile.botservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BotService {

    @POST("/webhook/rest/webhook")
    Call<List<BotResponse>> askQuestion(@Body BotRequest botRequest);

}
