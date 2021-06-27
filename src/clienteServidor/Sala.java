package clienteServidor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sala {

	private static int Id = 0;
	private int idSala;
//	private List<Socket> sockets;
	private String nombre;
	private HashMap<String, Socket> usuarios;
	private List<String> mensajes;

	public Sala(String nombre) throws IOException {
		this.idSala = Id;
		Id++;
		this.nombre = nombre;
		this.usuarios = new HashMap<String, Socket>();
	}

	public String getNombre() {
		return nombre;
	}

	public int getIdSala() {
		return idSala;
	}

	public void sacarUsuario(String nombreUsuario) {
		this.usuarios.remove(nombreUsuario);
	}

	public int cantUsuarios() {
		return this.usuarios.size();
	}

	public void addUsuario(String nombreUsuario, Socket socket) {
		this.usuarios.put(nombreUsuario, socket);
	}

	public HashMap<String, Socket> getUsuarios() {
		return usuarios;
	}
	
	public ArrayList<Socket> getSockets() {
		ArrayList<Socket> list = new ArrayList<Socket>(usuarios.values());
		return list;
	}

	@Override
	public String toString() {
		return this.nombre + " | Usuarios: " + cantUsuarios();
	}

}
