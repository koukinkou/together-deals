package org.deals.scraper.web;

import org.deals.scraper.webmagic.pageprocessor.FanLiWebPageProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import us.codecraft.webmagic.Spider;

@RestController
public class StartUpController {

	@GetMapping("/")
	public String index() {

		Spider.create(new FanLiWebPageProcessor()).addUrl("http://www.meiyatao.com").thread(5).run();
		return "sssss";
	}

}
