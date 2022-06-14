package JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import grafos.Comunicacion;


public class EspiasJSON {

	private ArrayList<Comunicacion> comunicaciones;
    private HashMap <String,Integer> espias;
 	
	public EspiasJSON(){
		espias = new HashMap <String,Integer> ();
		comunicaciones= new ArrayList <Comunicacion> ();
	}
	
	public void agregarEspia(String nombre, int pos) {
		espias.put(nombre,pos);
	}
	
	public void agregarComunicacion(Comunicacion comunicacion) {
		comunicaciones.add(comunicacion);
	}
	
	public int cantEspias() {
		return espias.size();
	}
	
	public String GeneraJSON_pretty() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public void guardarJSON(String json, String ArchivoDestino) {
		try {
			FileWriter writer = new FileWriter(ArchivoDestino);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

