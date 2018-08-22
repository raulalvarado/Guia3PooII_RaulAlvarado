package music;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class ArtistRS {

	private static ArtistList artistList;

	public ArtistRS() {
	}

	@GET
	@Path("/xml")
	@Produces({ MediaType.APPLICATION_XML })
	public ArtistList getXml() {
		artistList = new ArtistList();
		return artistList;
	}

	@GET
	@Path("/xml/{name}")
	@Produces({ MediaType.APPLICATION_XML })
	public ArtistList getXml(@PathParam("name") String name) {
		artistList = new ArtistList(name);
		return artistList;
	}

	@GET
	@Path("/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public ArtistList getJson() {
		artistList = new ArtistList();
		return artistList;
	}

	@GET
	@Path("/json/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ArtistList getJson(@PathParam("name") String name) {
		artistList = new ArtistList(name);
		return artistList;
	}

	@POST
	@Path("/create")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response Update(@FormParam("name") String name) {
		artistList = new ArtistList(false);
		String msg;
		if (name == null) {
			msg = "Must to specify Artist name";
			return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
		}
		try {
			String id = artistList.add(name);
			msg = name + " Has been inserted with id " + id;
			return Response.ok(msg, "text/plain").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg = "Could not insert the artist";
		return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
	}
	
	@DELETE
	@Path("/delete/{ArtistId: \\d+}")
	@Produces({MediaType.TEXT_PLAIN})
	public Response delete(@PathParam("ArtistId") int ArtistId) {
		artistList = new ArtistList(false);
		int affectedRows = -1;
		try {
			affectedRows = artistList.delete(ArtistId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		String msg;
		if(affectedRows == 0) {
			msg = "ArtistId not found";
			return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
		}else if(affectedRows == -1) {
			msg = "Error on delete";
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).type(MediaType.TEXT_PLAIN).build();
		}else {
			msg = "ArtistId:"+ArtistId+" Deleted!";
		}
		return Response.ok(msg,"text/plain").build();
	}
	
}
