package com.android.valetsafe.valetsafedroid.MapManagement;

/**
 * Created by ryan on 2016/7/8.
 */
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class ConvertUtil
{
    // 根据地址获取对应的经纬度
    public static double[] getLocationInfo(String address)
    {
        // 定义一个HttpClient，用于向指定地址发送请求
        HttpClient client = new DefaultHttpClient();
        // 向指定地址发送GET请求
        HttpGet httpGet = new HttpGet("http://maps.google."
                + "com/maps/api/geocode/json?address=" + address
                + "ka&sensor=false");
        StringBuilder sb = new StringBuilder();
        try
        {
            // 获取服务器的响应
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // 获取服务器响应的输入流
            InputStream stream = entity.getContent();
            int b;
            // 循环读取服务器响应
            while ((b = stream.read()) != -1)
            {
                sb.append((char) b);
            }
            // 将服务器返回的字符串转换为JSONObject对象
            JSONObject jsonObject = new JSONObject(sb.toString());
            // 从JSONObject对象中取出代表位置的location属性
            JSONObject location = jsonObject.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location");
            // 获取经度信息
            double longitude = location.getDouble("lng");
            // 获取纬度信息
            double latitude = location.getDouble("lat");
            // 将经度、纬度信息组成double[]数组
            return new double[]{longitude , latitude};
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    // 根据经纬度获取对应的地址
    public static String getAddress(double longitude, double latitude)
    {
        // 定义一个HttpClient，用于向指定地址发送请求
        HttpClient client = new DefaultHttpClient();
        // 向指定地址发送GET请求
        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/"
                + "geocode/json?latlng="
                + latitude + "," + longitude
                + "&sensor=false®ion=cn");
        StringBuilder sb = new StringBuilder();
        try
        {
            // 执行请求
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // 获取服务器响应的字符串
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1)
            {
                sb.append((char) b);
            }
            // 把服务器相应的字符串转换为JSONObject
            JSONObject jsonObj = new JSONObject(sb.toString());
            // 解析出响应结果中的地址数据
            return jsonObj.getJSONArray("results").getJSONObject(0)
                    .getString("formatted_address");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void showAddressFromGeoPoint(Barcode.GeoPoint gPoint) throws IOException {
//        StringBuilder sb=new StringBuilder();
//        Geocoder geoCode=new Geocoder(this, Locale.getDefault());
//        List<Address> addresses=geoCode.getFromLocation(gPoint.getLatitudeE6()/1E6, gPoint.getLongitudeE6()/1E6, 1);
//        if(addresses.size()>0){
//            Address address=addresses.get(0);
//            for(int i=0;i<address.getMaxAddressLineIndex();i++){
//                sb.append(address.getAddressLine(i)+"\n");
//            }
//            sb.append(address.getLocality()+"\n");
//            sb.append(address.getPostalCode()+"\n");
//            sb.append(address.getCountryName()+"\n");
//
//            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
//        }
    }
}
