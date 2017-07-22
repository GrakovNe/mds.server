package org.grakovne.mds.server.importer.retrofit;

import org.grakovne.mds.server.importer.dto.FantLabStoryDto;
import org.grakovne.mds.server.importer.dto.search.AudioMetaData;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Url;

public interface FantLabService {
    @GET("search-works.json")
    Call<AudioMetaData> findStories(@Query("q") String searchPhrase);

    @GET
    Call<FantLabStoryDto> findStoryMetaData(@Url String url);
}
