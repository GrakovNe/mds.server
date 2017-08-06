package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Service
public class AuthorSearchService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ConfigurationUtils configurationUtils;

    public Page<Author> findAuthor(Map<String, String> params) {
        String name = params.getOrDefault("name", "");
        String orderDirection = params.getOrDefault("orderDirection", "asc");
        String pageNumber = params.getOrDefault("page", "0");

        StringBuilder searchQueryBuilder = new StringBuilder();

        searchQueryBuilder
            .append("from Author where lower(name) like '%")
            .append(name)
            .append("%' ")
            .append("order by name ")
            .append(orderDirection);

        return executeSearchQuery(searchQueryBuilder.toString(), Integer.valueOf(pageNumber), orderDirection);
    }

    private Page<Author> executeSearchQuery(String searchQuery, Integer pageNumber, String orderDirection) {

        List<Author> results = entityManager
            .createQuery(searchQuery, Author.class)
            .setFirstResult(pageNumber * configurationUtils.getPageSize())
            .setMaxResults(configurationUtils.getPageSize())
            .getResultList();

        String countSearchQuery = searchQuery.split("order by")[0];
        countSearchQuery = "select count(*) " + countSearchQuery;

        Long totalResults = entityManager
            .createQuery(countSearchQuery, Long.class)
            .getSingleResult();

        Sort.Direction direction = Sort.Direction.DESC;

        switch (orderDirection) {
            case "desc":
                direction = Sort.Direction.DESC;
                break;
            case "asc":
                direction = Sort.Direction.ASC;
                break;
        }

        return new PageImpl<>(
            results,
            new PageRequest(pageNumber, configurationUtils.getPageSize(), direction, "name"),
            totalResults);
    }
}
