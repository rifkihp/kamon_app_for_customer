package kamoncust.application.com.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import kamoncust.application.com.libs.CommonUtilities;

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