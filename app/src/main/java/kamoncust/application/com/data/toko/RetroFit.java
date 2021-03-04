package kamoncust.application.com.data.toko;

import kamoncust.application.com.libs.CommonUtilities;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFit {

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(CommonUtilities.SERVER_TOKO_URL+"/store/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RestApi getInstanceRetrofit() {
        return getRetrofit().create(RestApi.class);
    }

}