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

@XmlRootElement(name = "artistList")
@XmlSeeAlso({Artist.class})
public class ArtistList {

	private List<Artist> arts;
	
	String param;
	Connect cn = new Connect();
	
	ArtistList(){
		arts = new CopyOnWriteArrayList<Artist>();
		param = null;
	}
	
	ArtistList(String name){
		arts = new CopyOnWriteArrayList<Artist>();
		param = name;
	}
	
	ArtistList(boolean charge){
		if(charge) {
			arts = new CopyOnWriteArrayList<Artist>();
			param = null;
		}
	}
	
	@XmlElement
	public List getArtist() {
		try {
			arts = getArtistList(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.arts;
	}
	
	public void setArtist(List<Artist> arts) {
		this.arts = arts;
	}
	
	
	public List<Artist> getArtistList(String param) throws Exception{
		String whereQuery="";
		if(param!=null) {
			whereQuery = "where name like '%"+param+"%'";
		}
		Connection conn = cn.conn();
		Statement st = conn.createStatement();
		ResultSet res = st.executeQuery("SELECT * FROM Artist " +
				whereQuery);
		while(res.next()){
			Artist tmpArtista = new Artist();
			tmpArtista.setArtistId(Integer.parseInt(res.getString("ArtistId")));
			tmpArtista.setName(res.getString("name"));
			arts.add(tmpArtista);
		}
		return arts;
	}

	public String add(String name) throws Exception{
		String id = "-1";
		Connection conn = cn.conn();
		Statement st = conn.createStatement();
		ResultSet res = st.executeQuery("SELECT max(artistId)+1 as id FROM Artist ");
		res.next();
		id = res.getString(1);
		String sql = " insert into artist (ArtistId, Name) values('"+id+"','" + name +
		"')";
		st.executeUpdate(sql);
		return id;
		}

	public int add() {
		return 0;
	}

	public int delete(int id) throws Exception {
		int affectedRows = -1;
		String sql = "delete from Artist where ArtistId= " + id;
		Connection conn = cn.conn();
		Statement st = conn.createStatement();
		affectedRows = st.executeUpdate(sql);
		return affectedRows;
	}
}
