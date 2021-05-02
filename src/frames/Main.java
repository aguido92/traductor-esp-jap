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

public class Main extends JFrame {

	private static final long serialVersionUID = -1175581035221039000L;
	private JPanel contentPane, traducirPanel, agregarPanel, eliminarPanel;
	private JTextField txtTraducir;
	private JTextField textField;
	private JTextField textField_1;

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
		
		traducirPanel = new JPanel();
		traducirPanel.setBounds(0, 0, 694, 549);
		contentPane.add(traducirPanel);
		traducirPanel.setLayout(null);
		
		txtTraducir = new JTextField();
		txtTraducir.setText("Traducir");
		txtTraducir.setBounds(250, 227, 86, 20);
		traducirPanel.add(txtTraducir);
		
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
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(5, 5, 674, 22);
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem traducirItem = new JMenuItem("Traducir");
		traducirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().setVisible(false);
				setContentPane(traducirPanel);
				traducirPanel.setVisible(true);
			}
		});
		mnMenu.add(traducirItem);
		
		JMenuItem agregarItem = new JMenuItem("Agregar");
		agregarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().setVisible(false);
				setContentPane(agregarPanel);
				agregarPanel.setVisible(true);
			}
		});
		mnMenu.add(agregarItem);
		
		JMenuItem eliminarItem = new JMenuItem("Eliminar");
		eliminarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().setVisible(false);
				setContentPane(eliminarPanel);
				eliminarPanel.setVisible(true);
			}
		});
		mnMenu.add(eliminarItem);
		
		JMenuItem salirItem = new JMenuItem("Salir");
		salirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnMenu.add(salirItem);
		return menuBar;
	}
}
