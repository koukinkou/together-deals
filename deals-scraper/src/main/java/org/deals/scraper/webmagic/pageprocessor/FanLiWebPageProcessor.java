package org.deals.scraper.webmagic.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class FanLiWebPageProcessor implements PageProcessor {

	public static final String URL_LIST = "https://bbs\\.hupu\\.com/bxj-\\d+";
    public static final String URL_POST = "https://bbs\\.hupu\\.com/\\d+\\-\\d+.html";
    public static final String URL_POST_1 = "https://bbs\\.hupu\\.com/\\d+\\.html";
    public static final String URL_USER = "https://my\\.hupu\\.com/\\d+";
    public static final int URL_LENGTH = "https://bbs.hupu.com/bxj-".length();
    
    //抓取网站的相关配置，包括编码、抓取间隔、重试次数、代理、UserAgent等
//    private Site site = Site.me()
//            .addHeader("Proxy-Authorization",ProxyGeneratedUtil.authHeader(ORDER_NUM, SECRET, (int) (new Date().getTime()/1000)))//设置代理
//            .setDisableCookieManagement(true)
//            .setCharset("UTF-8")
//            .setTimeOut(30000)
//            .setRetryTimes(3)
//            .setSleepTime(new Random().nextInt(20)*100)
//            .setUserAgent(UserAgentUtil.getRandomUserAgent());
    
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    
	@Override
	public Site getSite() {
		return site;
	}
	//http://www.meiyatao.com/
	@Override
	public void process(Page page) {
		page.addTargetRequests(page.getHtml().links().regex("(http://www.meiyatao\\.com/\\w+/\\w+)").all());
        //page.putField("author", page.getUrl().regex("http://www.meiyatao\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//div[@class=\"list\"]/div[@class=\"listTitle\"]/h4[@class=\"itemName\"]/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("url", page.getHtml().xpath("/html/body/section/div[1]/div[8]/div[2]/div[3]/div[1]/div/a@href"));
	}

}
