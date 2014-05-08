import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SoketSupport {
	
	 private As _typeConnection;

	 private static Socket _ns;

     private DataOutputStream _writer;
     
     private DataInputStream _reader;

     private ServerSocket _server;
     private Socket _client;
     private Socket _socket;

     private String _ip;
     private int _port;
     
     public void configure(As typeConnection, String ip, String port) throws IOException
     {
         _ip = ip;
         _port = Integer.parseInt(port);

         _typeConnection = typeConnection;

         switch (typeConnection)
         {
             case server:
            	 InetAddress address = InetAddress.getByName(ip);
            	 if(_server == null)
                 _server = new ServerSocket(_port,0,address);
                 
                 break;
                 
             case client:

                 _client = new Socket();
                 break;
         }

     }
     
     public void sendData(String data) throws IOException
     {
         switch (_typeConnection)
         {
             case server:

                 _writer.writeBytes(data);
                 _writer.flush();
                 _socket.close();

                 break;
             case client:

                 _client = new Socket(_ip, _port);
                 _ns = _client;
                 
 				_writer = new DataOutputStream(_ns.getOutputStream());

                 if (_client.isConnected())

                     _writer.writeBytes(data);
                 _writer.flush();

                 break;
         }

     }
     
     public String receiveData() throws IOException
     {
         String data = null;

         switch (_typeConnection)
         {
             case server:
                 _socket = _server.accept();

                 _ns = _socket;

  				_writer = new DataOutputStream(_ns.getOutputStream());
  				_reader = new DataInputStream(_ns.getInputStream());
  				
  				BufferedReader reader = new BufferedReader(new InputStreamReader(_reader));
                 return reader.readLine();

             case client:

   				BufferedReader clientreader = new BufferedReader(new InputStreamReader(_reader));
                 data = clientreader.readLine();
                 _client.close();
                 break;
         }
         return data;
     }
     
     public As getTypeConnection()
     {
         return _typeConnection;
     }
     
     public String[] getLocalIp() throws UnknownHostException, IOException
     {
         String nome = InetAddress.getLocalHost().getHostName();
         
         InetAddress[] ip = Inet4Address.getAllByName(nome);
         String[] ips = new String[ip.length+1];
         ips[0] = "127.0.0.1";
         for (int i = 0; i < ip.length; i++) {
			ips[i+1] = ip[i].getHostAddress();
		}
         return (ips);
     }
     
     public void ForceClose() throws IOException
     {
         switch (_typeConnection)
         {
             case server:

                 _socket.close();
                 break;
             case client:

                 _client.close();
                 break;
         }
     }
     
     
}



