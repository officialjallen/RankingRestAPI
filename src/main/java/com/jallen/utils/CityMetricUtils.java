package com.jallen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jallen.model.CMAppException;
import com.jallen.model.CityResponse;
import com.jallen.model.MetricContents;
import com.jallen.model.RankResponse;

public class CityMetricUtils {
	public static CityResponse callDatabase(int cityId, CityResponse response) throws IOException, CMAppException {
		ObjectMapper mapper = new ObjectMapper();
		boolean cityFound = false;
		
		try {
			InputStream databaseStream = CityMetricUtils.class.getResourceAsStream("/cities.json");
			if(databaseStream == null) {
				throw new IOException("Error Reading Database");
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(databaseStream));
			List<CityResponse> cityData = Arrays.asList(mapper.readValue(reader, CityResponse[].class));
			for(CityResponse dbResponse : cityData) {
				if(cityId == Integer.parseInt(dbResponse.getCityId())) {
					response.setMetricContents(new MetricContents());
					response.setCityId(dbResponse.getCityId());
					response.setCityName(dbResponse.getCityName());
					response.setMetricContents(dbResponse.getMetricContents());
					cityFound = true;
				}
			}
			if (!cityFound) {
				throw new CMAppException("No city with id: ");
			}
			databaseStream.close();
		} catch(IOException | CMAppException e) {
			throw e;
		}
		return response;
		
	}
	
	public static RankResponse callDatabase() {
		return null;
		
	}

}
