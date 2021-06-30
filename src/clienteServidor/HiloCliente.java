package clienteServidor;

import java.io.DataInputStream;
import java.io.IOException;

import graficos.JChatCliente;

public class HiloCliente extends Thread {

	private DataInputStream entrada;
	private JChatCliente ventana;
	private Cliente cliente;

	public HiloCliente(DataInputStream entrada, JChatCliente ventana, Cliente cliente) {
		this.ventana = ventana;
		this.entrada = entrada;
		this.cliente  = cliente;
	}

	public void run() {
		PaqueteDatos datos = new PaqueteDatos();
		byte[] buffer = new byte[250000];

		while (true) {
			try {
				// Datos de entrada
				entrada.read(buffer);
				datos = datos.deserialize(buffer);

				// Escribe el frame si NO es dataServer
				if (!datos.isDataServer())
					ventana.escribirMensaje(datos);

				// Recibo datos del servidor y NO escribe el frame
				else
					cliente.setSalas(datos.getSalas());
				

				System.out.println(datos);
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
