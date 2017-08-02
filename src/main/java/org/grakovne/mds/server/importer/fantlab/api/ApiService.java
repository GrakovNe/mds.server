package org.grakovne.mds.server.importer.fantlab.api;

import org.grakovne.mds.server.importer.fantlab.dto.FantLabStoryDto;
import org.grakovne.mds.server.importer.fantlab.dto.search.SearchResult;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Fantlab api requests class.
 */
public interface ApiService {

    /**
     * Returns list of stories by phrase.
     *
     * @param searchPhrase search request
     * @return Search results
     */

    @GET("search-works.json")
    Call<SearchResult> findStories(@Query("q") String searchPhrase);

    /**
     * Returns story meta data by story id.
     *
     * @param url string with story id
     * @return FantLab DTO
     */

    @GET
    Call<FantLabStoryDto> findStoryMetaData(@Url String url);
}
