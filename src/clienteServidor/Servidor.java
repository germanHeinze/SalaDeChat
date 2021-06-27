package clienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Servidor {
	
	private int puerto;
	private ServerSocket server;
	private HashMap <Integer, Sala> salas;
//	private ArrayList<Socket> sockets;
	
	public Servidor(int puerto) throws IOException {
		this.puerto = puerto;
		this.server = new ServerSocket(this.puerto);
//		this.sockets = new ArrayList<Socket>();
		this.salas = new HashMap<Integer, Sala>();
		
		// Sala 1 y 2
		Sala sala1 = new Sala("Sala 1");
		Sala sala2 = new Sala("Sala 2");
		this.salas.put(sala1.getIdSala(), sala1);
		this.salas.put(sala2.getIdSala(), sala2);
	}
	
	
	public void ejecutarChat() throws IOException {
		Socket socket;
		DataInputStream entrada;
		int salaId;
		String nombreUsuario;
		
		while (true) {
			System.out.println("Esperando conexion..");
			socket = server.accept();
			
			// El cliente me dice en que sala esta y su usuario
			entrada = new DataInputStream(socket.getInputStream());
			salaId = entrada.readInt();
			nombreUsuario = entrada.readUTF();
			
			System.out.println("Conexion establecida!!!" + nombreUsuario + " se unio a sala: " + salaId);
			
			if (this.salas.containsKey(salaId))
				this.salas.get(salaId).addUsuario(nombreUsuario, socket);
			
			// Diferentes hilos por sala
			for (int i = 0; i < this.salas.size(); i++) {
				new HiloServidor(socket, this.salas.get(i).getSockets()).start();
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		new Servidor(50000).ejecutarChat();
	}
}
