package com.project.devidea.modules.content.techNews.crawling;

import com.project.devidea.modules.content.techNews.TechNews;
import com.project.devidea.modules.content.techNews.TechNewsRepository;
import com.project.devidea.modules.content.techNews.TechSite;
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
public class LineCrawling extends Crawling{

    public LineCrawling() {
        blogUrl = "https://engineering.linecorp.com/";
        category = "/ko/blog";
        doc = null;
    }

    @Autowired
    private TechNewsRepository techBlogRepository;

    @Override
    public void connect() {
        try {
            doc = Jsoup.connect(blogUrl+category)
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Scheduled( cron="${crawling.Scheduled}")
    public void executeCrawling() {
        connect();
        Elements document = doc.select("main#main");
        Iterator<Element> articles = document.select("article.post").iterator();
        List<TechNews> techBlogs = new ArrayList<>();
        while (articles.hasNext()) {
            Element element = articles.next();
            String url = element.select("h2.entry-title>a").attr("href");
            String title = element.select("h2.entry-title>a").text();
            String imgUrl = "";
            String content = element.select("div.entry-content>p").text();
            String writerImgUrl = element.select("header.entry-header>div.entry-meta>span.blog-author-image>img").attr("src");
            String writerName = element.select("header.entry-header>div.entry-meta>span.posted-by>a>span.author-name").text();
            LocalDate createdDate = LocalDate.parse(element.select("header.entry-header div.entry-meta>span.posted-on>span.published").text().replace(".","-"));
            Set<String> tags = new HashSet<>();

            Iterator<Element> elementTags = element.select("div.entry-content>p.post-tags-list>a").iterator();
            while (elementTags.hasNext()) {
                Element tag = elementTags.next();
                tags.add(tag.text());
            }
            TechNews techBlog = TechNews.builder()
                    .techSite(TechSite.LINE)
                    .url(url)
                    .imgUrl(imgUrl)
                    .title(title)
                    .content(content)
                    .writerName(writerName)
                    .writerImgUrl(writerImgUrl)
                    .createdDate(createdDate)
                    .tags(tags)
                    .build();

            boolean isExist = techBlogRepository.existsByTitleAndContent(title, content);
            if (isExist) break;
            techBlogs.add(techBlog);

        }
        techBlogRepository.saveAll(techBlogs);
    }
}
