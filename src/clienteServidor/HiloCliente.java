package clienteServidor;

import java.io.DataInputStream;
import java.io.IOException;

import graficos.JChatCliente;

public class HiloCliente extends Thread {

	private DataInputStream entrada;
	private JChatCliente ventana;

	public HiloCliente(DataInputStream entrada, JChatCliente ventana) {
		this.ventana = ventana;
		this.entrada = entrada;
	}

	public void run() {
		String mensaje;

		while (true) {
			try {
				mensaje = entrada.readUTF();
				ventana.escribirMensaje(mensaje);
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}
}
