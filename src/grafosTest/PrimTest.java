package grafosTest;

import static org.junit.Assert.*;
import org.junit.Test;

import grafos.BFS;
import grafos.Grafo;
import grafos.Prim;

public class PrimTest {
	
	@Test
	public void tamanioAGMTest() {
		Grafo grafo = grafoPrueba();
		Grafo arbol = Prim.algoritmoPrim(grafo);
		assertEquals (arbol.getAristas().size(), grafo.tamanio() - 1);	
	}

	@Test
	public void grafoVacioAGM() {
		Grafo grafo = new Grafo (0);
		Prim.algoritmoPrim(grafo);		
	}
		
	@Test 
	public void grafo2VerticesAGM() {
		Grafo grafo = new Grafo (2);
		grafo.agregarVerticesNombre("rita", 0);
		grafo.agregarVerticesNombre("rodolfo", 1);
		grafo.agregarComunicacion("rita", "rodolfo", 1);
				
		Grafo arbol = Prim.algoritmoPrim(grafo);
		assertEquals (arbol.getAristas().size(), grafo.tamanio()-1);
	}
	
	@Test
	public void cantVertices() {
		Grafo grafo = grafoPrueba();
		Grafo arbol = Prim.algoritmoPrim(grafo);
		assertEquals (arbol.getCantVertices(),grafo.getCantVertices());
	}
		
	private Grafo grafoPrueba() {
		Grafo g = new Grafo(6);

		String [] s = new String [6];
		
		s[0] = "rodolfo";
		s[1] = "rita";
		s[2] = "holo";
		s[3] = "agus";
		s[4] = "lim";
		s[5] = "rae";
		
		for(int i=0; i<s.length; i++) {
			
			g.agregarVerticesNombre(s[i],i);
			
		}
		g.agregarComunicacion("rita", "agus", 1); 
		g.agregarComunicacion("agus", "rodolfo", 0); 
		g.agregarComunicacion("lim", "holo", 1);
		g.agregarComunicacion("rae", "agus", 1); 
		g.agregarComunicacion("lim", "rae", 1);
		g.agregarComunicacion("rae", "rita", 1);
		g.agregarComunicacion("agus", "holo", 1);
		g.agregarComunicacion("rodolfo", "rae", 1); 
		
		return g;
	}
	
}
