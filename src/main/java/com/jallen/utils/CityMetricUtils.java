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

/**
 * Use inputs to build response objects
 * @author Jonathan Allen
 */
public class CityMetricUtils {
	/**
	 * Build the city response by finding the city that matches the input id
	 * Use {@link #getCitiesList()} to read the cities from the database file
	 * Use {@link #copyDbToResponse(CityResponse, CityResponse)} to copy the found city data to the response
	 * @param cityId          The entered city id
	 * @param response        Object to hold response data
	 * @throws IOException    Thrown when there is an error reading the database
	 * @throws CMAppException Thrown when the city id is not found in the database
	 */
	public static void buildCityResponse(int cityId, CityResponse response) throws IOException, CMAppException {
		boolean cityFound = false;
		try {
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
	
	/**
	 * Build the city list response by calculating sorting the overall score
	 * Use {@link #getCitiesList()} to read the cities from the database file
	 * Use {@link #copyDbToResponse(CityResponse, CityResponse)} to copy the found city data to the response
	 * @param request      The request holding the user entered weight data
	 * @param response     The list of cities to return to the user
	 * @throws IOException Thrown when there is an error reading the database
	 */
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
	
	/**
	 * Read the list of cities in from the cities.json file
	 * @return             The list of cities
	 * @throws IOException Thrown when there is an error reading the database
	 */
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
	
	/**
	 * Copy the database response to the response object
	 * @param response   The object to return to the user
	 * @param dbResponse The database response object
	 */
	public static void copyDbToResponse (CityResponse response, CityResponse dbResponse) {
		response.setMetricContents(new MetricContents());
		response.setCityId(dbResponse.getCityId());
		response.setCityName(dbResponse.getCityName());
		response.setMetricContents(dbResponse.getMetricContents());
		return;
	}
	
	/**
	 * Calculate the overall score using the metric data and entered weights
	 * @param weights  The weights entered by the user
	 * @param response The response object to get the metric data from
	 */
	public static void calculateOverallScore(RankRequest weights, RankResponse response) {
		response.setCalculatedScore((float) (Math.round(((weights.getMetricContents().getGreenSpace() * response.getMetricContents().getGreenSpace()) +
				(weights.getMetricContents().getJobGrowth() * response.getMetricContents().getJobGrowth()) +
				(weights.getMetricContents().getWalkability() * response.getMetricContents().getWalkability()) +
				(weights.getMetricContents().getTaxes() * response.getMetricContents().getTaxes())) * 100.0) / 100.0));
	}
}
