package clienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class HiloServidor extends Thread {

	private ArrayList<Socket> sockets;
	private Socket socket;

	public HiloServidor(Socket socket, ArrayList<Socket> sockets) {
		this.socket = socket;
		this.sockets = sockets;
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
				
				System.out.println(datos);
				System.out.println("Sala: " +  datos.getSala() + " | " + datos.getNombreUsuario() + ": " + datos.getMensaje());

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
