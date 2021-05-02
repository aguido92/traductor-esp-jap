package frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.SystemColor;

public class Main extends JFrame {

	private static final long serialVersionUID = -1175581035221039000L;
	private JPanel contentPane, traducirPanel, agregarPanel, eliminarPanel;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel traducir_label;
	private JTextField input;
	private JButton traducir_button;
	private JTextPane output;
	private JButton agregar_button;
	private JButton eliminar_button;

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
		
		agregarPanel = new JPanel();
		agregarPanel.setBounds(0, 0, 694, 549);
		contentPane.add(agregarPanel);
		agregarPanel.setLayout(null);
		agregarPanel.setVisible(false);
		contentPane.add(agregarPanel);
		
		textField = new JTextField();
		textField.setText("Agregar");
		textField.setBounds(250, 227, 86, 20);
		agregarPanel.add(textField);
		
		eliminarPanel = new JPanel();
		eliminarPanel.setLayout(null);
		eliminarPanel.setBounds(0, 0, 694, 549);
		eliminarPanel.setVisible(false);
		contentPane.add(eliminarPanel);
		
		textField_1 = new JTextField();
		textField_1.setText("Eliminar");
		textField_1.setBounds(250, 227, 86, 20);
		eliminarPanel.add(textField_1);
		
		setJMenuBar(createJMenuBar());
		
	}
	
	private void crearTraducirPanel() {
		traducirPanel = new JPanel();
		traducirPanel.setBounds(0, 0, 694, 549);
		traducirPanel.setLayout(null);
		
		traducir_label = new JLabel("Traducir: ");
		traducir_label.setFont(new Font("Tahoma", Font.PLAIN, 21));
		traducir_label.setBounds(10, 19, 92, 20);
		traducirPanel.add(traducir_label);
		
		input = new JTextField();
		input.setFont(new Font("Tahoma", Font.PLAIN, 21));
		input.setHorizontalAlignment(SwingConstants.CENTER);
		input.setBounds(107, 11, 361, 45);
		traducirPanel.add(input);
		input.setColumns(10);
		
		traducir_button = new JButton("Traducir");
		traducir_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		traducir_button.setBounds(514, 11, 160, 45);
		traducirPanel.add(traducir_button);
		
		output = new JTextPane();
		output.setFont(new Font("Arial", Font.BOLD, 25));
		output.setBackground(SystemColor.control);
		output.setEditable(false);
		output.setBounds(227, 83, 447, 400);
		traducirPanel.add(output);
		
		agregar_button = new JButton("Agregar");
		agregar_button.addActionListener(e -> setVisibleAgregarPanel());
		agregar_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		agregar_button.setBounds(10, 483, 160, 45);
		traducirPanel.add(agregar_button);
		
		eliminar_button = new JButton("Eliminar");
		eliminar_button.addActionListener(e -> setVisibleEliminarPanel());
		eliminar_button.setFont(new Font("Tahoma", Font.PLAIN, 21));
		eliminar_button.setBounds(514, 483, 160, 45);
		traducirPanel.add(eliminar_button);
		
		contentPane.add(traducirPanel);
	}

	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(5, 5, 674, 22);
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem traducirItem = new JMenuItem("Traducir");
		traducirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleTraducirPanel();
			}
		});
		mnMenu.add(traducirItem);
		
		JMenuItem agregarItem = new JMenuItem("Agregar");
		agregarItem.addActionListener(e -> setVisibleAgregarPanel());
		mnMenu.add(agregarItem);
		
		JMenuItem eliminarItem = new JMenuItem("Eliminar");
		eliminarItem.addActionListener(e -> setVisibleEliminarPanel());
		mnMenu.add(eliminarItem);
		
		JMenuItem salirItem = new JMenuItem("Salir");
		salirItem.addActionListener(e -> System.exit(0));
		mnMenu.add(salirItem);
		return menuBar;
	}
	
	private void setVisibleTraducirPanel() {
		getContentPane().setVisible(false);
		setContentPane(traducirPanel);
		traducirPanel.setVisible(true);
	}
	
	private void setVisibleAgregarPanel() {
		getContentPane().setVisible(false);
		setContentPane(agregarPanel);
		agregarPanel.setVisible(true);
	}
	
	private void setVisibleEliminarPanel() {
		getContentPane().setVisible(false);
		setContentPane(eliminarPanel);
		eliminarPanel.setVisible(true);
	}
}
