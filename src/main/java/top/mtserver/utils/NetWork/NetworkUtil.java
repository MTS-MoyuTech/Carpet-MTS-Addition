package top.mtserver.utils.NetWork;

import top.mtserver.MTSCarpetServer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class NetworkUtil {
    private static String doGet(String httpUrl) {
        //链接
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String temp;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭远程连接
            assert connection != null;
            connection.disconnect();
        }
        return result.toString();
    }

    private static ArrayList<String> getNames(String uuid) {
        String url = "https://api.mojang.com/user/profiles/%UUID/names".replace("%UUID",uuid);
        // https://api.mojang.com/user/profiles/<UUID>/names
        // 这里返回的东西类似于数组,没办法,硬核解析吧
        // 清空符号(除了:)
        String RawNames = doGet(url).replace("[", "").replace("]", "").replace("{","").replace("}","").replace("\"","");
        String[] Names = RawNames.split(",");
        System.out.println("test start");
        ArrayList<String> FinalNames = new ArrayList<>();
        for (String RawName : Names) {
            // 真硬核
            String[] Rd =  RawName.split(":");
            if (Objects.equals(Rd[0], "changedToAt")){
                continue;
            }
            FinalNames.add(Rd[1]);
        }
        return FinalNames;
    }

    public static String getUUID(String name) {
        // 我草泥马,json数据咋也不能处理,妈蛋
        // 硬核解析开始
        return doGet("https://api.mojang.com/users/profiles/minecraft/%name".replace("%name",name)).replace("{","").replace("}","").replace("\"","").split(",")[1].split(":")[1];
    }

    public static ArrayList<String> getPlayerNames(String Name){
        // 获取玩家名称的api返回的是Json,直接处理
        String UUID = "";
        try{
            UUID = getUUID(Name);
        } catch (Exception e){
            MTSCarpetServer.Log("Can't get player data from Budjump api");
        }
        return getNames(UUID);
    }
}