package com.loadcell;

import com.loadcell.service.ArticleElasticsearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Resource {
    private final LoadCellService loadCellService;
    private final ArticleElasticsearchService articleElasticsearchService;

    public Resource(LoadCellService loadCellService, ArticleElasticsearchService articleElasticsearchService) {
        this.loadCellService = loadCellService;
        this.articleElasticsearchService = articleElasticsearchService;
    }

    @GetMapping("")
    public ResponseEntity<?> run() {
        loadCellService.loadCell();
        return ResponseEntity.ok("Load cell successfully");
    }

    @GetMapping("/elastic")
    public ResponseEntity<?> runElastic() {
        return ResponseEntity.ok(articleElasticsearchService.findByAuthorsNameUsingCustomQuery());
    }

    @PostMapping("/elastic/save")
    public ResponseEntity<?> runSaveElastic() {
        articleElasticsearchService.saveArticle();
        return ResponseEntity.ok(null);
    }

}
