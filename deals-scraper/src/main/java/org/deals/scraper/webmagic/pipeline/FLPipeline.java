package org.deals.scraper.webmagic.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class FLPipeline implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
				System.out.println("get page: " + resultItems.getRequest().getUrl());
				// 遍历所有结果，输出到控制台，上面例子中的"author"、"name"、"readme"都是一个key，其结果则是对应的value
				
				String title = resultItems.get("title");
				String img = resultItems.get("img");
				String selllink = resultItems.get("selllink");
				String content = resultItems.get("content");
				String realselllink = resultItems.get("realselllink");
				String price = resultItems.get("price");
				System.out.println(title);
				System.out.println(img);
				System.out.println(selllink);
				System.out.println(realselllink);	
				System.out.println(price);	
				System.out.println(content);	
			//////	for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
//					System.out.println(entry.getKey() + ":\t" + entry.getValue());
//				}
	}

}
