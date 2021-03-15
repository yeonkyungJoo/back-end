package com.project.devidea.modules.content.tech;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class JavableCrawling extends Crawling{

	public JavableCrawling() {
		blogUrl = "https://woowacourse.github.io";
		category = "/javable";
		doc = null;
	}

	@Autowired
	private TechBlogRepository techBlogRepository;

	@Override
    public void connect() {
        try {

			doc = Jsoup.connect(blogUrl+category)
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
//	@Scheduled( cron="${project.crawling.Scheduled}")
    public void executeCrawling() {
		connect();
        Elements document = doc.select("main#site-main>div.css-6ada2o");
		Iterator<Element> articles = document.select("article.post-card").iterator();

		List<TechBlog> techBlogs = new ArrayList<>();
		while (articles.hasNext()) {
			Element element = articles.next();
			String url = blogUrl + element.select(">a").attr("href");
			String imgUrl = blogUrl + element.select(" picture img").attr("src");
			String title = element.select("h2.post-card-title").text();
			String content = element.select("section.post-card-excerpt ").text();
			String writerImgUrl = element.select("div.post-card-content footer picture img").attr("src");
			String writerName = element.select("div.post-card-content footer div.post-card-byline-content>span:not(.post-card-byline-date)>a").text();
			LocalDate createdDate =  LocalDate.parse(element.select(".post-card-byline-date time").attr("datetime"));
			Set<String> tags = new HashSet<>();
			tags.add(element.select("div.post-card-primary-tag").text());

			TechBlog techBlog = TechBlog.builder()
					.url(url)
					.imgUrl(imgUrl)
					.title(title)
					.content(content)
					.createdDate(createdDate)
					.writerImgUrl(writerImgUrl)
					.writerName(writerName)
					.tags(tags)
					.build();

			boolean isExist = techBlogRepository.existsByTitleAndContent(title, content);
			if(isExist) break;
			techBlogs.add(techBlog);
		}
		techBlogRepository.saveAll(techBlogs);
    }
}
