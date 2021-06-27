package clienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import graficos.JChatCliente;

public class Cliente {
	
	private String ip;
	private int puerto;
	private Socket socket;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private int sala;
	private String nombreUsuario;
	
	public Cliente(int puerto, String ip, int sala, String nombreUsuario) throws UnknownHostException, IOException {
		this.ip = ip;
		this.puerto = puerto;
		this.socket = new Socket(this.ip, this.puerto);
		this.entrada = new DataInputStream(socket.getInputStream());
		this.salida = new DataOutputStream(socket.getOutputStream());
		this.sala = sala;
		this.nombreUsuario = nombreUsuario;
	}
	
	public void enviarMensaje(String mensaje) {
		try {
			
			PaqueteDatos datos = new PaqueteDatos(this.sala, this.nombreUsuario, mensaje, 0);
			salida.write(datos.serialize(datos));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inicializarHiloCliente(JChatCliente ventana) throws IOException {
		new HiloCliente(this.entrada, ventana).start();
	}
	
	public void close() throws IOException {
		this.entrada.close();
		this.salida.close();
		this.socket.close();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Cliente cliente = new Cliente(50000, "localhost", 0, "ger1234");
		JChatCliente frame = new JChatCliente();
		frame.setVisible(true);
		frame.asignarCliente(cliente);
		cliente.inicializarHiloCliente(frame);
		
		
		Cliente cliente2 = new Cliente(50000, "localhost", 0, "Veiny24");
		JChatCliente frame2 = new JChatCliente();
		frame2.setVisible(true);
		frame2.asignarCliente(cliente2);
		cliente2.inicializarHiloCliente(frame2);
		
	}
}
