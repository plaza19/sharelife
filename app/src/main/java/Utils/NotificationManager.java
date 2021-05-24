package Utils;

import channel.NotificationHelper;
import modelos.FCMBody;
import modelos.FCMResponse;
import retrofit.IFCMApi;
import retrofit.RetrofitClient;
import retrofit2.Call;

public class NotificationManager {

    private String url = "https://fcm.googleapis.com";

    public NotificationManager() {

    }

    public Call<FCMResponse> sendNotification(FCMBody body) {
        return RetrofitClient.getClient(url).create(IFCMApi.class).send(body);

    }
}
