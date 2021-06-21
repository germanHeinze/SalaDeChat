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
		String mensaje;

		try {
			entrada = new DataInputStream(this.socket.getInputStream());

			while (true) {
				mensaje = entrada.readUTF();
				System.out.println(mensaje);

				for (Socket envio : sockets) {
					salida = new DataOutputStream(envio.getOutputStream());
					salida.writeUTF(mensaje);
				}
			}

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
