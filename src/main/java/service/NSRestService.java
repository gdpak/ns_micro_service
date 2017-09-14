package service;

import java.net.URI;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import domain.VirtualLink;
import domain.VirtualLinkManager;

@Path("/ns")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class NSRestService {
	@Context
	private UriInfo uriInfo;
	/*
	 * CRUD on VL
	 */
	/*
	 * Create a VL
	 * @param VL 
	 */
	@POST
	@Path("vl")
	public Response create(VirtualLink vl) {
		if (vl == null)
			throw new BadRequestException();
		VirtualLinkManager.add(vl);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(vl.getName())
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * Get a vl
	 * @param id of the vl to retrieve
	 * @return the status and vl
	 */
	@GET
	@Path("/vl/{name}")
	public Response get(@PathParam("name") String name, @QueryParam("type") String type) {
		VirtualLink vl = VirtualLinkManager.find(name);
		if (vl == null)
			throw new NotFoundException();
		return Response
				.ok(vl, "xml".equals(type) ? MediaType.APPLICATION_XML :MediaType.APPLICATION_JSON )
				.build();
	}
	
	/*
	 * Update a vl
	 * @param new VL
	 */
	@PUT
	@Path("vl")
	public Response update(VirtualLink vl) {
		if (vl == null)
			throw new BadRequestException();
		VirtualLinkManager.update(vl);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(String.valueOf(vl.getName()))
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * Delete a vl
	 */
	@DELETE
	@Path("/vl/{name}")
	public Response delete(@PathParam("name") String name, @QueryParam("type") String type) {
		VirtualLink vl = VirtualLinkManager.find(name);
		if (vl == null) {
			throw new NotFoundException();
		}
		VirtualLinkManager.delete(name);
		return Response.noContent().build();
	}
}
