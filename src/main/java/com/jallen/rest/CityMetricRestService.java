package com.jallen.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jallen.impl.IdValidationService;
import com.jallen.impl.WeightValidationService;
import com.jallen.model.CMAppException;
import com.jallen.model.CityResponse;
import com.jallen.model.RankRequest;
import com.jallen.model.RankResponse;
import com.jallen.utils.CityMetricUtils;

/**
 * Restful City Rank Service
 * @author Jonathan Allen
 */
@Path("/")
public class CityMetricRestService {
	/**
	 * Process the City ID GET Request
	 * Use {@link com.jallen.impl.IdValidationService#validateId(String)} to validate input weights.
	 * Use {@link com.jallen.utils.CityMetricUtils#buildCityResponse(int, CityResponse)} to find the city.
	 * @param inputCityId The entered city code
	 * @return            Returns the response status and object
	 */
	@GET
    @Path("city/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response processCityId(@PathParam("id") String inputCityId) {
		Status status = Response.Status.OK;
		int cityId = 0;
		CityResponse response = new CityResponse();
		try {
			cityId = IdValidationService.validateId(inputCityId);
			CityMetricUtils.buildCityResponse(cityId, response);
		} catch (CMAppException e) {
			status = Response.Status.BAD_REQUEST;
			response.setError(e.getMessage());
		} catch (IOException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.setError("Internal Server Error: " + e.getMessage());
		}
		return Response.status(status).entity(response).build();
    }
	
	/**
	 * Process the City Rank POST Request
	 * Use {@link com.jallen.impl.WeightValidationService#validateWeights(RankRequest)} to validate input weights.
	 * Use {@link com.jallen.utils.CityMetricUtils#buildRankResponse(RankRequest, List&lt;RankResponse&gt;)} to build the city list.
	 * @param input The input stream of the Json Request
	 * @return      Return the response status and object
	 */
	@POST
    @Path("rank")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response process(InputStream input) {
		Status status = Response.Status.OK;
		RankRequest request = new RankRequest();
		List<RankResponse> response = new ArrayList<RankResponse>();
		try {
			try {
				ObjectMapper mapper = new ObjectMapper();
				request = mapper.readValue(input, RankRequest.class);
			} catch (IOException e) {
				throw new CMAppException("Invalid Json Request");
			}
			WeightValidationService.validateWeights(request);
			CityMetricUtils.buildRankResponse(request, response);
		} catch (CMAppException e) {
			status = Response.Status.BAD_REQUEST;
			response.get(0).setError(e.getMessage());
		} catch (IOException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.get(0).setError("Internal Server Error: " + e.getMessage());
		} 
        return Response.status(status).entity(response).build();
  
    }
}
