package Soccer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class NotifyMember extends JFrame {

	private JPanel contentPane;
	private JTextField L_SSN;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotifyMember frame = new NotifyMember();
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
	public NotifyMember() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		L_SSN = new JTextField();
		L_SSN.setBounds(164, 45, 166, 20);
		contentPane.add(L_SSN);
		L_SSN.setColumns(10);
		
		JButton btnNewButton = new JButton("RENEW");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String booksExpried = getExpircedBooks(L_SSN);
				JOptionPane.showMessageDialog(null, booksExpried);
			}

		});
		btnNewButton.setBounds(144, 188, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("RENEW BOOKS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(151, 0, 127, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("SSN");
		lblNewLabel_1.setBounds(40, 48, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("TITLE");
		lblNewLabel_2.setBounds(40, 91, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(164, 85, 166, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("ISBN");
		lblNewLabel_3.setBounds(40, 136, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setText("");
		textField_1.setBounds(164, 133, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Enter the following fields:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_4.setBounds(40, 23, 290, 14);
		contentPane.add(lblNewLabel_4);
		//call event
		//select book_name from table_name where overdue_date< now();
	}

	private String getExpircedBooks(final JTextField L_SSN) {
		StringBuilder sb = new StringBuilder();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
				PreparedStatement prep = conn.prepareStatement("select * from issue_book where NOTIFICATION_DATE < now() AND L_SSN=?");) {
			int count = 1;
			prep.setString(1, L_SSN.getText());
			ResultSet res = prep.executeQuery();
			sb.append("List of books\n");
			while(res.next()) {
				sb.append(count++).append(") ").append(res.getString("TITLE")).append("\n");
			}
			System.out.println(sb.toString());
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return sb.toString();
	}
}
