package com.zyd.blog.business.util;

import com.zyd.blog.framework.exception.ZhydCommentException;
import com.zyd.blog.util.RestClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Date;

/**
 * 百度站长推送工具类
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://docs.zhyd.me
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
public class BaiduPushUtil extends RestClientUtil {

    /**
     * 推送链接到百度站长平台
     *
     * @param urlString 百度站长平台地址
     * @param params    待推送的链接
     * @param cookie    百度平台的cookie
     * @return api接口响应内容
     */
    public static String doPush(String urlString, String params, String cookie) {
        if (StringUtils.isEmpty(cookie)) {
            cookie="BAIDUID=0C39DC752FD7FE8D1851E4743FB617A7:FG=1; BIDUPSID=0C39DC752FD7FE8D1851E4743FB617A7; PSTM=1705503200; H_PS_PSSID=40039_40075; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; BA_HECTOR=240084018k0l04818ka18g8gkt0ka21iqn7mf1t; ZFY=904QSfOIxVT7JX1vSecTdbX5h7ei5X43YVDGBMiELkM:C; BAIDUID_BFESS=0C39DC752FD7FE8D1851E4743FB617A7:FG=1; BDRCVFR[C0sZzZJZb70]=mk3SLVN4HKm; PSINO=6; BDRCVFR[dG2JNJb_ajR]=mk3SLVN4HKm; BDRCVFR[-pGxjrCMryR]=mk3SLVN4HKm; BDRCVFR[tox4WRQ4-Km]=mk3SLVN4HKm; userFrom=null; delPer=0; Hm_lvt_6f6d5bc386878a651cb8c9e1b4a3379a=1705776857; SITEMAPZHITONGEXPIRE=1; BDUSS=mVxMDQwOVl5dC1kTXdFMEt4TnFsdTI1YzZ3bjk2UzgzSE1PYm9INkhqNUNwTk5sSUFBQUFBJCQAAAAAAAAAAAEAAAAsYy1d0KHC~LjnMTIxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEIXrGVCF6xld; BDUSS_BFESS=mVxMDQwOVl5dC1kTXdFMEt4TnFsdTI1YzZ3bjk2UzgzSE1PYm9INkhqNUNwTk5sSUFBQUFBJCQAAAAAAAAAAAEAAAAsYy1d0KHC~LjnMTIxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEIXrGVCF6xld; PHPSESSID=s0j47mmuu20oq0htn5hhp6ss65; lastIdentity=PassUserIdentity; __cas__st__=NLI; __cas__id__=0; XFI=90b3d910-b7c5-11ee-802c-7b5b1253110f; Hm_lpvt_6f6d5bc386878a651cb8c9e1b4a3379a=1705776966; ab_sr=1.0.1_YjhiNWY2NWEzMDgzYTIxYTQ1YWFmZmI3YjZkMDNmMWRkNTkxM2JjNmY0OTA5MDUxM2ViOGFjZWQ4MGM2MzFiMWRmOTQxNmQxYTI3ZDA5OTNiNWNhZDI5ZWI4NWQ1NDgxNDEwNTAyNjhmOTBjY2YzOTI1NjBkNzA0YmQ3OGVlZjdhMmQ4NmM2ZWY3YjczYmU0NGQwZGRiYzU5YmI4N2YxNjIzM2NlYWUyZjZmNWE0ZTMyOTI2MzM4N2I3Y2FmMDRm; SITEMAPZHITONG={"data":"41c92b5c9fb57f2bd9b2a4e73b1e692dfe0db6e6db2f5c1522d5ecf594bbdbb1c8cfa15e9e54eb1daf11e0e1a3daba3bc5b588f9bc67c613a06bd086d528843e0d13dca33dc066b1ff988d9a73524ab0599d284f78128800892022272300b28243cfcb5786a7a694fef0c286d34fc9ad19ac106f6c022af26d6147f8eebae9ac6c5a6fcb831c213655043b9cec2506f868d8af02a9bf061c7cf9bcd96017b6183a61ff553fcf210d49ff684f742e1fb14642589879d4774bf10f716332085f27e36757a72bc9a246bbb5284bc3cec13c1a62041efd9972f2ae69d739307bddc06ab5e90c20d06cf997d3e95ed37a10f7c2136f1fb346830ae0ebe972d3873694469e575b5d04b944019ec7e07ab330563b0f711c0870a757de5076fa1bf6df4a1f02b183e68673059dd7265096a6332eb5fe38d25b975d24dbf974aef3ed8437cb5502cb29ef07d60ee6e6960eb016494284163fac6906c356c666c98ddad444c12a3572b1a56eef98ef4f550e2dd5e9","key_id":"32","sign":"89215d4b"}; XFCS=527CCC825F8FB44B34E172008B0BF2B2CDB0D967D56BEE4B1B9992691E2CC676; XFT=I0YreF+pe/sBCKJsbtYnE55DpADPUPjrswr6UMDfPQ8="
            // throw new ZhydCommentException("尚未设置百度站长平台的Cookie信息，该功能不可用！");
        }
        log.info("{} REST url: {}", new Date(), urlString);
        HttpURLConnection connection = null;
        try {
            connection = openConnection(urlString);
            connection.setRequestMethod("POST");
            // (如果不设此项,json数据 ,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Action", "1000");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Cookie", cookie);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(10000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(10000);
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            if (params != null) {
                final OutputStream outputStream = connection.getOutputStream();
                writeOutput(outputStream, params);
                outputStream.close();
            }
            log.info("RestClientUtil response: {} : {}", connection.getResponseCode(), connection.getResponseMessage());
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                return readInput(connection.getInputStream(), "UTF-8");
            } else {
                return readInput(connection.getErrorStream(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("推送到百度站长平台发生异常！", e);
            return "";
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }
}
