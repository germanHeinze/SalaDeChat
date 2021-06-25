package clienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Servidor {
	
	private int puerto;
	private ServerSocket server;
	private ArrayList<Socket>[] salas;
	private ArrayList<Socket> sockets;
	
	public Servidor(int puerto) throws IOException {
		this.puerto = puerto;
		this.server = new ServerSocket(this.puerto);
		this.sockets = new ArrayList<Socket>();
		
		// Sala 1 y 2
		this.salas[0] = new ArrayList<Socket>();
		this.salas[1] = new ArrayList<Socket>();
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
		DataInputStream entrada;
		int sala;
		
		while (true) {
//			System.out.println("Esperando conexion..");
			socket = server.accept();
			entrada = new DataInputStream(socket.getInputStream());
			sala = entrada.read();
			System.out.println("Conexion establecida!!!" + " en sala: " + sala);
			
			this.sockets.add(socket);
			this.salas[sala].add(socket);
			new HiloServidor(socket, salas[sala]).start();
		}
	}
	
	public int cantSockets() {
		return this.sockets.size();
	}
	
//	public static void main(String[] args) throws IOException {
//		new Servidor(50000).ejecutarChat();
//	}
}
