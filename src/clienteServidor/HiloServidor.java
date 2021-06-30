package clienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class HiloServidor extends Thread {

	private ArrayList<Socket> sockets;
	private Socket socket;
	private Servidor servidor;

	public HiloServidor(Socket socket, ArrayList<Socket> sockets, Servidor servidor) {
		this.socket = socket;
		this.sockets = sockets;
		this.servidor = servidor;
	}

	public void run() {
		DataInputStream entrada;
		DataOutputStream salida;
		PaqueteDatos datos = new PaqueteDatos();
		byte[] buffer = new byte[250000];

		try {
			entrada = new DataInputStream(this.socket.getInputStream());

			while (true) {
				// El cliente me dice en que sala esta y su usuario
				entrada.read(buffer);
				try {
					datos = datos.deserialize(buffer);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Si es dataServer actualizo el servidor
				if (datos.isDataServer()) {
					// Le envio datos al cliente de las salas disponibles
//					datos.setSalas(servidor.getSalas()); --> esto genera una NotSerializableException:
					System.out.println(datos);
					
					// Agrego a la sala / actualizo el server
					if (servidor.getSalas().containsKey(datos.getidSala()) && datos.getidSala() != -1)
						servidor.getSalas().get(datos.getidSala()).addUsuario(datos.getNombreUsuario(), socket);
				}
				
				// Se hace siempre, aunque sea dataServer
				System.out.println(datos);
				// System.out.println("Sala: " + datos.getSala() + " | " +
				// datos.getNombreUsuario() + ": " + datos.getMensaje());

				for (Socket envio : sockets) {
					salida = new DataOutputStream(envio.getOutputStream());
					salida.write(datos.serialize(datos));
				}

			}

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
