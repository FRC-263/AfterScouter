import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;
import org.jvnet.substance.skin.*;

public class AfterScouterClient extends JFrame implements ActionListener{
	private static final long serialVersionUID = 263L;
	private static final String scouterVersion = "v. 2.1"; 
	private  ScoutCanvas canvas;
	
	Socket netSock;
	ObjectOutputStream canvasStream;
	
	JTextField teamOptionsSelection;
	JTextField hostSelection;
	JTextField portSelection;
	JTextArea scoutConsole;
	JSpinner matchNumber;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Connect"))
		{
			 try {
				PrintConsole("Creating socket...");
				netSock = new Socket(hostSelection.getText(), GetPort());
				PrintConsole("Connection request sent to " + hostSelection.getText() + " at port " + GetPort() + "...");
				canvasStream = new ObjectOutputStream(new BufferedOutputStream(netSock.getOutputStream()));
				canvasStream.flush();
				PrintConsole("Connection accepted...");
			} catch (Exception e1) {
				try {
					PrintConsole(e1.getMessage());
					netSock.close();
				} catch (IOException e2) {}
			}
		}
		else if(e.getActionCommand().equals("Send"))
		{
			try
			{
				PrintConsole("Sending current canvas information - Team #:" + teamOptionsSelection.getText() + " Match #: " + matchNumber.getModel().getValue().toString());
				canvasStream.reset();
				canvasStream.writeObject(new CanvasPacket(canvas.imlX, canvas.imlY, canvas.fmlX, canvas.fmlY, teamOptionsSelection.getText(), matchNumber.getModel().getValue().toString()));
				canvasStream.flush();
			} catch (Exception e1) { System.out.println("Error: " + e1.getMessage()); }
		}
		else if(e.getActionCommand().equals("Reset"))
		{
			try
			{
				netSock.close();
				canvasStream.reset();
				canvasStream.close();
				PrintConsole("Reset client settings...");
			} catch(Exception e2) {}
		}
	}
	
	public static void main(String[] args) {
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    	    
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
    	    try {
    	    	UIManager.setLookAndFeel(new SubstanceRavenGraphiteLookAndFeel());
	        } catch (Exception e) {}
    	    AfterScouterClient w = new AfterScouterClient();
	        w.setVisible(true);
	      }
	    });
	  }

	public AfterScouterClient()
	{
		super("AfterScouter Client " + scouterVersion);
	    this.setLayout(null);
	    
	    JMenuBar menuBar = new JMenuBar();
	    JMenu fileMenu = new JMenu("Client");
	    JMenuItem netOpen = new JMenuItem("Connect");
	    JMenuItem netSend = new JMenuItem("Send");
	    JMenuItem netReset = new JMenuItem("Reset");
	    
	    netOpen.addActionListener(this);
	    netSend.addActionListener(this);
	    netReset.addActionListener(this);
	    
	    fileMenu.add(netOpen);
	    fileMenu.add(netSend);
	    fileMenu.add(netReset);
	    menuBar.add(fileMenu);
	   
	    teamOptionsSelection = new JTextField();
	    hostSelection = new JTextField();
	    portSelection = new JTextField("263");
	    
		matchNumber = new JSpinner();
	
		menuBar.add(new JLabel("Host Address:"));
		menuBar.add(hostSelection);
		menuBar.add(new JLabel("Port:"));
		menuBar.add(portSelection);
		menuBar.add(new JLabel("Team Selection:"));
		menuBar.add(teamOptionsSelection);
		menuBar.add(new JLabel("Match #:"));
		menuBar.add(matchNumber);
	    
	    canvas = new ScoutCanvas();
	    this.add(canvas);
	    
	    scoutConsole = new JTextArea("AfterScouter Client " + scouterVersion +" Loaded...\n");
	    scoutConsole.setEditable(false);
	    JScrollPane scoutConsoleScroll = new JScrollPane(scoutConsole);
	    scoutConsoleScroll.setBounds(0,500,1294,100);
	    this.add(scoutConsoleScroll);
	    
	    this.setJMenuBar(menuBar);
	    this.setSize(new Dimension(1304,654));
	    this.setLocationRelativeTo(null);
	}
	
	public void PrintConsole(String toPrint)
	{
		scoutConsole.append(toPrint + "\n");
	}
	
	public int GetPort()
	{
		return Integer.parseInt(portSelection.getText());
	}
}
