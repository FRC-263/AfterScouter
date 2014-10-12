import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.jvnet.substance.skin.*;

public class AfterScouterServer extends JFrame implements ActionListener{
	private static final long serialVersionUID = 263L;
	private static final String scouterVersion = "v. 2.1"; 
	private ScoutCanvas canvas;
	private JTextArea scoutConsole;
	private JTextField portSelection;
	
	AfterServerThread recvThread;
	SocketHandler sockHNDL;
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start"))
		{
			PrintConsole("Turning on server at port " + GetPort() + "...");
			recvThread = new AfterServerThread(canvas, this);
			recvThread.start();
		}
		if(e.getActionCommand().equals("Reset"))
		{
			recvThread.stop();
			recvThread.EndRecvThread();
			PrintConsole("Reset server settings...");
		}
	}
	
	public static void main(String[] args) {
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    	    
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
    	    try {
    	    	UIManager.setLookAndFeel(new SubstanceRavenGraphiteLookAndFeel());
	        } catch (Exception e) {}
    	    AfterScouterServer w = new AfterScouterServer();
	        w.setVisible(true);
	      }
	    });
	  }

	public AfterScouterServer()
	{
		super("AfterScouter Server " + scouterVersion);
	    this.setLayout(null);
	    
	    JMenuBar menuBar = new JMenuBar();
	    JMenu fileMenu = new JMenu("Server");
	    JMenuItem netStart = new JMenuItem("Start");
	    JMenuItem netReset = new JMenuItem("Reset");
	    
	    netStart.addActionListener(this);
	    netReset.addActionListener(this);
	    
	    fileMenu.add(netStart);
	    fileMenu.add(netReset);
	    menuBar.add(fileMenu);
	    
	    portSelection = new JTextField("263");
	    menuBar.add(new JLabel("Port:"));
	    menuBar.add(portSelection);
	    
	    canvas = new ScoutCanvas();
	    this.add(canvas);
	    
	    scoutConsole = new JTextArea("AfterScouter Server " + scouterVersion +" Loaded...\n");
	    scoutConsole.setEditable(false);
	    JScrollPane scoutConsoleScroll = new JScrollPane(scoutConsole);
	    scoutConsoleScroll.setBounds(0,500,1294,100);
	    this.add(scoutConsoleScroll);
	    
	    this.setJMenuBar(menuBar);
	    this.setSize(new Dimension(1304,654));
	    this.setLocationRelativeTo(null);
	    
	    sockHNDL = new SocketHandler(recvThread);
	}
	
	public void PrintConsole(String toPrint)
	{
		scoutConsole.append(toPrint + "\n");
	}
	
	public int GetPort()
	{
		return Integer.parseInt(portSelection.getText());
	}
	
	private class SocketHandler extends WindowAdapter {
		AfterServerThread recvThread;
		
		public SocketHandler(AfterServerThread rt)
		{
			recvThread = rt;
		}
		
        public void windowClosing( WindowEvent e ) {
        		recvThread.EndRecvThread();
                System.exit( 0 );
        }
    }
}
