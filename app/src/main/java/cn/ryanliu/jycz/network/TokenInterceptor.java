package cn.ryanliu.jycz.network;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import cn.ryanliu.jycz.util.LogoutEvent;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp接口请求拦截器，判断token是否过期，若token过期则跳转至登录页面
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        //根据和服务端的约定判断token过期
        if (isTokenExpired(response)) {
            EventBus.getDefault().post(new LogoutEvent(-1, "请重新登录"));
            return response.newBuilder().code(200).build();
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            return true;
        }
        return false;
    }
}