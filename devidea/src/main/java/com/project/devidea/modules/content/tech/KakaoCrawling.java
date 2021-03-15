package com.project.devidea.modules.content.tech;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class KakaoCrawling extends Crawling{

    public KakaoCrawling() {
        blogUrl = "https://kakao.github.io";
        category = "";
        doc = null;
    }

    @Autowired
    private TechBlogRepository techBlogRepository;

    @Override
    void connect() {
        try {
            doc = Jsoup.connect(blogUrl+category)
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Scheduled( cron="${project.crawling.Scheduled}")
    void executeCrawling() {
        connect();
        Elements document = doc.select("ul#post-list");
		Iterator<Element> articles = document.select("li.post-item").iterator();

        List<TechBlog> techBlogs = new ArrayList<>();
		while (articles.hasNext()) {
			Element element = articles.next();
			String url = blogUrl + element.select(">a").attr("href");
			String imgUrl ="";
			String title = element.select(">a>h3.post-title").text();
			String content = element.select(">a>p.post-excerpt").text();
			String writerImgUrlText = element.select("div.author-image").attr("style").split("url")[1];
			writerImgUrlText = writerImgUrlText.substring(1, writerImgUrlText.length()-1);
            String writerImgUrl = blogUrl + writerImgUrlText;
            String writerName = element.select("p.author-name").text();
            LocalDate createdDate = LocalDate.parse(element.select(">div.post-meta p.post-date").text().split(" ")[0]);
            Set<String> tags = new HashSet<>();

			Iterator<Element> elementTags = element.select(">p.post-tags>a").iterator();
			while(elementTags.hasNext()) {
				Element tag = elementTags.next();
                tags.add(tag.text());
			}
			TechBlog techBlog = TechBlog.builder()
                    .url(url)
                    .imgUrl(imgUrl)
                    .title(title)
                    .content(content)
                    .writerImgUrl(writerImgUrl)
                    .writerName(writerName)
                    .createdDate(createdDate)
                    .tags(tags)
                    .build();

            boolean isExist = techBlogRepository.existsByTitleAndContent(title, content);
            if(isExist) break;
            techBlogs.add(techBlog);
		}
        techBlogRepository.saveAll(techBlogs);

    }
}
