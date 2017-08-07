package com.zqx.urlparser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangQixiang on 2017/8/8.
 */

public class UrlParser {

    private URL                 mURL;
    private String[]            mArr;
    private Map<String, String> mQueryMap;


    public static UrlParser parse(String url) {
        return new UrlParser(url);
    }

    private UrlParser(String url) {
        mArr = url.split("/");
        try {
            mURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            /*url不合规则*/
        }
    }

    /**
     * 得到路径中某一项'/'之后到后一个'/'之前的值
     */
    public String getAfter(String path) {
        if (!isEmpty(mArr)) {
            for (int i = 0; i < mArr.length; i++) {
                if (mArr[i].equals(path) && i < mArr.length - 1) {
                    return mArr[i + 1];
                }
            }
        }
        return null;
    }

    public String getQuery(String key) {
        if (mQueryMap == null) {
            mQueryMap = getQueryMap();
        }
        if (mQueryMap != null) {
            return mQueryMap.get(key);
        }
        return null;
    }

    public Map<String, String> getQueryMap() {
        HashMap<String, String> map = null;
        if (mURL != null) {
            String query = mURL.getQuery();
            if (!isEmpty(query)) {
                map = new HashMap<>();
                if (query.contains("&")) {
                    String[] params = query.split("&");
                    for (String param : params) {
                        parseIntoMap(param, map);
                    }
                } else {
                    parseIntoMap(query, map);
                }
            }

        }

        return map;
    }


    public String getHost() {
        if (mURL != null) {
            return mURL.getHost();
        } else {
            return null;
        }
    }

    public String getProtocol() {
        if (mURL != null) {
            return mURL.getProtocol();
        } else {
            return null;
        }
    }

    //type=3这种字符串,以"="切割后key,value分别放进数组
    private void parseIntoMap(String param, HashMap<String, String> map) {
        String[] kv = param.split("=");
        if (kv.length > 1) {
            map.put(kv[0], kv[1]);
        } else {
            map.put(kv[0], "");
        }
    }

    private static boolean isEmpty(String string) {
        return string == null || string.trim().equals("");
    }

    private static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public static void main(String[] args) {
        String url = "https://www.oschina.net/translate/list/3?type=2&sort=";
        UrlParser parser = UrlParser.parse(url);
        String protocol = parser.getProtocol();
        String host = parser.getHost();
        String type = parser.getQuery("type");
        String sort = parser.getQuery("sort");
        String translate = parser.getAfter("translate");

        System.out.println(
                "protocol=" + protocol +
                        "\nhost=" + host +
                        "\ntype=" + type +
                        "\nsort=" + sort +
                        "\ntranslate=" + translate
        );

    }


}
