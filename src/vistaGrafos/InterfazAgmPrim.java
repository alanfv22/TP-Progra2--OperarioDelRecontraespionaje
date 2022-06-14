package vistaGrafos;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import grafos.Comunicacion;
import grafos.Grafo;
import grafos.Kruskal;
import grafos.Prim;

public class InterfazAgmPrim extends JFrame {

	private JFrame frame;
	private JPanel panel;
	private Grafo grafo;
	private ArrayList<String> vertices;
	private ArrayList<Comunicacion> aristas;
	private Grafo grafoPrim;

	private JTable comunicaciones;
	private JTable espias;

	public InterfazAgmPrim(Grafo g) {
		getContentPane().setFont(new Font("Agency FB", Font.BOLD, 11));
		this.grafo = g;
		this.grafoPrim = g;
		initialize();
	}

	private void initialize() {
		crearFrame();
		crearPanel();
		crearTabla();

		cargarNombres();
		aplicarPrim();
		cargarComunicaciones();
		mostrarTiempoEjecucion();
		creacionBtnMostrarOperacion();
		actualizarJSON(); // guarda los datos de grafo AGM en formato JSON
		cerrar();
	}

	public void crearFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 855, 621);
		getContentPane().setBounds(100, 100, 852, 621);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public void crearPanel() {
		getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 352, 781, -352);
		panel.setLayout(null);
		this.getContentPane().add(panel);
	}

	public void crearTabla() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(102, 128, 144, 201);
		getContentPane().add(scrollPane);

		espias = new JTable();
		espias.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Espias" }));
		espias.setRowSelectionAllowed(false);
		espias.setEnabled(false);
		scrollPane.setViewportView(espias);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(330, 128, 483, 201);
		getContentPane().add(scrollPane_1);

		comunicaciones = new JTable();
		comunicaciones.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Espia ", "Se comunica con espia", "Probabilidad de intercepcion" }));
		scrollPane_1.setViewportView(comunicaciones);
	}

	public void cargarNombres() {
		vertices = (ArrayList<String>) grafo.dameNombresEspia().clone();

		for (String nombre : vertices) {
			DefaultTableModel model = (DefaultTableModel) espias.getModel();
			model.addRow(new Object[] { nombre });
		}
	}

	public void aplicarPrim() {
		grafo = Prim.algoritmoPrim(grafo);
	}

	private void cargarComunicaciones() {
		aristas = (ArrayList<Comunicacion>) grafo.getAristas().clone();
		int pos = 0;
		for (Comunicacion arista : aristas) {
			DefaultTableModel model = (DefaultTableModel) comunicaciones.getModel();
			model.addRow(new Object[] { vertices.get(arista.getEspia1()) });
			comunicaciones.setValueAt(vertices.get(arista.getEspia2()), pos, 1);
			comunicaciones.setValueAt(arista.getProbabilidad() + " % ", pos, 2);
			pos++;
		}
	}

	private void creacionBtnMostrarOperacion() {
		JButton btnMostrarOrdenOperacion = new JButton("Mostrar orden de operacion");
		btnMostrarOrdenOperacion.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 15));
		btnMostrarOrdenOperacion.setBounds(268, 481, 330, 54);
		getContentPane().add(btnMostrarOrdenOperacion);

		comportamientoBtnMostrarOrdenOperacion(btnMostrarOrdenOperacion);

		JLabel lblNewLabel = new JLabel("Resultado de la operaci\u00F3n");
		lblNewLabel.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 28));
		lblNewLabel.setBounds(250, 43, 309, 25);
		getContentPane().add(lblNewLabel);
	}

	private void comportamientoBtnMostrarOrdenOperacion(JButton btnMostrarOrdenOperacion) { // muestra recorrido de la
																							// operacion utilizando BFS
		btnMostrarOrdenOperacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cerrarVentana();
				InterfazFinalBFS grafoConBFS = new InterfazFinalBFS((grafo.ordenDeEncuentrosConBFS(Prim.getAgm())));
				grafoConBFS.setVisible(true);
				grafoConBFS.setSize(855, 621);
				grafoConBFS.setLocationRelativeTo(null);

			}
		});
	}

	private void cerrar() {
		try {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					cerrarVentana();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cerrarVentana() {
		this.setVisible(false);
	}

	private void mostrarTiempoEjecucion() {
		long tiempo = Prim.tiempoEjecucion(grafoPrim);
		JLabel lblTiempoEjec = new JLabel("Tiempo de ejecucion: " + String.valueOf(tiempo)+ " milisegundos");
		lblTiempoEjec.setFont(new Font("Agency FB", Font.BOLD, 13));
		lblTiempoEjec.setBounds(102, 352, 229, 25);
		getContentPane().add(lblTiempoEjec);
	}

	private void actualizarJSON() {
		grafo.cargarEspiasJSON();
		grafo.cargarComunicacionesJSON();
		grafo.actualizarJSON();
	}
}
