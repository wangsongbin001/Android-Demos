package com.wang.mtoolsdemo.common.rxjava1;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.wang.mtoolsdemo.common.bean.ResponseBean;
import com.wang.mtoolsdemo.common.bean.ResultBean;
import com.wang.mtoolsdemo.common.excep.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by dell on 2017/11/21.
 */

public class GsonDataMapFactory extends Converter.Factory{

    public static GsonDataMapFactory create(Gson gson){
        if(gson == null) throw new NullPointerException("gson = null");
        return  new GsonDataMapFactory(gson);
    }

    private final Gson gson;
    private GsonDataMapFactory(Gson gson){
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(type);
        if(rawType != ResultBean.class){
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new DataMapGsonResponseBodyConverter<>(adapter, rawType != ResponseBean.class);//解析出data
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    private static class DataMapGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;
        private final boolean withData;

        DataMapGsonResponseBodyConverter(TypeAdapter<T> adapter, boolean withData) {
            this.adapter = adapter;
            this.withData = withData;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                String json = value.string();
                JSONObject jsonObject = new JSONObject(json);
                String status = jsonObject.getString("status");
                if ("000".equals(status)) {//请求成功
                    if (withData) {
                        json = jsonObject.get("data").toString();
                        if ("null".equalsIgnoreCase(json)) {
                            throw new ApiException("001", "data is null");
                        }
                    }
                    return adapter.fromJson(json);
                } else {
                    throw new ApiException(status, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                throw new IOException(e);
            } finally {
                value.close();
            }
        }
    }

}
