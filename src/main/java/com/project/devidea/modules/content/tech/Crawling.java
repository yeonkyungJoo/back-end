package com.project.devidea.modules.content.tech;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public abstract class Crawling {
    String blogUrl;
    String category;
    Document doc;

    abstract void connect();
    abstract void executeCrawling();


}
