package frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.FileController;
import exceptions.PalabraNoEncontradaException;

public class Main extends JFrame {

	private static final long serialVersionUID = -1175581035221039000L;

	private JPanel contentPane, traducirPanel, agregarPanel, eliminarPanel;
	private JRadioButton radioButtonJap, radioButtonEsp;
	private Boolean esp_jap = true;

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
		traducir_label.setFont(new Font("SansSerif", Font.PLAIN, 21));
		traducir_label.setBounds(10, 19, 92, 20);
		traducirPanel.add(traducir_label);
		
		radioButtonJap = new JRadioButton("Japones");
		radioButtonJap.setBounds(10, 63, 109, 23);
		radioButtonJap.setSelected(true);
		traducirPanel.add(radioButtonJap);
		
		radioButtonEsp = new JRadioButton("Español");
		radioButtonEsp.setBounds(143, 63, 109, 23);
		traducirPanel.add(radioButtonEsp);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioButtonJap);
		group.add(radioButtonEsp);
		
		JTextField input = new JTextField();
		input.setFont(new Font("SansSerif", Font.BOLD, 25));
		input.setHorizontalAlignment(SwingConstants.CENTER);
		input.setBounds(107, 11, 361, 45);
		traducirPanel.add(input);
		
		JButton traducir_button = new JButton("Traducir");
		traducir_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		traducir_button.setBounds(524, 11, 160, 45);
		traducirPanel.add(traducir_button);
		
		JTextPane output = new JTextPane();
		output.setFont(new Font("Arial", Font.BOLD, 25));
		output.setBackground(UIManager.getColor("CheckBox.background"));
		output.setEditable(false);
		output.setBounds(227, 93, 447, 390);
		traducirPanel.add(output);
		
		JButton agregar_button = new JButton("Agregar");
		agregar_button.addActionListener(e -> setVisiblePanel(agregarPanel));
		agregar_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		agregar_button.setBounds(10, 483, 160, 45);
		traducirPanel.add(agregar_button);
		
		JButton eliminar_button = new JButton("Eliminar");
		eliminar_button.addActionListener(e -> setVisiblePanel(eliminarPanel));
		eliminar_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		eliminar_button.setBounds(524, 483, 160, 45);
		traducirPanel.add(eliminar_button);
		
		traducir_button.addActionListener(e -> {
			DefaultListModel<String> list;
			try {
				list = FileController.leer(input.getText(), intValueOf(radioButtonEsp.isSelected()));
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
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()!=KeyEvent.VK_BACK_SPACE && 
						e.getKeyCode()!=KeyEvent.VK_DELETE &&
						e.getKeyCode()!=KeyEvent.VK_CAPS_LOCK &&
						e.getKeyCode()!=KeyEvent.VK_SHIFT &&
						e.getKeyCode()!=KeyEvent.VK_ENTER) {
					String entrada = input.getText();
					Boolean idiomaJap = radioButtonJap.isSelected();
					List<String> todas = FileController.asList(idiomaJap.compareTo(true));
					for (String p : todas) {
						if (p.startsWith(entrada)) {									
							input.setText(p);
							input.setSelectionStart(entrada.length());
							input.setSelectionEnd(p.length());
							break;
						}
					}
					
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
		espanol_label.setFont(new Font("SansSerif", Font.PLAIN, 21));
		espanol_label.setHorizontalAlignment(SwingConstants.CENTER);
		espanol_label.setBounds(10, 58, 327, 26);
		agregarPanel.add(espanol_label);
		
		JTextPane input_esp = new JTextPane();
		input_esp.setFont(new Font("SansSerif", Font.PLAIN, 21));
		input_esp.setBounds(0, 95, 342, 267);
		agregarPanel.add(input_esp);
		
		JLabel japones_label = new JLabel("Japones");
		japones_label.setHorizontalAlignment(SwingConstants.CENTER);
		japones_label.setFont(new Font("SansSerif", Font.PLAIN, 21));
		japones_label.setBounds(347, 58, 327, 26);
		agregarPanel.add(japones_label);
		
		JTextPane input_jap = new JTextPane();
		input_jap.setFont(new Font("SansSerif", Font.PLAIN, 21));
		input_jap.setBounds(352, 95, 342, 267);
		agregarPanel.add(input_jap);
		
		JLabel respuesta = new JLabel("");
		respuesta.setFont(new Font("SansSerif", Font.PLAIN, 21));
		respuesta.setHorizontalAlignment(SwingConstants.CENTER);
		respuesta.setBounds(10, 388, 664, 80);
		agregarPanel.add(respuesta);
		
		JButton agregar_button = new JButton("AGREGAR");
		agregar_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		agregar_button.setBounds(524, 485, 160, 45);
		agregarPanel.add(agregar_button);
		
		JButton atras_button = new JButton("ATRAS");
		atras_button.setForeground(Color.WHITE);
		atras_button.setBackground(Color.RED);
		atras_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		atras_button.addActionListener(e -> setVisiblePanel(traducirPanel));
		atras_button.setBounds(10, 485, 160, 45);
		agregarPanel.add(atras_button);
		
		input_esp.getInputMap().put(KeyStroke.getKeyStroke("TAB"), new ChangeFocusInputAction(input_jap));
		input_esp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				input_esp.setText(input_esp.getText().replace("\n", ""));
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					agregar_button.doClick();
				}
			}
		});
		input_esp.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				input_esp.setText(input_esp.getText().replace("\r\n", ""));
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				input_esp.setText(input_esp.getText().replace("\r\n", ""));
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
		input_jap.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (input_jap.getText().equals("\r\n")) {
					input_jap.setText(input_jap.getText().trim());
				}
			}
		});
		input_jap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (input_jap.getText().equals("\r\n")) {
					input_jap.setText("");
				}
			}
		});
		agregar_button.addActionListener(e -> {
			if (input_esp.getText().equals("") || input_jap.getText().equals("")) {
				respuesta.setText("Campo/s vacio/s");
				respuesta.setForeground(Color.RED);
			} else {
				FileController.escribir(input_jap.getText().trim(), input_esp.getText().trim());
				respuesta.setText("Agregado exitosamente!");
				respuesta.setForeground(Color.BLUE);
				input_esp.setText("");
				input_jap.setText("");
			}
		});
	}
	
	private void crearEliminarPanel() {
		eliminarPanel = new JPanel();
		eliminarPanel.setLayout(null);
		eliminarPanel.setBounds(0, 0, 694, 549);
		eliminarPanel.setVisible(false);
		contentPane.add(eliminarPanel);
		
		JList<String> lista_esp = new JList<String>();
		lista_esp.setFont(new Font("SansSerif", Font.BOLD, 25));
		lista_esp.setBounds(0, 0, 1, 1);
		lista_esp.setModel(FileController.getAll(esp_jap.compareTo(false)));
		eliminarPanel.add(lista_esp);
		
		JList<String> lista_jap = new JList<String>();
		lista_jap.setFont(new Font("SansSerif", Font.BOLD, 25));
		lista_jap.setBounds(432, 81, 252, 393);
		eliminarPanel.add(lista_jap);
		
		JButton eliminar_button = new JButton("ELIMINAR");
		eliminar_button.setEnabled(false);
		eliminar_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		eliminar_button.setBounds(524, 485, 160, 45);
		eliminarPanel.add(eliminar_button);
		
		JScrollPane scrollPane = new JScrollPane(lista_esp);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 81, 252, 393);
		eliminarPanel.add(scrollPane);
		
		JButton atras_button = new JButton("ATRAS");
		atras_button.setForeground(Color.WHITE);
		atras_button.setBackground(Color.RED);
		atras_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		atras_button.addActionListener(e -> setVisiblePanel(traducirPanel));
		atras_button.setBounds(10, 485, 160, 45);
		eliminarPanel.add(atras_button);
		
		JButton switch_button = new JButton("<-->");
		switch_button.setFont(new Font("SansSerif", Font.PLAIN, 21));
		switch_button.setBounds(267, 202, 160, 45);
		eliminarPanel.add(switch_button);
		
		JLabel espanol_label = new JLabel("Español");
		espanol_label.setHorizontalAlignment(SwingConstants.CENTER);
		espanol_label.setFont(new Font("SansSerif", Font.PLAIN, 21));
		espanol_label.setBounds(10, 11, 252, 70);
		eliminarPanel.add(espanol_label);
		
		JLabel japones_label = new JLabel("Japones");
		japones_label.setHorizontalAlignment(SwingConstants.CENTER);
		japones_label.setFont(new Font("SansSerif", Font.PLAIN, 21));
		japones_label.setBounds(432, 11, 252, 70);
		eliminarPanel.add(japones_label);
		
		eliminar_button.addActionListener(e -> {
			String esp = esp_jap ? lista_esp.getSelectedValue() : lista_jap.getSelectedValue();
			String jap = esp_jap ? lista_jap.getSelectedValue() : lista_esp.getSelectedValue();
			FileController.eliminar(jap, esp);
			lista_esp.setModel(FileController.getAll(esp_jap.compareTo(false)));
			try {
				lista_jap.setModel(FileController.leer(esp_jap ? esp : jap, intValueOf(esp_jap)));					
			} catch (FileNotFoundException | PalabraNoEncontradaException ex) {
				lista_jap.setModel(new DefaultListModel<String>());
			}
		});
		
		switch_button.addActionListener(e -> {
			String aux = espanol_label.getText();
			espanol_label.setText(japones_label.getText());
			japones_label.setText(aux);
			esp_jap = !esp_jap;
			lista_esp.setModel(FileController.getAll(esp_jap.compareTo(false)));
			lista_jap.setModel(new DefaultListModel<String>());
		});
		
		lista_esp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					lista_jap.setModel(FileController.leer(lista_esp.getSelectedValue(), intValueOf(esp_jap)));
				} catch (PalabraNoEncontradaException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		lista_jap.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				eliminar_button.setEnabled(lista_esp.getSelectedValue() != null && lista_jap.getSelectedValue() != null);					
			}
		});
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
	
	private int intValueOf(boolean b) {
		return b ? 1 : 0;
	}
	
}
