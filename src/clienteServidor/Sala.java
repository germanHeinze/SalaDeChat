package clienteServidor;

import java.io.IOException;

public class Sala {
	
	private Servidor servidor;
	private static int PUERTO = 50000;
	private int puertoSala;
	private String nombre;
	
	public Sala(String nombre) throws IOException {
		this.puertoSala = PUERTO;
		PUERTO++;
		
		servidor = new Servidor(puertoSala);
		
		this.nombre = nombre;
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
	
	@Override
	public String toString() {
		return this.nombre + " | Usuarios: " + cantUsuarios();
	}
	
	
	
}
