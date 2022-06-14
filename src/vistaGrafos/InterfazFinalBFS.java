package vistaGrafos;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import grafos.Comunicacion;
import grafos.Grafo;
import grafos.Kruskal;


import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class InterfazFinalBFS extends JFrame{

	private JFrame frame;
	private JPanel panel;
	
	private Grafo grafo;
	private ArrayList<String> espias;
	private ArrayList<Comunicacion> comunicaciones;
	
	private JTable tablaComunicaciones;

	public InterfazFinalBFS(Grafo g) {
		setTitle("Operacion del recontraEspionaje");
		this.grafo = g;
		initialize();
	}

	private void initialize() {
		crearFrame();
		crearPanel();
		crearTabla();
		cargarComunicaciones();
		cerrar();
	}

	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 852, 621);
		frame.getContentPane().setBounds(100, 100, 852, 621);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	private void crearPanel() {
		getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 352, 781, -352);
//		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
	}

	private void crearTabla() {
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(174, 154, 483, 232);
		getContentPane().add(scrollPane_1);
		
		tablaComunicaciones = new JTable();
		tablaComunicaciones.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Espia ", "Se comunica con espia"
				}
			));
			scrollPane_1.setViewportView(tablaComunicaciones);
			
			JLabel lblNewLabel = new JLabel("Orden de ejecucion de la operacion");
			lblNewLabel.setFont(new Font("Agency FB", Font.BOLD | Font.ITALIC, 28));
			lblNewLabel.setBounds(153, 45, 537, 78);
			getContentPane().add(lblNewLabel);
			
			JButton btnNewButton = new JButton("Volver a realizar operacion");
			btnNewButton.setFont(new Font("Agency FB", Font.BOLD, 15));
			btnNewButton.setBounds(266, 431, 272, 54);
			getContentPane().add(btnNewButton);
			
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cerrarVentana();
					InterfazInicialGrafo ventanaInicio = new InterfazInicialGrafo();
					ventanaInicio.setVisible(true);
				}
			});
			
			JButton btnNewButton_1 = new JButton("Salir");
			btnNewButton_1.setFont(new Font("Agency FB", Font.BOLD, 15));
			btnNewButton_1.setBounds(266, 483, 272, 54);
			getContentPane().add(btnNewButton_1);
			
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
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
	
	private void cargarComunicaciones() {
			espias= new ArrayList<String>(grafo.getEspias().keySet());
			comunicaciones= grafo.getAristas();		
			int pos=0;
	
		    for(Comunicacion arista : comunicaciones){
		    	DefaultTableModel model = (DefaultTableModel) tablaComunicaciones.getModel();
				model.addRow(new Object[] {espias.get(arista.getEspia1()) });
				tablaComunicaciones.setValueAt(espias.get(arista.getEspia2()), pos, 1);			
				pos++;			
		    }
	}
}
