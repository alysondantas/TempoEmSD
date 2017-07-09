package br.uefs.ecomp.tempoEmSD.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.uefs.ecomp.tempoEmSD.controller.ControllerRelogio;
import br.uefs.ecomp.tempoEmSD.controller.threads.ThreadAtualizaGUI;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RelogioGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textFieldSA;
	private JTextField textFieldMA;
	private JTextField textFieldHA;
	private JTextField textField_4;
	private JTextField textField_5;
	private JButton btnIniciarConectar;
	private JButton btnAlterar;
	private JLabel lblH;
	private JLabel lblM;
	private JLabel lblS;
	private JLabel lblId;
	private JLabel labelIdReferencia;
	private ControllerRelogio controller = ControllerRelogio.getInstance();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RelogioGUI window = new RelogioGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RelogioGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		String nome = System.getProperty("os.name");//recupera o nome do SO
		if(nome.substring(0, 7).equals("Windows")){//se ele for WINDOWS é colocado um LookAndFeel do windows para rodar melhorar a aparencia
			try { 
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 239);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Relogio", null, panel, null);
		panel.setLayout(null);
		
		JButton button = new JButton("Alterar");
		button.setEnabled(false);
		button.setBounds(310, 96, 89, 23);
		panel.add(button);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(10);
		textField.setBounds(310, 63, 28, 22);
		panel.add(textField);
		
		JLabel label = new JLabel("0.0");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(310, 27, 28, 22);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Drift:");
		label_1.setBounds(310, 11, 46, 14);
		panel.add(label_1);
		
		textFieldSA = new JTextField();
		textFieldSA.setEnabled(false);
		textFieldSA.setColumns(10);
		textFieldSA.setBounds(121, 64, 28, 20);
		panel.add(textFieldSA);
		
		JLabel label_2 = new JLabel(":");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_2.setBounds(104, 60, 18, 22);
		panel.add(label_2);
		
		textFieldMA = new JTextField();
		textFieldMA.setEnabled(false);
		textFieldMA.setColumns(10);
		textFieldMA.setBounds(66, 64, 28, 20);
		panel.add(textFieldMA);
		
		JLabel label_3 = new JLabel(":");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(49, 60, 18, 22);
		panel.add(label_3);
		
		textFieldHA = new JTextField();
		textFieldHA.setEnabled(false);
		textFieldHA.setColumns(10);
		textFieldHA.setBounds(11, 64, 28, 20);
		panel.add(textFieldHA);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnAlterar.setEnabled(false);
		btnAlterar.setBounds(11, 95, 89, 23);
		panel.add(btnAlterar);
		
		btnIniciarConectar = new JButton("Iniciar/Conectar");
		btnIniciarConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inicia();
			}
		});
		btnIniciarConectar.setBounds(11, 156, 111, 50);
		panel.add(btnIniciarConectar);
		
		lblS = new JLabel("00");
		lblS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblS.setBounds(87, 27, 28, 22);
		panel.add(lblS);
		
		JLabel label_5 = new JLabel(":");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_5.setBounds(76, 27, 18, 22);
		panel.add(label_5);
		
		lblM = new JLabel("00");
		lblM.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblM.setBounds(49, 27, 28, 22);
		panel.add(lblM);
		
		JLabel label_7 = new JLabel(":");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_7.setBounds(38, 27, 18, 22);
		panel.add(label_7);
		
		lblH = new JLabel("00");
		lblH.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblH.setBounds(11, 27, 28, 22);
		panel.add(lblH);
		
		JLabel label_9 = new JLabel("Tempo:");
		label_9.setBounds(10, 11, 46, 14);
		panel.add(label_9);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Configurações", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(10, 11, 23, 14);
		panel_1.add(lblIp);
		
		textField_4 = new JTextField();
		textField_4.setBounds(31, 8, 86, 20);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(127, 11, 40, 14);
		panel_1.add(lblPorta);
		
		textField_5 = new JTextField();
		textField_5.setBounds(166, 8, 86, 20);
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(10, 46, 89, 23);
		panel_1.add(btnAtualizar);
		
		
		
		JLabel lblIdN = new JLabel("Seu ID:");
		lblIdN.setBounds(326, 192, 46, 14);
		panel.add(lblIdN);
		
		lblId = new JLabel("0");
		lblId.setBounds(381, 192, 18, 14);
		panel.add(lblId);
		
		JLabel lblReferencia = new JLabel("Referencia: ");
		lblReferencia.setBounds(157, 11, 59, 14);
		panel.add(lblReferencia);
		
		labelIdReferencia = new JLabel("0");
		labelIdReferencia.setBounds(223, 11, 28, 14);
		panel.add(labelIdReferencia);
		
		ThreadAtualizaGUI threadAtualiza = new ThreadAtualizaGUI(lblH,lblM,lblS,lblId,labelIdReferencia);
		threadAtualiza.start();
		
	}
	
	public void inicia(){
		
		controller.iniciaContador();
		btnIniciarConectar.setEnabled(false);
		textFieldHA.setEnabled(true);
		textFieldMA.setEnabled(true);
		textFieldSA.setEnabled(true);
		btnAlterar.setEnabled(true);
		
	}
	
	public void alterar(){
		String hora = textFieldHA.getText();
		String minuto = textFieldMA.getText();
		String segundo = textFieldSA.getText();
		if(!hora.equals("")){
			try{
				int h = Integer.parseInt(hora);
				controller.setHoras(h);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(!minuto.equals("")){
			try{
				int m = Integer.parseInt(minuto);
				controller.setMinutos(m);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(!segundo.equals("")){
			try{
				int s = Integer.parseInt(segundo);
				controller.setSegundos(s);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
