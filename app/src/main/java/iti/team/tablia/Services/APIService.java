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
      "Authorization:Key=AAAAkyKFWIY:APA91bHIEA_FCTvp4RxISMIrq1PiLYLvmj9NxBOJkcf5OZiIMDezIEp9aliajLDubFL2w0IFMyo7HVRHFaPloOhQBLKBJY4jHsI7LTgP32oYSS2HdY87L0OFbmTW338bdR_hNNX11goB"
  })
  @POST("fcm/send")
  Call<MyResponse> sendNotification(@Body Sender sender);
}
