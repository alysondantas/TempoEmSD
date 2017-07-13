package br.uefs.ecomp.tempoEmSD.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import java.awt.Color;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class RelogioGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textFieldSA;
	private JTextField textFieldMA;
	private JTextField textFieldHA;
	private JTextField textFieldIP;
	private JButton btnIniciarConectar;
	private JButton btnAlterar;
	private JButton button;
	private JLabel lblH;
	private JLabel lblM;
	private JLabel lblS;
	private JLabel lblId;
	private JLabel labelIdReferencia;
	private JLabel labelDrift;
	private JButton btnAtualizar;
	private ControllerRelogio controller = ControllerRelogio.getInstance();
	private JTextField textFieldSeuID;


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

		button = new JButton("Alterar");
		button.setEnabled(false);
		button.setBounds(310, 96, 89, 23);
		panel.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				alteraDrift();
			}
		});

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(10);
		textField.setBounds(310, 63, 28, 22);
		panel.add(textField);

		labelDrift = new JLabel("0.0");
		labelDrift.setBackground(Color.LIGHT_GRAY);
		labelDrift.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelDrift.setBounds(310, 27, 28, 22);
		panel.add(labelDrift);

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

		JLabel lblIp = new JLabel("MultiCast:");
		lblIp.setBounds(10, 11, 54, 14);
		panel_1.add(lblIp);

		textFieldIP = new JTextField();
		textFieldIP.setText("233.4.5.6");
		textFieldIP.setBounds(64, 8, 96, 20);
		panel_1.add(textFieldIP);
		textFieldIP.setColumns(10);

		btnAtualizar = new JButton("Definir");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizaMulticast();
			}
		});
		btnAtualizar.setBounds(10, 46, 89, 23);
		panel_1.add(btnAtualizar);

		JLabel lblSeuId = new JLabel("Seu ID:");
		lblSeuId.setBounds(10, 80, 46, 14);
		panel_1.add(lblSeuId);

		textFieldSeuID = new JTextField();
		textFieldSeuID.setBounds(64, 80, 96, 20);
		panel_1.add(textFieldSeuID);
		textFieldSeuID.setColumns(10);

		JButton button_1 = new JButton("Definir");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterarSeuID();
			}
		});
		button_1.setBounds(10, 117, 89, 23);
		panel_1.add(button_1);



		JLabel lblIdN = new JLabel("Seu ID:");
		lblIdN.setBounds(310, 192, 46, 14);
		panel.add(lblIdN);

		lblId = new JLabel("0");
		lblId.setBounds(366, 192, 33, 14);
		panel.add(lblId);

		JLabel lblReferencia = new JLabel("Referencia: ");
		lblReferencia.setBounds(178, 192, 59, 14);
		panel.add(lblReferencia);

		labelIdReferencia = new JLabel("0");
		labelIdReferencia.setBounds(244, 192, 28, 14);
		panel.add(labelIdReferencia);

		ThreadAtualizaGUI threadAtualiza = new ThreadAtualizaGUI(lblH,lblM,lblS,lblId,labelIdReferencia,labelDrift);
		threadAtualiza.start();

	}

	/**
	 * Metodo que inicia o contador
	 */
	public void inicia(){
		controller.iniciaContador();
		btnIniciarConectar.setEnabled(false);
		btnAtualizar.setEnabled(false);
		textFieldIP.setEnabled(false);
		textFieldHA.setEnabled(true);
		textFieldMA.setEnabled(true);
		textFieldSA.setEnabled(true);
		btnAlterar.setEnabled(true);
		textField.setEnabled(true);
		button.setEnabled(true);
	}

	/**
	 * Metodo que altera o tempo
	 */
	public void alterar(){
		String hora = textFieldHA.getText();
		String minuto = textFieldMA.getText();
		String segundo = textFieldSA.getText();
		if(!hora.equals("")){
			try{
				int h = Integer.parseInt(hora);
				if(h<24 && h>-1){
					controller.setHoras(h);
				}else{
					JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Apenas numeros","Erro",2);	
			}
		}
		if(!minuto.equals("")){
			try{
				int m = Integer.parseInt(minuto);
				if(m>-1 && m<60){
					controller.setMinutos(m);
				}else{
					JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
			}
		}
		if(!segundo.equals("")){
			try{
				int s = Integer.parseInt(segundo);
				if(s>-1 && s<60){
					controller.setSegundos(s);
				}else{
					JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
			}
		}
	}

	/**
	 * Metodo que altera o drift
	 */
	public void alteraDrift(){
		if(!textField.getText().equals("")){
			try{
				double drift = Double.parseDouble(textField.getText());
				if(drift>-1){
					System.err.println("novo drift inserido: " + textField.getText());
					controller.setDrift(drift);
				}else{
					JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
			}
		}
	}

	/**
	 * Metodo que altera o ip multicast
	 */
	public void atualizaMulticast(){
		controller.setIp(textFieldIP.getText());
	}

	/**
	 * Metodo que altera o id
	 */
	public void alterarSeuID(){
		try{
			int id = Integer.parseInt(textFieldSeuID.getText());
			controller.setId(id);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Numero invalido","Erro",2);	
		}

	}
}
