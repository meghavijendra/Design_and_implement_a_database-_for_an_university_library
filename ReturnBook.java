package Soccer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import java.awt.Font;

public class ReturnBook extends JFrame {

	private JPanel contentPane;
	private JTextField L_SSN;
	private JTextField TITLE;
	private JTextField ISSUE_DATE;
	private JComboBox ISBN;
	private Calendar c = Calendar.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnBook frame = new ReturnBook();
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
	public ReturnBook() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("RETURN BOOK");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(171, 11, 154, 27);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				c.setTime(date);
				java.sql.Date RETURN_DATE = new java.sql.Date(c.getTimeInMillis());
				deleteOldEntry(TITLE,ISBN,L_SSN,ISSUE_DATE,RETURN_DATE);
				System.exit(0);
			}
		});
		btnNewButton.setBounds(236, 262, 89, 23);
		contentPane.add(btnNewButton);
		
		L_SSN = new JTextField();
		L_SSN.setBounds(141, 55, 243, 20);
		contentPane.add(L_SSN);
		L_SSN.setColumns(10);
		
		
		TITLE = new JTextField();
		TITLE.setColumns(10);
		TITLE.setBounds(141, 164, 243, 20);
		contentPane.add(TITLE);
		
		JLabel lblNewLabel_1 = new JLabel("SSN");
		lblNewLabel_1.setBounds(31, 58, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblTitle = new JLabel("TITLE");
		lblTitle.setBounds(31, 167, 46, 14);
		contentPane.add(lblTitle);
		
		JLabel label = new JLabel("");
		label.setBounds(31, 129, 46, 14);
		contentPane.add(label);
		
		JLabel lblNewLabel_2 = new JLabel("ISBN");
		lblNewLabel_2.setBounds(31, 129, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		ISBN = new JComboBox();
		ISBN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Connection conn;
					conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
					System.out.println("connected");
					String selectQuery = "select * from ISSUE_BOOK where ISBN=?";
					PreparedStatement pst = conn.prepareStatement(selectQuery);
					pst.setString(1, (String)ISBN.getSelectedItem());
					ResultSet rs = pst.executeQuery();
					while(rs.next())
					{
						TITLE.setText(rs.getString("TITLE"));
						ISSUE_DATE.setText(rs.getString("ISSUE_DATE"));					
					}
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();

				}
				
			}
		});
		ISBN.setBounds(141, 123, 139, 20);
		contentPane.add(ISBN);
		
		JLabel lblNewLabel_3 = new JLabel("ISSUE DATE");
		lblNewLabel_3.setBounds(31, 210, 65, 14);
		contentPane.add(lblNewLabel_3);
		
		ISSUE_DATE = new JTextField();
		ISSUE_DATE.setColumns(10);
		ISSUE_DATE.setBounds(141, 207, 243, 20);
		contentPane.add(ISSUE_DATE);
		
		JButton btnConfirmSsn = new JButton("CONFIRM SSN");
		btnConfirmSsn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					Connection conn;
					conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
					System.out.println("connected");
					String Query = "select * from ISSUE_BOOK where L_SSN=?";
					PreparedStatement pst = conn.prepareStatement(Query);
					pst.setString(1, L_SSN.getText());
					ResultSet rs = pst.executeQuery();
					while(rs.next())
					{
						ISBN.addItem(rs.getString("ISBN"));				
					}
					conn.close();
				} catch (Exception e1) {
					e1.printStackTrace();

				}
			}
		});
		btnConfirmSsn.setBounds(263, 86, 139, 23);
		contentPane.add(btnConfirmSsn);
		
		JLabel lblNewLabel_4 = new JLabel("Please enter your SSN");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_4.setBounds(31, 33, 169, 14);
		contentPane.add(lblNewLabel_4);
	}
	
	private void deleteOldEntry(final JTextField TITLE,final JComboBox ISBN,final JTextField L_SSN,final JTextField ISSUE_DATE,java.sql.Date RETURN_DATE){
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			PreparedStatement prep = conn.prepareStatement("delete from ISSUE_BOOK where ISBN=? AND L_SSN=?");) {
			prep.setString(1,(String)ISBN.getSelectedItem());
			prep.setString(2,  L_SSN.getText());
			int res = prep.executeUpdate();
			UIManager.put("OptionPane.minimumSize",new Dimension(1000, 500));
			if(res == 1 )
			{
				JOptionPane.showMessageDialog(null,"Book has been returned successfully\n RETURN RECEIPT DETAILS\n YOUR SSN:" +L_SSN.getText() + "\n TITLE :" + TITLE.getText() + "\n ISBN:" + (String)ISBN.getSelectedItem() + "\n ISSUE DATE:" + ISSUE_DATE.getText() + "\n RETURN DATE:" + RETURN_DATE, "RETURN RECEIPT", JOptionPane.PLAIN_MESSAGE);
			}
		}catch(java.sql.SQLIntegrityConstraintViolationException userExsistException) {
			
			JOptionPane.showMessageDialog(null, "Book could not be returned currently");
		}
		catch (Exception exception) {

		}
	}

}
