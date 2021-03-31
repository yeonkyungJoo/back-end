package com.project.devidea.modules.content.techNews.crawling;

import com.project.devidea.modules.content.techNews.TechNews;
import com.project.devidea.modules.content.techNews.TechNewsRepository;
import com.project.devidea.modules.content.techNews.TechSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class DaangnCrawling extends Crawling{

    public DaangnCrawling() {
        blogUrl = "https://medium.com";
        category = "/daangn";
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
 //   @Scheduled( cron="${crawling.Scheduled}")
    public void executeCrawling() {
        connect();

        Elements document = doc.select("div.u-clearfix.u-maxWidth1032.u-marginAuto");
        Iterator<Element> articles = document.select("div.u-size8of12.u-xs-size12of12>div").iterator();

        List<TechNews> techBlogs = new ArrayList<>();
        while (articles.hasNext()) {
            Element element = articles.next();
            String url = element.select(">a[data-post-id]").attr("href");
            String imgUrl = element.select(">au-block.u-width140.u-height120.u-floatRight.u-marginLeft30.u-xs-size80x80.u-backgroundCover.u-borderLighter").attr("href");
            String title = element.select(">a>h3>div.u-fontSize24.u-xs-fontSize18").text();
            String content = element.select(">a div.u-fontSize18.u-xs-fontSize16").text();
            String writerImgUrl = element.select("div.postMetaInline-avatar.u-flex0>a").attr("href");
            String writerName = element.select("div.postMetaInline.postMetaInline-authorLockup.ui-captionStrong.u-flex1.u-noWrapWithEllipsis>a").text();
            LocalDate createdDate = LocalDate.parse(element.select("div.postMetaInline.postMetaInline-authorLockup.ui-captionStrong.u-flex1.u-noWrapWithEllipsis>div>time").attr("datetime").substring(0,10));

            TechNews techBlog = TechNews.builder()
                    .techSite(TechSite.DAANGN)
                    .url(url)
                    .imgUrl(imgUrl)
                    .title(title)
                    .content(content)
                    .writerName(writerName)
                    .writerImgUrl(writerImgUrl)
                    .createdDate(createdDate)
                    .build();

            boolean isExist = techBlogRepository.existsByTitleAndContent(title, content);
            if (isExist) break;
            techBlogs.add(techBlog);

        }
        techBlogRepository.saveAll(techBlogs);
    }
}
