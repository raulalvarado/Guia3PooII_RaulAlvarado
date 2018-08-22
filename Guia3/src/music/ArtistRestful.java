package music;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/Artist")
public class ArtistRestful extends Application {
	
	public Set<Class<?>> getClasses(){
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(ArtistRS.class);
		return set;
		}

}
