package iti.team.tablia.Services;


import iti.team.tablia.others.MyResponse;
import iti.team.tablia.others.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
  @Headers({
      "Content-Type:application/json",
      "Authorization:Key=AAAAl2Phq54:APA91bEEkG8mh91la0bR0SroQoociyYHDO3lbKhiyFtyWpW8SbS9WwJkGsgqL7yIvvgI-zGB1VwoBaKO4K44UUtFkhsHyFiFqqPmN18rH-y-F3jZj12g-hNybuMlJAVGuHxTY9eU2Mme"
  })
  @POST("fcm/send")
  Call<MyResponse> sendNotification(@Body Sender sender);
}
