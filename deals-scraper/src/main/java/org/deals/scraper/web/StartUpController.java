package org.deals.scraper.web;

import org.deals.scraper.webmagic.pageprocessor.FanLiWebPageProcessor;
import org.deals.scraper.webmagic.pipeline.FLPipeline;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import us.codecraft.webmagic.Spider;

@RestController
public class StartUpController {

	@GetMapping("/")
	public String index() {

		Spider.create(new FanLiWebPageProcessor()).addUrl("http://www.mgpyh.com/post/?page=1").addPipeline(new FLPipeline()).thread(1).run();
		return "init";
	}

}
