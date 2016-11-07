package pl.polak.android.architecture.model.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationTokenHeaderInterceptor implements Interceptor {

    private String token;

    public AuthorizationTokenHeaderInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", String.format("token %s", token))
                .build();
        return chain.proceed(request);
    }

}
