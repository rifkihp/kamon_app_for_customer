package gomocart.application.com.data;

import gomocart.application.com.libs.CommonUtilities;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFit {

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(CommonUtilities.SERVER_URL+"/store/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RestApi getInstanceRetrofit() {
        return getRetrofit().create(RestApi.class);
    }

}