package org.nil1one;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import okhttp3.*;

import java.nio.file.*;
import java.util.Map;
import java.util.Objects;

/**
 *
 **/
public class JDWindowProcessor {
    private final String pid;
    private final OkHttpClient client;
    private final Request cartReq;
    private final Request orderInfoReq;
    private final Request orderPayReq;
    private final Request yuyueInfoReq;
    private final Request serverTimeReq;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JDWindowProcessor(String productID) {
        this.pid = productID;
        // 1-构建请求客户端
        // 说明: 拦截链详见 RealCall#getResponseWithInterceptorChain() 拦截器仿照 BridgeInterceptor
        this.client = new OkHttpClient.Builder().cookieJar(new AutoCookieJar()).addInterceptor((chain) -> {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("User-Agent", "JD4iPhone/10.1.6 CFNetwork/1312 Darwin/21.0.0");
            return chain.proceed(builder.build());
        }).build();
        // 2-预备请求对象
        this.yuyueInfoReq = new Request.Builder().url("https://item-soa.jd.com/getWareBusiness?skuId=" + productID).build();
        this.serverTimeReq = new Request.Builder().url("https://api.m.jd.com/client.action?functionId=queryMaterialProducts&client=wh5").build();
        this.cartReq = new Request.Builder().url("https://cart.jd.com/gate.action?pcount=1&ptype=1&pid=" + productID).build();
        this.orderInfoReq = new Request.Builder().url("https://trade.jd.com/shopping/order/getOrderInfo.action")
                .addHeader("Referer", "https://cart.jd.com/").build();
        this.orderPayReq = new Request.Builder().url("https://trade.jd.com/shopping/order/submitOrder.action?&presaleStockSign=1")
                .addHeader("Referer", "https://trade.jd.com/shopping/order/getOrderInfo.action").build();
    }

    public final void process() {
        login();
        yuyueInfo();
        purchase();
    }

    /**
     * "yuyueInfo": {
     * "bannerUrl": "//img30.360buyimg.com/jdg/jfs/t1/104376/40/20001/3320/63a116e5Effd814e2/7e3cf07f5127e906.png",
     * "btnText": "立即预约",
     * "buyTime": "2023-01-01 20:00-2023-01-01 20:30",
     * "cdPrefix": "预约剩余",
     * "countdown": 234,
     * "hidePrice": 0,
     * "hideText": "",
     * "num": 90990,
     * "plusText": "",
     * "plusType": 0,
     * "sellWhilePresell": "0",
     * "showDraw": false,
     * "showPlusTime": false,
     * "showPromoPrice": 0,
     * "state": 2,
     * "supportOther": 0,
     * "type": "1",
     * "url": "//yushou.jd.com/toYuyue.action?sku=100012043978&key=6873604fdcdd146e64f79612425f9d03",
     * "userType": "NO_LOGIN",
     * "yuyue": true,
     * "yuyueRuleText": [
     * "1.部分商品预约成功后才有抢购资格，预约成功后，请关注抢购时间及时抢购，货源有限，先抢先得！",
     * "2.部分商品在预约期间抢购时间未定，我们会在商品开抢前通过Push通知提醒您，请在设置中选择允许通知，以免错过抢购时间。",
     * "3.对于预约成功享优惠的商品，抢购开始后，点击\"立即抢购\"将商品加入购物车，可在购物车查看优惠，若抢购时间结束，优惠自动失效。",
     * "4.查看预约商品请至\"京东APP\"-\"我的\"-\"我的预约\"进行查看。",
     * "5.如果提供赠品，赠品赠送顺序按照预约商品购买成功时间来计算，而不是预约成功时间。",
     * "6.如您对活动有任何疑问，请联系客服咨询。"
     * ],
     * "yuyueText": "预约享资格",
     * "yuyueTime": "2023-01-01 18:00~2023-01-01 19:59"
     * }
     *
     */


    public void yuyueInfo() {
        try {
            Response yuyueInfoRes = client.newCall(yuyueInfoReq).execute();
            Map map = objectMapper.readValue(Objects.requireNonNull(yuyueInfoRes.body()).byteStream(), Map.class);
            Map yuyueInfo = (Map) map.get("yuyueInfo");
            if (yuyueInfo != null) {
                System.out.println(yuyueInfo.get("buyTime"));
                System.out.println(yuyueInfo.get("yuyueTime"));
                System.out.println(yuyueInfo.get("url"));
            }


            yuyueInfoRes.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void login() {
        String ticket = "";
        try {
            // 1-获取二维码
            Response qrRes = client.newCall(new Request.Builder()
                    .url("https://qr.m.jd.com/show?appid=133&size=147").build()).execute();
            Files.copy(Objects.requireNonNull(qrRes.body()).byteStream(), Paths.get(System.getProperty("user.dir"), "QR.png"), StandardCopyOption.REPLACE_EXISTING);
            Runtime.getRuntime().exec("cmd /c QR.png");
            qrRes.close();

            // 2-等验二维码
            String token = ((AutoCookieJar) client.cookieJar()).findByName("wlfstk_smdl");
            while (true) {
                Response checkQRRes = client.newCall(new Request.Builder()
                        .url("https://qr.m.jd.com/check?appid=133&callback=r&token=" + token)
                        .addHeader("Referer", "https://passport.jd.com/new/login.aspx").build()).execute();
                String result = Objects.requireNonNull(checkQRRes.body()).string();
                if (result.contains("二维码未扫描") || result.contains("请手机客户端确认登录")) {
                    Thread.sleep(5000);
                    continue;
                }
                ticket = result.split("\"ticket\" : \"")[1].split("\"")[0];
                checkQRRes.close();

                // 3-登录二维码
                Response loginQRRes = client.newCall(new Request.Builder()
                        .url("https://passport.jd.com/uc/qrCodeTicketValidation?t=" + ticket).build()).execute();
                if (!Objects.requireNonNull(loginQRRes.body()).string().contains("https://www.jd.com")) throw new RuntimeException("登录失败: " + result);
                loginQRRes.close();
                break;
            }
        } catch (Exception e) {
            System.out.println(ticket);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void purchase() {
        try {
            // 4-加入购物车
            Response addCartRes = client.newCall(cartReq).execute();
            // 5-查订单信息
            Response getOrderRes = client.newCall(orderInfoReq).execute();
            // 6-提交支付单
            Response submitOrderRes = client.newCall(orderPayReq).execute();

            String res = Objects.requireNonNull(submitOrderRes.body()).string();
            if (res.contains("\"success\":false")) {
                System.out.println("下单失败: " + res);
            }
            getOrderRes.close();
            addCartRes.close();
            submitOrderRes.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
