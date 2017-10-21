package com.jallen.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

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
import com.jallen.CMAppException;
import com.jallen.impl.IdValidationService;
import com.jallen.model.CityResponse;
import com.jallen.model.RankRequest;
import com.jallen.model.RankResponse;
import com.jallen.utils.CityMetricUtils;

@Path("/")
public class CityMetricRestService {
	@GET
    @Path("city/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response processCityId(@PathParam("id") String inputCityId) {
		Status status = Response.Status.OK;
		int cityId = Integer.parseInt(inputCityId);
		CityResponse response = new CityResponse();
		
		try {
			IdValidationService.ValidateId(cityId, response);
			response = CityMetricUtils.callDatabase(cityId, response);
		} catch (CMAppException e) {
			status = Response.Status.BAD_REQUEST;
			response.setError(e.getMessage() + inputCityId);
		} catch (IOException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.setError("Internal Server Error: " + e.getMessage());
		}
		return Response.status(status).entity(response).build();
    }
	
	@POST
    @Path("rank")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response process(InputStream entity) {
		Status status = Response.Status.OK;
		ObjectMapper mapper = new ObjectMapper();
		RankRequest request = new RankRequest();
		RankResponse response = new RankResponse();
		try {
			try {
				request = mapper.readValue(entity, RankRequest.class);
			} catch (IOException e) {
				throw new CMAppException("Invalid Json Request");
			}

			//request.setMetricContents(metricWeights);
		} catch (CMAppException e) {
			status = Response.Status.BAD_REQUEST;
			response.setError(e.getMessage());
		//} catch (IOException e) {
	//		status = Response.Status.INTERNAL_SERVER_ERROR;
//			response.setError("Internal Server Error: " + e.getMessage());
		} 
		
  
        return Response.status(status).entity(response).build();
  
    }
}
