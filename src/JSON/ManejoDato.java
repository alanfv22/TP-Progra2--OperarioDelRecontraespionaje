package JSON;

import java.util.ArrayList;
import java.util.HashMap;
import grafos.Comunicacion;


public class ManejoDato {
	
	EspiasJSON EspiasJSON;
	
	private HashMap<String,Integer> espias;
	private ArrayList<Comunicacion> comunicaciones;

	public ManejoDato() {	
		EspiasJSON = new EspiasJSON();	
		espias = new HashMap<String,Integer>();
		comunicaciones = new  ArrayList<Comunicacion>();
	}
	
	public void agregarEspia(String espia,Integer pos) {
		espias.put(espia,pos);
		
	}
	
	public void agregarComunicacion(Comunicacion comunicacion) {
		comunicaciones.add(comunicacion);
	}
	
	public void actualizarDatos() {
		int pos=0;
	
		for (String espia : espias.keySet()) {
			EspiasJSON.agregarEspia(espia,pos++);
		}
		for(Comunicacion comunicacion : comunicaciones) {
			EspiasJSON.agregarComunicacion(comunicacion);
		}
		String json = EspiasJSON.GeneraJSON_pretty();
		EspiasJSON.guardarJSON(json, "src\\JSON_DATOS\\datos.JSON");	
	}

}

