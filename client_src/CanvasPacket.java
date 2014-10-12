import java.io.Serializable;
import java.util.Vector;

class CanvasPacket implements Serializable
{  
	Vector<Integer> imlX, imlY, fmlX, fmlY;
	String matchNum, team;
	
	private static final long serialVersionUID = 263L;

	public CanvasPacket(Vector<Integer> ix, Vector<Integer> iy, Vector<Integer> fx, Vector<Integer> fy, String t, String mn)
	{
		imlX = ix;
		imlY = iy;
		fmlX = fx;
		fmlY = fy;
		matchNum = mn;
		team = t;
	}
	
	public Vector<Integer> GetIMLX() { return imlX; }
	public Vector<Integer> GetIMLY() { return imlY; }
	public Vector<Integer> GetFMLX() { return fmlX; }
	public Vector<Integer> GetFMLY() { return fmlY; }
	public String GetMatchNum() { return matchNum; }
	public String GetTeam() { return team; }
} 