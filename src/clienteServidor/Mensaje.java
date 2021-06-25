package clienteServidor;

public class Mensaje {
	
	private int sala;
	private String mensaje;
	
	public Mensaje(int sala, String mensaje) {
		this.sala = sala;
		this.mensaje = mensaje;
	}
	
	public int getSala() {
		return sala;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
}
