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
	private HashMap<Integer, Sala> salas;
	private ArrayList<Socket> sockets;

	public Servidor(int puerto) throws IOException {
		this.puerto = puerto;
		this.server = new ServerSocket(this.puerto);
		this.sockets = new ArrayList<Socket>();
		this.salas = new HashMap<Integer, Sala>();

		// Sala 1 y 2
		Sala sala1 = new Sala("Sala 1");
		Sala sala2 = new Sala("Sala 2");
		this.salas.put(sala1.getIdSala(), sala1);
		this.salas.put(sala2.getIdSala(), sala2);
	}

	public void ejecutarChat() throws IOException, ClassNotFoundException {
		Socket socket;
		DataInputStream entrada;
		DataOutputStream salida;
		PaqueteDatos datos = new PaqueteDatos();
		byte[] buffer = new byte[250000];

		while (true) {
			System.out.println("Esperando conexion..");
			socket = server.accept();
			

			// El cliente me dice en que sala esta y su usuario
			entrada = new DataInputStream(socket.getInputStream());

			entrada.read(buffer);
			datos = datos.deserialize(buffer);

			System.out.println("Conexion establecida!!!" + datos.getNombreUsuario() + " se unio a sala: "
					+ datos.getSala());

			// Le envio datos al cliente de las salas disponibles
			salida = new DataOutputStream(socket.getOutputStream());
			datos.setSalas(this.salas);
			System.out.println(datos);
			salida.write(datos.serialize(datos));
			
			// Agrego a la sala
			if (this.salas.containsKey(datos.getSala()) && datos.getSala() != -1)
				this.salas.get(datos.getSala()).addUsuario(datos.getNombreUsuario(), socket);

			// Diferentes hilos por sala
//			for (int i = 0; i < this.salas.size(); i++) {
//				new HiloServidor(socket, this.salas.get(i).getSockets()).start();
//			}
			
			this.sockets.add(socket);
			new HiloServidor(socket, this.sockets, this).start();
		}
	}
	
	public HashMap<Integer, Sala> getSalas() {
		return salas;
	}
	
	public void setSalas(HashMap<Integer, Sala> salas) {
		this.salas = salas;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		new Servidor(50000).ejecutarChat();
	}
}
