import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class ScoutCanvas extends JPanel implements MouseMotionListener, MouseInputListener{
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
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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

	public void mouseDragged(MouseEvent e) {
		imlX.addElement(fmlX.get(fmlX.size() - 1));
		imlY.addElement(fmlY.get(fmlY.size() - 1));
		fmlX.addElement(e.getX());
		fmlY.addElement(e.getY());
		repaint();
	}
	
	public void mousePressed(MouseEvent e) {
		imlX.addElement(e.getX());
		imlY.addElement(e.getY());
		fmlX.addElement(e.getX());
		fmlY.addElement(e.getY());
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}	
}
