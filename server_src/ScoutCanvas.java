import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ScoutCanvas extends JPanel{
	private static final long serialVersionUID = 263L;
	public Vector<Integer> imlX, imlY, fmlX, fmlY;

	public ScoutCanvas()
	{
		imlX = new Vector<Integer>();
		imlY = new Vector<Integer>();
		fmlX = new Vector<Integer>();
		fmlY = new Vector<Integer>();
		
		this.setBounds(0, 0, 1300, 498);
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(1300, 498));
	}
	
	public void updateCanvasVectors(CanvasPacket cp)
	{
		imlX = cp.GetIMLX();
		imlY = cp.GetIMLY();
		fmlX = cp.GetFMLX();
		fmlY = cp.GetFMLY();
		
		repaint();
		
	    JFrame win = (JFrame)SwingUtilities.getWindowAncestor(this);
	    Dimension size = win.getSize();
	    BufferedImage image = (BufferedImage)win.createImage(size.width, size.height);
	    Graphics g = image.getGraphics();
	    win.paint(g);
	    g.dispose();
	    try      {
	      ImageIO.write(image, "jpg", new File("match_" + cp.GetTeam() + "_" + cp.GetMatchNum()+".jpg"));
	    }
	    catch (Exception e){}
	}
	
    public void paintComponent( Graphics g )
    {
    	Graphics2D g2 = (Graphics2D) g;
    	g.drawImage(new ImageIcon("field_small.jpg").getImage(),0,0,null);

		g2.setColor(Color.CYAN);
		g2.setStroke(new BasicStroke(5));
		
		for(int i = 0; i < fmlX.size(); i++)
		{
			g.drawLine(imlX.get(i), imlY.get(i), fmlX.get(i), fmlY.get(i));
		}
    	
    	super.paintComponent( g );
    }
}
