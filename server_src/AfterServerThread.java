import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


class AfterServerThread extends Thread {
	//Thread recvThread;
	
	ObjectInputStream canvasStream;
	ServerSocket servSock;
	Socket sock;
	 
	CanvasPacket currCanvasPacket;
	AfterScouterServer server;
	ScoutCanvas canvas;
     
	public AfterServerThread(ScoutCanvas sc, AfterScouterServer s) {
		super("recvThread");
		canvas = sc;
		server = s;
	}
	
	public void run() {
		try {
			server.PrintConsole("Creating socket...");
			servSock = new ServerSocket(server.GetPort());
			server.PrintConsole("Accepting Connections...");
			sock = servSock.accept();
			server.PrintConsole("Connection recieved from: " + sock.getLocalAddress());
			canvasStream = new ObjectInputStream( new
		               BufferedInputStream(sock.getInputStream()));
		} catch (Exception e) {};
		while(true)
		{
			try {
				currCanvasPacket = (CanvasPacket)canvasStream.readObject();
				canvas.updateCanvasVectors(currCanvasPacket);
				server.PrintConsole("Recieved a packet - Team #: " + currCanvasPacket.GetTeam() + " Match #: " + currCanvasPacket.GetMatchNum());
			} catch (Exception e) {}
		}
	}
	
	public void EndRecvThread()
	{
		try
		{
			canvasStream.close();
			sock.close();
			servSock.close();
		}
		catch(Exception e) {}
	}
}