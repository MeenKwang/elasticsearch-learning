package com.loadcell.service;

import com.loadcell.model.Article;
import com.loadcell.model.Author;
import com.loadcell.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleElasticsearchService {
    private final ArticleRepository articleRepository;

    private final Author johnSmith = new Author("John Smith");
    private final Author johnDoe = new Author("John Doe");

    public ArticleElasticsearchService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<Article> findByAuthorsNameUsingCustomQuery() {
        Pageable pageable = PageRequest.of(0, 10);
        return articleRepository.findByAuthorsNameUsingCustomQuery("John", pageable);
    }

    public void saveArticle() {
        Article article = new Article("Spring Data Elasticsearch");
        article.setAuthors(Arrays.asList(johnSmith, johnDoe));
        article.setTags("elasticsearch", "spring data");
        articleRepository.save(article);

        article = new Article("Search engines");
        article.setAuthors(List.of(johnDoe));
        article.setTags("search engines", "tutorial");
        articleRepository.save(article);

        article = new Article("Second Article About Elasticsearch");
        article.setAuthors(List.of(johnSmith));
        article.setTags("elasticsearch", "spring data");
        articleRepository.save(article);

        article = new Article("Elasticsearch Tutorial");
        article.setAuthors(List.of(johnDoe));
        article.setTags("elasticsearch");
        articleRepository.save(article);
    }
}
