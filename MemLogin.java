package Soccer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MemLogin extends JFrame {

	private JPanel contentPane;
	private JTextField L_SSN;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemLogin frame = new MemLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MemLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MEMBER LOGIN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(147, 23, 156, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setBounds(46, 113, 46, 14);
		contentPane.add(lblSsn);
		
		L_SSN = new JTextField();
		L_SSN.setBounds(149, 110, 185, 20);
		contentPane.add(L_SSN);
		L_SSN.setColumns(10);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String renewmem = RenewBook(L_SSN);
				JOptionPane.showMessageDialog(null, renewmem);
			}

		});
		btnNewButton.setBounds(247, 171, 89, 23);
		contentPane.add(btnNewButton);	
		
		JLabel lblNewLabel_1 = new JLabel("Enter the SSN:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_1.setBounds(42, 75, 192, 14);
		contentPane.add(lblNewLabel_1);
	}
	private String RenewBook(final JTextField L_SSN) {
		StringBuilder sb = new StringBuilder();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			PreparedStatement prep = conn.prepareStatement("select * from LIBRARY_MEMBER where CARD_EXPIRY_DATE < now() AND L_SSN=?");) {
			prep.setString(1, L_SSN.getText());
			ResultSet res = prep.executeQuery();
			if(res.next()) {
				sb.append("Yor library Card Membership has been expired on").append(res.getString("CARD_EXPIRY_DATE")).append("To renew please go to RenewMember.java");
			}else {
				sb.append("You have successfully logged in!");
			}
			return sb.toString();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
