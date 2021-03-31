package com.project.devidea.modules.content.techNews.crawling;

import org.jsoup.nodes.Document;


public abstract class Crawling {
    String blogUrl;
    String category;
    Document doc;

    abstract public void connect();
    abstract public void executeCrawling();


}
