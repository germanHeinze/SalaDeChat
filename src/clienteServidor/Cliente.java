package clienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import graficos.JChatCliente;

public class Cliente {
	
	private String ip;
	private int puerto;
	private Socket socket;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private String nombreUsuario;
	private HashMap<Integer, Sala> salas = new HashMap<Integer, Sala>();
	
	public Cliente(int puerto, String ip, String nombreUsuario) throws UnknownHostException, IOException, ClassNotFoundException {
		this.ip = ip;
		this.puerto = puerto;
		this.socket = new Socket(this.ip, this.puerto);
		this.entrada = new DataInputStream(socket.getInputStream());
		this.salida = new DataOutputStream(socket.getOutputStream());
		this.nombreUsuario = nombreUsuario;

	}
	
	public void enviarMensaje(String mensaje, int idSala, boolean crearSala, boolean dataServer) throws ClassNotFoundException {
		try {
			
			// Le mando al server un paquete de datos con el mensaje, usuario e instrucciones
			PaqueteDatos datos = new PaqueteDatos(this.nombreUsuario, mensaje, idSala, crearSala, dataServer);
			salida.write(datos.serialize(datos));
			
			if (datos.isDataServer()) {
				byte[] buffer = new byte[250000];
				
				// recibo datos del servidor
				PaqueteDatos datosServer = new PaqueteDatos();
				entrada.read(buffer);
				datosServer = datosServer.deserialize(buffer);
				
				this.salas = datosServer.getSalas();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inicializarHiloCliente(JChatCliente ventana) throws IOException {
		new HiloCliente(this.entrada, ventana, this).start();
	}
	
	public void close() throws IOException {
		this.entrada.close();
		this.salida.close();
		this.socket.close();
	}
	
	
	public HashMap<Integer, Sala> getSalas() {
		return salas;
	}
	
	public void setSalas(HashMap<Integer, Sala> salas) {
		this.salas = salas;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
//	public static void main(String[] args) throws UnknownHostException, IOException {
//		
//		
//		
//		
//	}
}
