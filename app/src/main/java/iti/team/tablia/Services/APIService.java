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
      "Authorization:Key=AAAABpfozlQ:APA91bElJQrw8D-kWw-R0-U711VK5xNxEUhCNIAZZydfOesLTTkDRijR03kMF5rFyNISbHow14fzccPTZvhxQBi3UroFZWJa4l9AgWhd1hkXmvrEQ6Xa18-H_J3bjQmximeGTQHSZe-u"
  })
  @POST("fcm/send")
  Call<MyResponse> sendNotification(@Body Sender sender);
}
