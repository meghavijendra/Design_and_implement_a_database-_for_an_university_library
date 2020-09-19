package Soccer;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewMember extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private static Logger log = LoggerFactory.getLogger(NewMember.class);
	private Calendar c = Calendar.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		log.info("Executing Program");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMember frame = new NewMember();
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
	public NewMember() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 397);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NEW MEMBER LOGIN PAGE ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(143, 11, 205, 38);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PHONE NO");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(26, 89, 79, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSsn.setBounds(26, 133, 79, 23);
		contentPane.add(lblSsn);
		
		JLabel lblCampusAddress = new JLabel("CAMPUS ADDRESS");
		lblCampusAddress.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCampusAddress.setBounds(26, 175, 102, 23);
		contentPane.add(lblCampusAddress);
		
		JLabel lblHomeAddress = new JLabel("HOME ADDRESS");
		lblHomeAddress.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHomeAddress.setBounds(26, 222, 79, 23);
		contentPane.add(lblHomeAddress);
		
		final JTextArea PHONE_NO = new JTextArea();
		PHONE_NO.setBounds(210, 88, 175, 22);
		contentPane.add(PHONE_NO);
		
		final JTextArea L_SSN = new JTextArea();
		L_SSN.setBounds(210, 132, 175, 22);
		contentPane.add(L_SSN);
		
		final JTextArea CAMPUS_ADDRESS = new JTextArea();
		CAMPUS_ADDRESS.setBounds(210, 174, 175, 22);
		contentPane.add(CAMPUS_ADDRESS);
		
		final JTextArea HOME_ADDRESS = new JTextArea();
		HOME_ADDRESS.setBounds(210, 221, 175, 22);
		contentPane.add(HOME_ADDRESS);
		
		final JTextArea ISPROFESSOR = new JTextArea();
		ISPROFESSOR.setBounds(210, 272, 175, 22);
		contentPane.add(ISPROFESSOR);
		
		JLabel lblAreYouProfessor = new JLabel("ARE YOU PROFESSOR?");
		lblAreYouProfessor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAreYouProfessor.setBounds(26, 273, 126, 23);
		contentPane.add(lblAreYouProfessor);
		
		JButton btnNewButton = new JButton("SUBMIT");
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				String S_SSN= "746-11-3480";
				Date date = new Date();
				c.setTime(date);
				c.add(Calendar.YEAR, 5);
				java.sql.Date newDate = new java.sql.Date(c.getTimeInMillis());
				
				createNewLibMember(PHONE_NO, L_SSN, CAMPUS_ADDRESS, HOME_ADDRESS, ISPROFESSOR, S_SSN,
						newDate);
				System.exit(0);
			}
		});
		
		btnNewButton.setBounds(296, 324, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Enter the values of following fields:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_2.setBounds(26, 64, 359, 14);
		contentPane.add(lblNewLabel_2);
	}
	
	private void createNewLibMember(final JTextArea PHONE_NO, final JTextArea L_SSN,
			final JTextArea CAMPUS_ADDRESS, final JTextArea HOME_ADDRESS, final JTextArea ISPROFESSOR,
			 String S_SSN, java.sql.Date newDate) {
		Boolean prof;
		String type;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			PreparedStatement prep = conn.prepareStatement("insert into LIBRARY_MEMBER values (?,?,?,?,?,?,?,?,?,?)");) {
			log.info("Connected");
			
			if (ISPROFESSOR.getText() == "yes")
			{
				prof = true;
				type = "FACULTY";
			}else
			{
				prof = false;
				type = "STUDENT";
			}
			
			prep.setString(1, PHONE_NO.getText());
			prep.setString(2,  L_SSN.getText());
			prep.setString(3, CAMPUS_ADDRESS.getText());
			prep.setString(4, HOME_ADDRESS.getText());
			prep.setInt(5,  0);
			prep.setBoolean(6, prof);
			prep.setString(7, type);
			prep.setString(8,  S_SSN);
			prep.setDate(9, newDate);
			prep.setString(10, CAMPUS_ADDRESS.getText());
			
			int res = prep.executeUpdate();

			if(res == 1 ) {
				JOptionPane.showMessageDialog(null, "Member added successfully");
			}
			log.info("Inserted into database");
		}catch(java.sql.SQLIntegrityConstraintViolationException userExsistException) {
			JOptionPane.showMessageDialog(null, "Member already exist");
		}
		catch (Exception exception) {
			log.error("Failed to insert into table", exception);

		}
	}	
}
