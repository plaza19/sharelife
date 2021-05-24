package retrofit;

import modelos.FCMBody;
import modelos.FCMResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAhFeD01E:APA91bE21TGcWWXb66d-kfsqXlavnby8aK_zkCUYTIE5i6WpS-HoD41tE5xzSPSxJ1x1cZcNakJGHC2s-LhqGtI_dJrDgMnjvUGfX98N9xxs9Q8rR3T3Cru8r-JCRQgkX5XN_vWdoIhr"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);
}
