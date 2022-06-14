package grafos;

import java.util.ArrayList;

public class Prim {
	private static Grafo agm;
	private static boolean grafoEsConexo;

	public static Grafo algoritmoPrim(Grafo g) {
		try {
			int n = g.getCantVertices();
			agm = new Grafo(g.tamanio());
			grafoEsConexo = true;

			ArrayList<Comunicacion> aristasVertice = new ArrayList<>();
			ArrayList<Integer> numerosEspia = (ArrayList<Integer>) g.dameNumeroEspia().clone();

			for (int i = 0; i < n; i++) {
				if (!agm.existeVertice(numerosEspia.get(i)) && g.vecinos(numerosEspia.get(i)).size() != 0) {
					aristasVertice = (ArrayList<Comunicacion>) g.aristasAsociadas(numerosEspia.get(i)).clone();

					if (aristasVertice != null && aristasVertice.size() != 0) {
						Comunicacion menor = seleccionarMenorArista(aristasVertice, agm); // Arista de menor peso
						if(menor != null) {
							verificarCreacionAgm(g, numerosEspia, menor, i);
						}
					}

				}
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		if (!BFS.esConexo(g)) {
			grafoEsConexo = false;
		}
		return agm;
	}

	public static boolean getEsConexo() {
		return grafoEsConexo;
	}

	public static Grafo getAgm() {
		return agm;
	}

	//Verifica los casos para crear un AGM
		public static void verificarCreacionAgm(Grafo g, ArrayList<Integer> numerosEspia, Comunicacion menor, int vertice) {
			if (!existeAristaEnGrafo(menor, agm) && !agm.existeVertice(menor.getEspia2()) && !BFS.esConexo(agm)) {
				agm.agregarVerticesNombre(g.dameNombreEspia(numerosEspia.get(vertice)), numerosEspia.get(vertice));
				agm.agregarArista(menor.getEspia1(), menor.getEspia2(), menor.getProbabilidad());

			} else if (agm.existeVertice(menor.getEspia2()) && !BFS.esConexo(agm)) {
				agm.agregarVerticesNombre(g.dameNombreEspia(numerosEspia.get(vertice)), numerosEspia.get(vertice));
				agm.agregarArista(menor.getEspia1(), menor.getEspia2(), menor.getProbabilidad());

			} else if (BFS.esConexo(agm) && !agm.existeVertice(menor.getEspia1()) && agm.existeVertice(menor.getEspia2())) {
				agm.agregarVerticesNombre(g.dameNombreEspia(numerosEspia.get(vertice)), numerosEspia.get(vertice));

			} else if (!g.esVerticeInicial(numerosEspia.get(vertice))) {
				agm.agregarVerticesNombre(g.dameNombreEspia(numerosEspia.get(vertice)), numerosEspia.get(vertice));
			}
		}

	// Se fija si la arista ya existe en el grafo pasado por parametro
	private static boolean existeAristaEnGrafo(Comunicacion arista, Grafo grafoAGM) {
		return grafoAGM.existeArista(arista.getEspia1(), arista.getEspia2());
	}

	// retorna true si el array de aristas tiene mas de una arista
	private static boolean existeOtraArista(ArrayList<Comunicacion> aristas) {
		return aristas.size() > 1;
	}

	// Devuelve la primer arista que no exista en el grafo pasado por parametro
	private static Comunicacion aristaInexistenteEnAGM(ArrayList<Comunicacion> aristas, Grafo agm) {
		Comunicacion aristaAGM = null;
		for (Comunicacion arista : aristas) {
			if (existeOtraArista(aristas) && !existeAristaEnGrafo(arista, agm)) {
				aristaAGM = arista;
				break;
			}
		}
		return aristaAGM;
	}

	// Metodo que utiliza elegirArista para seleccionar la arista de menor peso
	public static Comunicacion seleccionarMenorArista(ArrayList<Comunicacion> aristaList, Grafo grafoAGM) {
		Comunicacion auxi = aristaList.get(0);
		for (int i = 0; i < aristaList.size(); i++) {
			if (!existeAristaEnGrafo(auxi, grafoAGM) && !existeAristaEnGrafo(aristaList.get(i), grafoAGM)
					&& aristaList.get(i).getProbabilidad() < auxi.getProbabilidad()) {
				auxi = aristaList.get(i);

			} else if (existeAristaEnGrafo(auxi, grafoAGM) && existeOtraArista(aristaList)) {
				auxi = aristaInexistenteEnAGM(aristaList, grafoAGM);
				if (!existeAristaEnGrafo(aristaList.get(i), grafoAGM)
						&& aristaList.get(i).getProbabilidad() < auxi.getProbabilidad()) {
					auxi = aristaList.get(i);
				}
			}

		}
		return auxi;
	}

	@Override
	public String toString() {
		return "Prim [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public static long tiempoEjecucion(Grafo f) {
		long tiempoInicio, tiempoFin, tiempo;

		tiempoInicio = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			algoritmoPrim(f);
		}
		tiempoFin = System.currentTimeMillis();
		tiempo = tiempoFin - tiempoInicio;
		return tiempo;

	}
	
}
