package InterfazPrincipal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Interfaz {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	
		JPanel panel = new JPanel();
		frame.add(panel);
		
		panel.setLayout(null	);
		JLabel label = new JLabel("Usuario");
		label.setBounds(10, 20, 80, 25);
		panel.add(label);
		
		JTextField userText = new JTextField();
		userText.setBounds(100,20,165,25);
		panel.add(userText);
		
		JLabel contraseña = new JLabel("Contraseña");
		contraseña.setBounds(10,50,85,25);
		panel.add(contraseña);
		
		JPasswordField contraseña1 = new JPasswordField();
		contraseña1.setBounds(100,50,165,25);
		panel.add(contraseña1);
		
		JLabel tipoUsuario = new JLabel("Tipo Usuario");
		tipoUsuario.setBounds(10,80,80,25);
		panel.add(tipoUsuario);
		
		JTextField tipo = new JTextField();
		tipo.setBounds(100,80,165,25);
		panel.add(tipo);
		
		JButton button = new JButton();
		button.setBounds(10,110,10,25);
		button.setSize(0,0);
		panel.add(button);
		
		
		
		
		
		
	}

}
