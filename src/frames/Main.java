package frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.FileController;
import exceptions.PalabraNoEncontradaException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Main extends JFrame {

	private static final long serialVersionUID = -1175581035221039000L;
	private JPanel contentPane, traducirPanel, agregarPanel, eliminarPanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		setTitle("Traductor ESP-JAP");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		crearTraducirPanel();
		crearAgregarPanel();
		crearEliminarPanel();
		setJMenuBar(createJMenuBar());
	}
	
	private void crearTraducirPanel() {
		traducirPanel = new JPanel();
		traducirPanel.setBounds(0, 0, 694, 549);
		traducirPanel.setLayout(null);
		
		JLabel traducir_label = new JLabel("Traducir: ");
		traducir_label.setFont(new Font("Tahoma", Font.PLAIN, 21));
		traducir_label.setBounds(10, 19, 92, 20);
		traducirPanel.add(traducir_label);
		
		JTextField input = new JTextField();
		input.setFont(new Font("Tahoma", Font.PLAIN, 21));
		input.setHorizontalAlignment(SwingConstants.CENTER);
		input.setBounds(107, 11, 361, 45);
		traducirPanel.add(input);
		
		JButton traducir_button = new JButton("Traducir");
		traducir_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		traducir_button.setBounds(524, 11, 160, 45);
		traducirPanel.add(traducir_button);
		
		JTextPane output = new JTextPane();
		output.setFont(new Font("Arial", Font.BOLD, 25));
		output.setBackground(UIManager.getColor("CheckBox.background"));
		output.setEditable(false);
		output.setBounds(227, 83, 447, 400);
		traducirPanel.add(output);
		
		JButton agregar_button = new JButton("Agregar");
		agregar_button.addActionListener(e -> setVisiblePanel(agregarPanel));
		agregar_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		agregar_button.setBounds(10, 483, 160, 45);
		traducirPanel.add(agregar_button);
		
		JButton eliminar_button = new JButton("Eliminar");
		eliminar_button.addActionListener(e -> setVisiblePanel(eliminarPanel));
		eliminar_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		eliminar_button.setBounds(524, 483, 160, 45);
		traducirPanel.add(eliminar_button);
		
		traducir_button.addActionListener(e -> {
			DefaultListModel<String> list;
			try {
				list = FileController.leer(input.getText());
				String traduccion = list.firstElement();
				output.setForeground(Color.BLACK);
				if (list.size() > 1) {
					traduccion = "1. " + traduccion;
					int i = 1;
					for (i = 1; i < list.size() && i < 10; i++) {
						traduccion += "\n" + (i+1) + ". " + list.getElementAt(i);
					}
					if (i < list.size()) {
						traduccion += "\n(" + (list.size()-i) +" traduccion/es mas)";
					}
				}
				output.setText(traduccion);
			} catch (PalabraNoEncontradaException ex) {
				output.setText("Palabra no encontrada");
				output.setForeground(Color.RED);
			} catch (FileNotFoundException ex1) {
				ex1.printStackTrace();
			}
		});
		
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					traducir_button.doClick();
				}
			}
		});
		
		contentPane.add(traducirPanel);
	}
	
	private void crearAgregarPanel() {
		agregarPanel = new JPanel();
		agregarPanel.setBounds(0, 0, 694, 549);
		contentPane.add(agregarPanel);
		agregarPanel.setLayout(null);
		agregarPanel.setVisible(false);
		contentPane.add(agregarPanel);
		
		JLabel espanol_label = new JLabel("Español");
		espanol_label.setFont(new Font("Tahoma", Font.PLAIN, 21));
		espanol_label.setHorizontalAlignment(SwingConstants.CENTER);
		espanol_label.setBounds(10, 58, 327, 26);
		agregarPanel.add(espanol_label);
		
		JTextPane input_esp = new JTextPane();
		input_esp.setFont(new Font("Tahoma", Font.PLAIN, 21));
		input_esp.setBounds(0, 95, 342, 267);
		agregarPanel.add(input_esp);
		
		JLabel japones_label = new JLabel("Japones");
		japones_label.setHorizontalAlignment(SwingConstants.CENTER);
		japones_label.setFont(new Font("Tahoma", Font.PLAIN, 21));
		japones_label.setBounds(347, 58, 327, 26);
		agregarPanel.add(japones_label);
		
		JTextPane input_jap = new JTextPane();
		input_jap.setFont(new Font("Tahoma", Font.PLAIN, 21));
		input_jap.setBounds(352, 95, 342, 267);
		agregarPanel.add(input_jap);
		
		JLabel respuesta = new JLabel("");
		respuesta.setFont(new Font("Tahoma", Font.PLAIN, 21));
		respuesta.setHorizontalAlignment(SwingConstants.CENTER);
		respuesta.setBounds(10, 388, 664, 80);
		agregarPanel.add(respuesta);
		
		JButton agregar_button = new JButton("AGREGAR");
		agregar_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		agregar_button.setBounds(524, 485, 160, 45);
		agregarPanel.add(agregar_button);
		
		JButton atras_button = new JButton("ATRAS");
		atras_button.setForeground(Color.WHITE);
		atras_button.setBackground(Color.RED);
		atras_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		atras_button.addActionListener(e -> setVisiblePanel(traducirPanel));
		atras_button.setBounds(10, 485, 160, 45);
		agregarPanel.add(atras_button);
		
		input_esp.getInputMap().put(KeyStroke.getKeyStroke("TAB"), new ChangeFocusInputAction(input_jap));
		input_esp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					agregar_button.doClick();
				}
			}
		});
		input_jap.getInputMap().put(KeyStroke.getKeyStroke("TAB"), new ChangeFocusInputAction(input_esp));
		input_jap.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					agregar_button.doClick();
				}
			}
		});
	}
	
	private void crearEliminarPanel() {
		eliminarPanel = new JPanel();
		eliminarPanel.setLayout(null);
		eliminarPanel.setBounds(0, 0, 694, 549);
		eliminarPanel.setVisible(false);
		contentPane.add(eliminarPanel);
		
		JList lista_esp = new JList();
		lista_esp.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lista_esp.setBounds(0, 0, 1, 1);
		eliminarPanel.add(lista_esp);
		
		JList lista_jap = new JList();
		lista_jap.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lista_jap.setBounds(432, 11, 252, 463);
		eliminarPanel.add(lista_jap);
		
		JButton eliminar_palabra_button = new JButton("ELIMINAR");
		eliminar_palabra_button.setEnabled(false);
		eliminar_palabra_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		eliminar_palabra_button.setBounds(524, 485, 160, 45);
		eliminarPanel.add(eliminar_palabra_button);
		
		JScrollPane scrollPane = new JScrollPane(lista_esp);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 11, 252, 463);
		eliminarPanel.add(scrollPane);
		
		JButton atras_button = new JButton("ATRAS");
		atras_button.setForeground(Color.WHITE);
		atras_button.setBackground(Color.RED);
		atras_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		atras_button.addActionListener(e -> setVisiblePanel(traducirPanel));
		atras_button.setBounds(10, 485, 160, 45);
		eliminarPanel.add(atras_button);
		
		JButton switch_button = new JButton("<-->");
		switch_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		switch_button.setBounds(267, 202, 160, 45);
		eliminarPanel.add(switch_button);
	}

	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(5, 5, 674, 22);
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem traducirItem = new JMenuItem("Traducir");
		traducirItem.addActionListener(e -> setVisiblePanel(traducirPanel));
		menu.add(traducirItem);
		
		JMenuItem agregarItem = new JMenuItem("Agregar");
		agregarItem.addActionListener(e -> setVisiblePanel(agregarPanel));
		menu.add(agregarItem);
		
		JMenuItem eliminarItem = new JMenuItem("Eliminar");
		eliminarItem.addActionListener(e -> setVisiblePanel(eliminarPanel));
		menu.add(eliminarItem);
		
		JMenuItem salirItem = new JMenuItem("Salir");
		salirItem.addActionListener(e -> System.exit(0));
		menu.add(salirItem);
		return menuBar;
	}
	
	private void setVisiblePanel(JPanel panel) {
		getContentPane().setVisible(false);
		setContentPane(panel);
		panel.setVisible(true);
	}
	
	private class ChangeFocusInputAction extends AbstractAction {
		private static final long serialVersionUID = -5219955235297262325L;

		private JTextPane textPane;
		
		public ChangeFocusInputAction(JTextPane textPane) {
			super();
			this.textPane = textPane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			textPane.requestFocus();
		}
		
	}
}
