package com.project.devidea.modules.content.techNews;

import com.project.devidea.modules.content.techNews.crawling.DaangnCrawling;
import com.project.devidea.modules.content.techNews.crawling.JavableCrawling;
import com.project.devidea.modules.content.techNews.crawling.KakaoCrawling;
import com.project.devidea.modules.content.techNews.crawling.LineCrawling;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/techNews")
public class TechNewsController {

    private final TechNewsRepository techNewsRepository;

    private final DaangnCrawling daangnCrawling;
    private final KakaoCrawling kakaoCrawling;
    private final JavableCrawling javableCrawling;
    private final LineCrawling lineCrawling;

    @GetMapping("")
    @ApiOperation("전체 테크 뉴스 조회")
    public List<TechNews> getAllTechNews() {
        return techNewsRepository.findAll();
    }

    @GetMapping("/daangn")
    @ApiOperation("당근마켓 테크 뉴스 조회")
    public List<TechNews> getDaangnTechNews() {
        return techNewsRepository.findAllByTechSite(TechSite.DAANGN);
    }

    @GetMapping("/javable")
    @ApiOperation("Javable 테크 뉴스 조회")
    public List<TechNews> getJavableTechNews() {
        return techNewsRepository.findAllByTechSite(TechSite.JAVABLE);
    }

    @GetMapping("/kakao")
    @ApiOperation("카카오 테크 뉴스 조회")
    public List<TechNews> getKakaoTechNews() {
        return techNewsRepository.findAllByTechSite(TechSite.KAKAO);
    }

    @GetMapping("/line")
    @ApiOperation("라인 테크 뉴스 조회")
    public List<TechNews> getLineTechNews() {
        return techNewsRepository.findAllByTechSite(TechSite.LINE);
    }

    @GetMapping("/testData")
    public void getTestData() {
        daangnCrawling.connect();
        daangnCrawling.executeCrawling();
        kakaoCrawling.connect();
        kakaoCrawling.executeCrawling();
        lineCrawling.connect();
        lineCrawling.executeCrawling();
        javableCrawling.connect();
        javableCrawling.executeCrawling();
    }
}
