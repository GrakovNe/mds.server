package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.grakovne.mds.server.utils.ValidationUtils;
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
public class SearchService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ConfigurationUtils configurationUtils;

    public Page<Story> findStory(Map<String, String> params) {
        ValidationUtils.validate(params);

        normalizeOrderBy(params);

        String listenedType = params.getOrDefault("listenedType", "both");
        String title = params.getOrDefault("title", "");
        String author = params.getOrDefault("author", "");
        String orderBy = params.getOrDefault("orderBy", "title");
        String orderDirection = params.getOrDefault("orderDirection", "desc");
        Integer userId = Integer.valueOf(params.getOrDefault("userId", "0"));
        Integer pageNumber = Integer.valueOf(params.getOrDefault("page", "0"));

        StringBuilder searchQueryBuilder = new StringBuilder();

        searchQueryBuilder
            .append("select s from Story s ")
            .append("join s.authors a where lower(a.name) like '%")
            .append(author)
            .append("%' ");

        switch (listenedType) {
            case "listened":
                searchQueryBuilder
                    .append("and s.id in ")
                    .append(getListenedIds(userId));
                break;

            case "unlistened":
                searchQueryBuilder
                    .append("and s.id not in ")
                    .append(getListenedIds(userId));
                break;
        }

        searchQueryBuilder
            .append("and lower(title) like '%")
            .append(title)
            .append("%' ");

        searchQueryBuilder
            .append("order by s.")
            .append(orderBy)
            .append(" ")
            .append(orderDirection);

        return executeSearchQuery(searchQueryBuilder.toString(), pageNumber, orderDirection, orderBy);
    }

    public Page<Story> executeSearchQuery(String searchQuery, Integer pageNumber, String orderDirection, String orderBy) {
        List<Story> results = entityManager
            .createQuery(searchQuery, Story.class)
            .setFirstResult(pageNumber * configurationUtils.getPageSize())
            .setMaxResults(configurationUtils.getPageSize())
            .getResultList();

        String countSearchQuery = searchQuery;

        countSearchQuery = countSearchQuery
            .split("select s ")[1]
            .split("order by")[0];

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
            new PageRequest(pageNumber, configurationUtils.getPageSize(), direction, orderBy),
            totalResults);
    }

    private String getListenedIds(Integer userId) {
        return "(select story.id from ListenedStory ls where ls.user.id = " + userId + ") ";
    }

    private void normalizeOrderBy(Map<String, String> params) {
        String orderBy = params.getOrDefault("orderBy", "id");
        orderBy = orderBy.replaceAll("rating", "rating.value");
        params.replace("orderBy", orderBy);
    }

}
