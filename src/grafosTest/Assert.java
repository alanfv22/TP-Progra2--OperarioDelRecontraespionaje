package grafosTest;

import static org.junit.Assert.*;

import java.util.Set;

public class Assert {

	//Verifica que sean iguales como conjuntos
	public static void iguales(int[] esperado, Set<Integer> obtenido) {
		assertEquals(esperado.length, obtenido.size());
		for (int i = 0; i < esperado.length; i++) {
			assertTrue(obtenido.contains(esperado[i]));
		}
	}
}
