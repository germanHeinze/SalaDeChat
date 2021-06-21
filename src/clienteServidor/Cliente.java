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
	
	public Cliente(int puerto, String ip) throws UnknownHostException, IOException {
		this.ip = ip;
		this.puerto = puerto;
		this.socket = new Socket(this.ip, this.puerto);
		this.entrada = new DataInputStream(socket.getInputStream());
		this.salida = new DataOutputStream(socket.getOutputStream());
	}
	
	public void ejecutar() throws IOException {
		
		System.out.println(entrada.readUTF());
		salida.writeUTF("Primer server!!");
		
		entrada.close();
		salida.close();
		this.socket.close();
	}
	
	public void enviarMensaje(String mensaje) {
		try {
			salida.writeUTF(mensaje);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inicializarHiloCliente(JChatCliente ventana) {
		new HiloCliente(this.entrada, ventana).start();
	}
	
	public void close() throws IOException {
		this.entrada.close();
		this.salida.close();
		this.socket.close();
	}
	
//	public static void main(String[] args) throws UnknownHostException, IOException {
//		new Cliente(5000, "localhost").ejecutar();
//	}
}
