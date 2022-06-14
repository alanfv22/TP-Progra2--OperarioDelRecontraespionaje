package vistaGrafos;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import grafos.Comunicacion;
import grafos.Grafo;
import grafos.Kruskal;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class InterfazAgmKruskal extends JFrame{

	private JFrame frame;
	private Grafo grafo;
	private ArrayList<String> espias;
	private ArrayList<Comunicacion> comunicaciones;
	private JTable tablaComunicaciones;
	private JTable tablaEspias;
	private Grafo grafoUF;


	public InterfazAgmKruskal(Grafo g) {
		setTitle("Operacion del recontraEspionaje");
		this.grafoUF = g;
		this.grafo = g;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		crearFrame();
		crearPanel();
		crearTabla();
		crearTitulo();
		cargarNombres();
		aplicarKruskal();
		cargarComunicaciones();
		mostrarTiempoEjecucion();
		creacionBtnMostrarOperacion();
		actualizarJSON(); // guarda los datos de grafo AGM en formato JSON
		cerrar();
	}

	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 852, 621);
		getContentPane().setBounds(100, 100, 852, 621);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
	}
	
	private void crearPanel() {
		getContentPane().setLayout(null);
	}

	private void crearTabla() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(102, 128, 144, 201);
		getContentPane().add(scrollPane);
		
		tablaEspias = new JTable();
		tablaEspias.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Espias"
			}
		));
		tablaEspias.setRowSelectionAllowed(false);
		tablaEspias.setEnabled(false);
		scrollPane.setViewportView(tablaEspias);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(330, 128, 483, 201);
		getContentPane().add(scrollPane_1);
		
		tablaComunicaciones = new JTable();
		tablaComunicaciones.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Espia ", "Se comunica con espia", "Probabilidad de intercepcion"
				}
			));
			scrollPane_1.setViewportView(tablaComunicaciones);
	}
	
	private void crearTitulo() {
		JLabel titulo = new JLabel("Resultado de la operaci\u00F3n");
		titulo.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 28));
		titulo.setBounds(230, 37, 300, 48);
		getContentPane().add(titulo);
	}

	
	private void aplicarKruskal() {
		this.grafo= Kruskal.kruskalUF(grafo);
	}

	private void cargarNombres() {	
		espias= new ArrayList<String>(grafo.getEspias().keySet());
		
		for(String nombre : espias) {
			DefaultTableModel model = (DefaultTableModel) tablaEspias.getModel();
			model.addRow(new Object[] { nombre});	
		}		
	}
	
	private void cargarComunicaciones() {
			comunicaciones= grafo.getAristas();		
			int pos=0;
			
		    for(Comunicacion arista : comunicaciones){
		    	DefaultTableModel model = (DefaultTableModel) tablaComunicaciones.getModel();
				model.addRow(new Object[] {espias.get(arista.getEspia1()) });
				tablaComunicaciones.setValueAt(espias.get(arista.getEspia2()), pos, 1);
				tablaComunicaciones.setValueAt(arista.getProbabilidad() + " % ", pos, 2);
				pos++;	
		    }
	}
	
	private void creacionBtnMostrarOperacion() {
		JButton btnMostrarOrdenOperacion = new JButton("Mostrar orden de operacion");
		btnMostrarOrdenOperacion.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 15));
		btnMostrarOrdenOperacion.setBounds(268, 481, 330, 54);
		getContentPane().add(btnMostrarOrdenOperacion);

		comportamientoBtnMostrarOrdenOperacion(btnMostrarOrdenOperacion);	
	}
	
	private void comportamientoBtnMostrarOrdenOperacion(JButton btnMostrarOrdenOperacion) { //muestra recorrido de la operacion utilizando BFS
		btnMostrarOrdenOperacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    cerrarVentana();
					InterfazFinalBFS grafoConBFS= new InterfazFinalBFS((grafo.ordenDeEncuentrosConBFS(Kruskal.getAgm())));
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
		long tiempo = Kruskal.tiempoEjecucion(grafoUF);
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
