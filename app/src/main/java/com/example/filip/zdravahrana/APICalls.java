package com.example.filip.zdravahrana;

import java.util.HashMap;
import java.util.Map;

import info.metadude.java.library.overpass.OverpassService;
import info.metadude.java.library.overpass.models.OverpassResponse;
import info.metadude.java.library.overpass.utils.NodesQuery;
import retrofit2.Call;

/**
 * Created by Filip on 1/3/2019.
 */

public class APICalls {
    private OverpassService overpassService;


    public APICalls(OverpassService overpassService) {
        this.overpassService = overpassService;
    }

    public void callApi()
    {
        Map<String, String> tags = new HashMap<String, String>() {
            {
                put("amenity", "park");
            }
        };
        NodesQuery nodesQuery = new NodesQuery(600, 52.516667, 13.383333, tags, true, 13);
        Call<OverpassResponse> streamsResponseCall = overpassService.getOverpassResponse(
                nodesQuery.getFormattedDataQuery());
    }
}
