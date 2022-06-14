package vistaGrafos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import grafos.Grafo;
import grafos.Kruskal;
import grafos.Prim;

import javax.swing.JButton;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;


public class InterfazInicialGrafo extends JFrame{

	private JFrame frmOperacionDelRecontraespionaje;
	JLabel nombre;
	private Grafo grafo;
	protected String nombreEspia;
	protected String nombreEspia2;
	private String probabilidad;
	private float pesoRiesgo;
	private JTable tablaEspias;
	private JTable tablaComunicaciones;
	int cantEspias;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazInicialGrafo window = new InterfazInicialGrafo();
					window.frmOperacionDelRecontraespionaje.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InterfazInicialGrafo() {
		initialize();
	}

	private void initialize() {

		creacionFrame();
		creacionTitulo();
		creacionGrafo();
		
		creacionBtnCargarComunicaciones();
		creacionBtnMostrarOperacion();
		cargarEspiasEnTabla();
	}

	private void creacionFrame() {
		frmOperacionDelRecontraespionaje = new JFrame();
		frmOperacionDelRecontraespionaje.setTitle("Operacion del recontraEspionaje");
		frmOperacionDelRecontraespionaje.setBounds(100, 100, 855, 621);
		frmOperacionDelRecontraespionaje.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOperacionDelRecontraespionaje.setLocationRelativeTo(null);
		frmOperacionDelRecontraespionaje.setResizable(false);
	}

	private void creacionTitulo() {
		frmOperacionDelRecontraespionaje.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("Operacion del recontraEspionaje");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(176, 43, 490, 75);
		lblNewLabel.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 28));
		frmOperacionDelRecontraespionaje.getContentPane().add(lblNewLabel);
	}

	private void creacionGrafo() {
		JOptionPane.showMessageDialog(null, "Debe ingresar un numero entre 2 y 12");
		String cantEspias = JOptionPane.showInputDialog("Ingrese la cantidad de espias para la operacion");
		if (Grafo.verificacionEsNumero(cantEspias)) {
			this.cantEspias = Integer.parseInt(cantEspias);
			this.grafo = new Grafo(this.cantEspias);
		} 
		else {
			JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	
	private void cargarEspiasEnTabla() {
		while (this.cantEspias != 0) {

			nombreEspia = JOptionPane.showInputDialog("Ingrese el nombre del espia ");

			if (grafo.verificarCargarEspiaMensaje(nombreEspia)=="") {
				DefaultTableModel model = (DefaultTableModel) tablaEspias.getModel();
				model.addRow(new Object[] { nombreEspia });

				cantEspias--;
				cargarEspiasEnGrafo();

			} else
				JOptionPane.showMessageDialog(null, grafo.verificarCargarEspiaMensaje(nombreEspia), "Error",
						JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarEspiasEnGrafo() {

		int cantEspias = tablaEspias.getRowCount();

		for (int i = 0; i < cantEspias; i++)
			grafo.agregarVerticesNombre((String) tablaEspias.getValueAt(i, 0), i);	
	}

	private void creacionBtnCargarComunicaciones() {

		JButton btnCargarComunicacionesEntre = new JButton("cargar comunicaciones entre espias");
		btnCargarComunicacionesEntre.setFont(new Font("Agency FB", Font.BOLD, 15));
		btnCargarComunicacionesEntre.setBounds(214, 407, 405, 43);
		frmOperacionDelRecontraespionaje.getContentPane().add(btnCargarComunicacionesEntre);
		comportamientoBtnCargarComunicacion(btnCargarComunicacionesEntre);

		creacionTabla();
	}
	
	private void creacionTabla() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(93, 172, 110, 208);
		frmOperacionDelRecontraespionaje.getContentPane().add(scrollPane);

		tablaEspias = new JTable();
		tablaEspias.setFont(new Font("Agency FB", Font.BOLD, 15));
		tablaEspias.setEnabled(false);
		tablaEspias.setRowSelectionAllowed(false);
		tablaEspias.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Espias" }));
		scrollPane.setViewportView(tablaEspias);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(224, 172, 529, 208);
		frmOperacionDelRecontraespionaje.getContentPane().add(scrollPane_1);

		tablaComunicaciones = new JTable();
		tablaComunicaciones.setFont(new Font("Agency FB", Font.BOLD, 15));
		tablaComunicaciones.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Espia ", "Se comunica con espia", "Probabilidad de intercepcion" }));
		scrollPane_1.setViewportView(tablaComunicaciones);
	}

	private void comportamientoBtnCargarComunicacion(JButton cargarComunicacion) {

		cargarComunicacion.addActionListener(new ActionListener() {

			int pos = 0;

			public void actionPerformed(ActionEvent e) {

				nombreEspia = JOptionPane.showInputDialog("Ingrese el nombre del espia ");
				nombreEspia2 = JOptionPane.showInputDialog("Ingrese el nombre del otro espia");
				probabilidad = JOptionPane.showInputDialog("Ingrese la probabilidad de intercepcion");

				cargarComunicacion();
			}

			private void cargarComunicacion() {
				if (grafo.verificarComunicacionMensaje(nombreEspia, nombreEspia2, probabilidad)=="") {
					
					DefaultTableModel model = (DefaultTableModel) tablaComunicaciones.getModel();
					model.addRow(new Object[] { nombreEspia });
					tablaComunicaciones.setValueAt(nombreEspia, pos, 1);
					tablaComunicaciones.setValueAt(nombreEspia2, pos, 1);
					tablaComunicaciones.setValueAt(probabilidad + " % ", pos, 2);

					pesoRiesgo = Float.valueOf(probabilidad);
					cargarComunicacionEnGrafo();
					pos++;
				}else {
					JOptionPane.showMessageDialog(null,
							grafo.verificarComunicacionMensaje(nombreEspia, nombreEspia2, probabilidad), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}


			private void cargarComunicacionEnGrafo() {
				grafo.agregarComunicacion(nombreEspia, nombreEspia2, pesoRiesgo);

			}
		});

	}

	private void creacionBtnMostrarOperacion() {
		JButton btnMostrarOperacionKruskal = new JButton("mostrar espias con Kruskal");
		btnMostrarOperacionKruskal.setFont(new Font("Agency FB", Font.BOLD, 15));
		btnMostrarOperacionKruskal.setBounds(214, 461, 405, 43);
		frmOperacionDelRecontraespionaje.getContentPane().add(btnMostrarOperacionKruskal);
		comportamientoBtnMostrarOperacionKruskal(btnMostrarOperacionKruskal);
		
		JButton btnMostrarOperacionPrim = new JButton("mostrar espias con Prim");
		btnMostrarOperacionPrim.setFont(new Font("Agency FB", Font.BOLD, 15));
		btnMostrarOperacionPrim.setBounds(214, 518, 405, 43);
		frmOperacionDelRecontraespionaje.getContentPane().add(btnMostrarOperacionPrim);
		comportamientoBtnMostrarOperacionPrim(btnMostrarOperacionPrim);

	}
	
	private void comportamientoBtnMostrarOperacionPrim(JButton btnMostrarOperacionPrim) {
		btnMostrarOperacionPrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Prim.algoritmoPrim(grafo);
				if(Prim.getEsConexo()) {
					InterfazAgmPrim grafoPrim = new InterfazAgmPrim(grafo);
					grafoPrim.setVisible(true);
					grafoPrim.setSize(855, 621);
					grafoPrim.setLocationRelativeTo(null);
					frmOperacionDelRecontraespionaje.setVisible(false);
					frmOperacionDelRecontraespionaje.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "No pueden quedar espias incomunicados",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}

	private void comportamientoBtnMostrarOperacionKruskal(JButton btnMostrarOperacionKruskal) {
		btnMostrarOperacionKruskal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Kruskal.kruskalUF(grafo);
				if (Kruskal.getEsConexo()) {
					InterfazAgmKruskal grafoAGM= new InterfazAgmKruskal((grafo));
					grafoAGM.setVisible(true);
					grafoAGM.setSize(855, 621);
					grafoAGM.setLocationRelativeTo(null);
					frmOperacionDelRecontraespionaje.setVisible(false);
					frmOperacionDelRecontraespionaje.dispose();

				} else {
					JOptionPane.showMessageDialog(null, "No pueden quedar espias incomunicados",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	
	public void setVisible(boolean b) {
		frmOperacionDelRecontraespionaje.setVisible(b);
	}

}