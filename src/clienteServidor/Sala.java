package clienteServidor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Sala {

	private Servidor servidor;
	private static int PUERTO = 50000;
	private int puertoSala;
	private String nombre;
	private List<String> usuarios;

	public Sala(String nombre) throws IOException {
		this.puertoSala = PUERTO;
		PUERTO++;
		servidor = new Servidor(puertoSala);
		this.nombre = nombre;
		this.usuarios = new LinkedList<String>();
	}

	public String getNombre() {
		return nombre;
	}

	public int getPuerto() {
		return this.puertoSala;
	}

	public int cantUsuarios() {
		return this.servidor.cantSockets();
	}

	public void ejecutarChat() throws IOException {
		this.servidor.ejecutarChat();
	}

	public void addUsuario(String usuario) {
		this.usuarios.add(usuario);
	}
	
	public List<String> getUsuarios() {
		return this.usuarios;
	}

	@Override
	public String toString() {
		return this.nombre + " | Usuarios: " + cantUsuarios();
	}

}
