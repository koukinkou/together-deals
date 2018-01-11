package org.deals.scraper.webmagic.pageprocessor;

import java.net.HttpURLConnection;
import java.net.URL;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class FanLiWebPageProcessor implements PageProcessor {

	public static final String URL_LIST = "https://bbs\\.hupu\\.com/bxj-\\d+";
	public static final String URL_POST = "https://bbs\\.hupu\\.com/\\d+\\-\\d+.html";
	public static final String URL_POST_1 = "https://bbs\\.hupu\\.com/\\d+\\.html";
	public static final String URL_USER = "https://my\\.hupu\\.com/\\d+";
	public static final int URL_LENGTH = "https://bbs.hupu.com/bxj-".length();

	// 抓取网站的相关配置，包括编码、抓取间隔、重试次数、代理、UserAgent等
	// private Site site = Site.me()
	// .addHeader("Proxy-Authorization",ProxyGeneratedUtil.authHeader(ORDER_NUM,
	// SECRET, (int) (new Date().getTime()/1000)))//设置代理
	// .setDisableCookieManagement(true)
	// .setCharset("UTF-8")
	// .setTimeOut(30000)
	// .setRetryTimes(3)
	// .setSleepTime(new Random().nextInt(20)*100)
	// .setUserAgent(UserAgentUtil.getRandomUserAgent());

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
			.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").setCharset("UTF-8");;

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		if (page.getUrl().toString().contains("recommend")) {
			// 如果是详细也，获取内容
			// title /html/body/div[1]/div[2]/div/div[1]/h1/text()
			page.putField("title", page.getHtml().xpath("/html/body/div/div[2]/div/div/div[1]/div/div[1]/span/text()").toString());

			/// catagory html/body/div[1]/div[2]/div/div[1]/div[1]/a[1]
			page.putField("price",page.getHtml().xpath("/html/body/div/div[2]/div/div/div[1]/div/div[1]/span/em/text()").toString());

			// img /html/body/div[1]/div[2]/div/div[1]/div[2]/div/div/img			
			page.putField("img",page.getHtml().xpath("/html/body/div/div[2]/div/div/div[1]/img/@src").toString());

			// selllink /html/body/div[1]/div[2]/div/div[1]/div[2]/div/div/a
			String selllink = page.getHtml().xpath("/html/body/div/div[2]/div/div/div[1]/div/div[3]/a/@href")
					.toString();
			page.putField("selllink", selllink);

			page.putField("realselllink", getRealselllink(selllink));
			// page.addTargetRequests(page.getHtml().xpath("/html/body/div[1]/div[2]/div/div[1]/div[2]/div/div").links().regex("(http://www\\.mgpyh\\.com/goods/\\w+/)").all());

			// content /html/body/div[1]/div[2]/div/div[1]/div[2]/div
			page.putField("content", page.getHtml().xpath("/html/body/div/div[2]/div/div/div[2]/div[2]/div").toString());
		} else if (page.getUrl().toString().contains("post")) {
			// 如果是列表页，则只取链接
			page.addTargetRequests(page.getHtml().xpath("/html/body/div[1]/div[2]/div/div[1]/div[2]").links()
					.regex("(http://www\\.mgpyh\\.com/recommend/\\d+/)").all());
			// 还可以继续读取别的翻页后的链接

		}
	}

	public String getRealselllink(String url) {
		String location = "";
		try {
			// System.out.println("访问地址:" + url);
			URL serverUrl = new URL("http://www.mgpyh.com"+url);
			HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setRequestMethod("GET");
			// 必须设置false，否则会自动redirect到Location的地址
			conn.setInstanceFollowRedirects(false);

			conn.addRequestProperty("Accept-Charset", "UTF-8;");
			conn.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
			conn.addRequestProperty("Referer", "http://zuidaima.com/");
			conn.connect();
			location = conn.getHeaderField("Location");

			//System.out.println("跳转地址:" + location);
			// return location;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;

	}

}
