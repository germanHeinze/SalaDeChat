package clienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	
	private int puerto;
	private ServerSocket server;
	private ArrayList<Socket> sockets;
	
	public Servidor(int puerto) throws IOException {
		this.puerto = puerto;
		this.server = new ServerSocket(this.puerto);
		this.sockets = new ArrayList<Socket>();
		
	}
	
	public void ejecutar() throws IOException {
		Socket socket;
		DataInputStream entrada;
		DataOutputStream salida;
		
		System.out.println("Esperando conexion..");
		socket = server.accept();
		System.out.println("Conexion establecida!!!");
		
		entrada = new DataInputStream(socket.getInputStream());
		salida = new DataOutputStream(socket.getOutputStream());
		
		System.out.println(entrada.readUTF());
		
		entrada.close();
		salida.close();
		socket.close();
		this.server.close();
	}
	
	public void ejecutarChat() throws IOException {
		Socket socket;
		
		while (true) {
//			System.out.println("Esperando conexion..");
			socket = server.accept();
			System.out.println("Conexion establecida!!!");
			
			this.sockets.add(socket);
			new HiloServidor(socket, sockets).start();
		}
	}
	
	public int cantSockets() {
		return this.sockets.size();
	}
	
//	public static void main(String[] args) throws IOException {
//		new Servidor(50000).ejecutarChat();
//	}
}
