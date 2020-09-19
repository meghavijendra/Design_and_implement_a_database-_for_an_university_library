package Soccer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class RenewMember extends JFrame {

	private JPanel contentPane;
	private JTextField L_SSN;
	private Calendar c = Calendar.getInstance();
	private JTextField DATE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RenewMember frame = new RenewMember();
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
	public RenewMember() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 342, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("RENEW YOUR MEMBERSHIP");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(62, 27, 264, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("CHECK CARD EXPIRY DATE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayEntry(L_SSN);
			
			}
		});
		btnNewButton.setBounds(136, 115, 180, 20);
		contentPane.add(btnNewButton);
		
		L_SSN = new JTextField();
		L_SSN.setBounds(143, 70, 173, 20);
		contentPane.add(L_SSN);
		L_SSN.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("RENEW CARD MEMBERSHIP");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date date = new Date();
				c.setTime(date);
				c.add(Calendar.YEAR, 5);
				java.sql.Date CARD_EXPIRY_DATE = new java.sql.Date(c.getTimeInMillis());
				renewCardMem(L_SSN,CARD_EXPIRY_DATE);
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(136, 226, 180, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("SSN");
		lblNewLabel_1.setBounds(31, 73, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		DATE = new JTextField();
		DATE.setBounds(150, 174, 166, 23);
		contentPane.add(DATE);
		DATE.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("CARD EXPIRY DATE");
		lblNewLabel_2.setBounds(31, 178, 97, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("*Please note that a card is valid only for 5 years");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_3.setBounds(31, 268, 232, 14);
		contentPane.add(lblNewLabel_3);
	}
	private void displayEntry(final JTextField L_SSN) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Connection conn;
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			System.out.println("connected");
			String selectQuery = "select * from LIBRARY_MEMBER where L_SSN=?";
			PreparedStatement pst = conn.prepareStatement(selectQuery);
			pst.setString(1, L_SSN.getText());
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				DATE.setText(rs.getString("CARD_EXPIRY_DATE"));
			}
			conn.close();

		}
		catch (Exception e) {
		e.printStackTrace();

	}
	}
	private void renewCardMem(final JTextField L_SSN,java.sql.Date CARD_EXPIRY_DATE){
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			PreparedStatement prep = conn.prepareStatement("update LIBRARY_MEMBER set CARD_EXPIRY_DATE=? where L_SSN=?");) {
		
			prep.setDate(1, CARD_EXPIRY_DATE);
			prep.setString(2,  L_SSN.getText());
			int res = prep.executeUpdate();
			if(res == 1 ) {
				JOptionPane.showMessageDialog(null, "Card has been renewed successfully and your new expiry date is  "+CARD_EXPIRY_DATE);
			}
		}catch(java.sql.SQLIntegrityConstraintViolationException userExsistException) {
			JOptionPane.showMessageDialog(null, "Card cannot be renewed currently as it does not  exist");
		}
		catch (Exception exception) {

		}
}
}
