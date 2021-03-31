package com.project.devidea.modules.content.techNews;

import java.util.ArrayList;
import java.util.List;

public class TechNewsDummy {

    public static List<TechNews> getDaangnTechNews() {
        TechNews techNews1 = TechNews.builder()
                .techSite(TechSite.DAANGN)
                .title("당근 제목1")
                .content("당근 내용1")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews2 = TechNews.builder()
                .techSite(TechSite.DAANGN)
                .title("당근 제목2")
                .content("당근 내용2")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews3 = TechNews.builder()
                .techSite(TechSite.DAANGN)
                .title("당근 제목3")
                .content("당근 내용3")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();

        List<TechNews> techNews = new ArrayList<>();
        techNews.add(techNews1);
        techNews.add(techNews2);
        techNews.add(techNews3);
        return techNews;
    }

    public static List<TechNews> getJavableTechNews() {
        TechNews techNews1 = TechNews.builder()
                .techSite(TechSite.JAVABLE)
                .title("Javable 제목1")
                .content("Javable 내용1")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews2 = TechNews.builder()
                .techSite(TechSite.JAVABLE)
                .title("Javable 제목2")
                .content("Javable 내용2")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews3 = TechNews.builder()
                .techSite(TechSite.JAVABLE)
                .title("Javable 제목3")
                .content("Javable 내용3")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();

        List<TechNews> techNews = new ArrayList<>();
        techNews.add(techNews1);
        techNews.add(techNews2);
        techNews.add(techNews3);
        return techNews;
    }

    public static List<TechNews> getKakaoTechNews() {
        TechNews techNews1 = TechNews.builder()
                .techSite(TechSite.KAKAO)
                .title("카카오 제목1")
                .content("카카오 내용1")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews2 = TechNews.builder()
                .techSite(TechSite.KAKAO)
                .title("카카오 제목2")
                .content("카카오 내용2")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews3 = TechNews.builder()
                .techSite(TechSite.KAKAO)
                .title("카카오 제목3")
                .content("카카오 내용3")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();

        List<TechNews> techNews = new ArrayList<>();
        techNews.add(techNews1);
        techNews.add(techNews2);
        techNews.add(techNews3);
        return techNews;
    }

    public static List<TechNews> getLineTechNews() {
        TechNews techNews1 = TechNews.builder()
                .techSite(TechSite.LINE)
                .title("라인 제목1")
                .content("라인 내용1")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews2 = TechNews.builder()
                .techSite(TechSite.LINE)
                .title("라인 제목2")
                .content("라인 내용2")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();
        TechNews techNews3 = TechNews.builder()
                .techSite(TechSite.LINE)
                .title("라인 제목3")
                .content("라인 내용3")
                .writerName("작성자")
                .writerImgUrl("작성자이미지")
                .url("이미지").build();

        List<TechNews> techNews = new ArrayList<>();
        techNews.add(techNews1);
        techNews.add(techNews2);
        techNews.add(techNews3);
        return techNews;
    }
}
