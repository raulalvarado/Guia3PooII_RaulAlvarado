package music;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class AlbumRS {
	private static AlbumList albumList;

	public AlbumRS() {
	}

	@GET
	@Path("/xml")
	@Produces({ MediaType.APPLICATION_XML })
	public AlbumList getXml() {
		albumList = new AlbumList();
		return albumList;
	}

	@GET
	@Path("/xml/{name}")
	@Produces({ MediaType.APPLICATION_XML })
	public AlbumList getXml(@PathParam("title") String name) {
		albumList = new AlbumList(name);
		return albumList;
	}

	@GET
	@Path("/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public AlbumList getJson() {
		albumList = new AlbumList();
		return albumList;
	}

	@GET
	@Path("/json/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public AlbumList getJson(@PathParam("name") String name) {
		albumList = new AlbumList(name);
		return albumList;
	}

	@POST
	@Path("/create")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response add(@FormParam("title") String name,@FormParam("artistId") int id) {
		albumList = new AlbumList(false);
		String msg;
		if (name == null) {
			msg = "Must to specify Album name";
			return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
		}
		try {
			System.out.println(String.valueOf(id));
			String i = albumList.add(name,id);
			msg = name + " Has been inserted with id " + i;
			return Response.ok(msg, "text/plain").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg = "Could not insert the album";
		return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
	}
	
	@PUT
	@Path("/update")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response update(@FormParam("albumId") int albumId,@FormParam("title") String name,@FormParam("artistId") int ArtistId) {
		albumList = new AlbumList(false);
		String msg;
		if (name == null) {
			msg = "Must to specify Album name";
			return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
		}
		try {
			String i = albumList.update(albumId,name, ArtistId);
			msg = name + " Has been updated with id " + i;
			return Response.ok(msg, "text/plain").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg = "Could not insert the album";
		return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
	}
	
	@DELETE
	@Path("/delete/{AlbumId: \\d+}")
	@Produces({MediaType.TEXT_PLAIN})
	public Response delete(@PathParam("AlbumId") int AlbumId) {
		albumList = new AlbumList(false);
		int affectedRows = -1;
		try {
			affectedRows = albumList.delete(AlbumId);
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
			msg = "AlbumId:"+AlbumId+" Deleted!";
		}
		return Response.ok(msg,"text/plain").build();
	}
}
