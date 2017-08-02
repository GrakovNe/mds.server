package org.grakovne.mds.server.importer.fantlab.api;

import org.grakovne.mds.server.importer.fantlab.dto.FantLabStoryDto;
import org.grakovne.mds.server.importer.fantlab.dto.search.SearchResult;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Url;

public interface ApiService {
    @GET("search-works.json")
    Call<SearchResult> findStories(@Query("q") String searchPhrase);

    @GET
    Call<FantLabStoryDto> findStoryMetaData(@Url String url);
}
