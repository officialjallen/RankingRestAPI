package com.jallen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jallen.model.CMAppException;
import com.jallen.model.CityResponse;
import com.jallen.model.MetricContents;
import com.jallen.model.RankRequest;
import com.jallen.model.RankResponse;

public class CityMetricUtils {
	public static void buildCityResponse(int cityId, CityResponse response) throws IOException, CMAppException {
		boolean cityFound = false;
		try {
			/* This would be replaced with a database call making the database do the work of finding
			the object with the matching id */
			List<CityResponse> cityData = getCitiesList();
			for(CityResponse dbResponse : cityData) {
				if(cityId == Integer.parseInt(dbResponse.getCityId())) {
					copyDbToResponse(response, dbResponse);
					cityFound = true;
				}
			}
			if (!cityFound) {
				throw new CMAppException("No city with id: " + cityId);
			}	
		} catch(IOException | CMAppException e) {
			throw e;
		}
		return;
	}
	
	public static void buildRankResponse(RankRequest request, List<RankResponse> response) throws IOException {
		try {
			List<CityResponse> cityData = getCitiesList();
			for(int i = 0; i < cityData.size(); i++) {
				response.add(i, new RankResponse());
				copyDbToResponse(response.get(i), cityData.get(i));
				calculateOverallScore(request, response.get(i));
			}
			Collections.sort(response, new Comparator<RankResponse>() {
	            @Override
	            public int compare(RankResponse o1, RankResponse o2) {
	                return -Float.compare(o1.getCalculatedScore(), o2.getCalculatedScore());
	            }
	        });
		} catch(IOException e) {
			throw e;
		}
		return;
	}
	
	public static List<CityResponse> getCitiesList() throws IOException {		
		ObjectMapper mapper = new ObjectMapper();
		InputStream databaseStream = CityMetricUtils.class.getResourceAsStream("/cities.json");
		if(databaseStream == null) {
			throw new IOException("Error Reading Database");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(databaseStream));
		List<CityResponse> cityData = Arrays.asList(mapper.readValue(reader, CityResponse[].class));
		return cityData;
	}
	
	public static void copyDbToResponse (CityResponse response, CityResponse dbResponse) {
		response.setMetricContents(new MetricContents());
		response.setCityId(dbResponse.getCityId());
		response.setCityName(dbResponse.getCityName());
		response.setMetricContents(dbResponse.getMetricContents());
		return;
	}
	
	public static void calculateOverallScore(RankRequest weights, RankResponse response) {
		response.setCalculatedScore((float) (Math.round(((weights.getMetricContents().getGreenSpace() * response.getMetricContents().getGreenSpace()) +
				(weights.getMetricContents().getJobGrowth() * response.getMetricContents().getJobGrowth()) +
				(weights.getMetricContents().getWalkability() * response.getMetricContents().getWalkability()) +
				(weights.getMetricContents().getTaxes() * response.getMetricContents().getTaxes())) * 100.0) / 100.0));
	}
}
