package clienteServidor;

import java.io.IOException;

public class HiloSalas extends Thread {
	
	private Sala sala;
	
	public HiloSalas(Sala sala) {
		this.sala = sala;
	}
	
	@Override
	public void run() {
		try {
			this.sala.ejecutarChat();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
