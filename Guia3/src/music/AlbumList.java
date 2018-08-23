package music;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "albumList")
@XmlSeeAlso({Album.class})
public class AlbumList {
	
private List<Album> alb;
	
	String param;
	Connect cn = new Connect();
	
	AlbumList(){
		alb = new CopyOnWriteArrayList<Album>();
		param = null;
	}
	
	AlbumList(String name){
		alb = new CopyOnWriteArrayList<Album>();
		param = name;
	}
	
	AlbumList(boolean charge){
		if(charge) {
			alb = new CopyOnWriteArrayList<Album>();
			param = null;
		}
	}
	
	@XmlElement
	public List getAlbum() {
		try {
			alb = getAlbumList(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.alb;
	}
	
	public void setAlbum(List<Album> alb) {
		this.alb = alb;
	}
	
	
	public List<Album> getAlbumList(String param) throws Exception{
		String whereQuery="";
		if(param!=null) {
			whereQuery = "where title like '%"+param+"%'";
		}
		Connection conn = cn.conn();
		Statement st = conn.createStatement();
		ResultSet res = st.executeQuery("SELECT * FROM Album " +
				whereQuery);
		while(res.next()){
			Album tmpAlbum = new Album();
			tmpAlbum.setAlbumId(Integer.parseInt(res.getString("AlbumId")));
			tmpAlbum.setArtistId(Integer.parseInt(res.getString("ArtistId")));
			tmpAlbum.setTitle(res.getString("title"));
			alb.add(tmpAlbum);
		}
		return alb;
	}

	public String add(String name, int artistId) throws Exception{
		String id = "-1";
		Connection conn = cn.conn();
		Statement st = conn.createStatement();
		ResultSet res = st.executeQuery("SELECT max(AlbumId)+1 as id FROM Album ");
		res.next();
		id = res.getString(1);
		String sql = " insert into Album (AlbumId,Title, ArtistId) values('"+id+"','" + name +"','"+artistId+"')";
		st.executeUpdate(sql);
		return id;
	}

	public String update(int AlbumId,String name, int artistId) throws Exception{
		String res = "Se actualizo: "+name;
		Connection conn = cn.conn();
		Statement st = conn.createStatement();
		String sqlU = " update Album set Title = '" + name +"', ArtistId='"+artistId+"' where AlbumId="+AlbumId;
		st.executeUpdate(sqlU);
		return res;
	}
	public int add() {
		return 0;
	}

	public int delete(int id) throws Exception {
		int affectedRows = -1;
		String sql = "delete from Album where AlbumId= " + id;
		Connection conn = cn.conn();
		Statement st = conn.createStatement();
		affectedRows = st.executeUpdate(sql);
		return affectedRows;
	}
}
