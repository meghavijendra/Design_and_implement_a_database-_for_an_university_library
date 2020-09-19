package Soccer;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewBook extends JFrame {

	private JPanel contentPane;
	private JTextField AUTHOR;
	private JTextField SUBJECT;
	private JTextField TITLE;
	private JTextField BIND;
	private JTextField LANGUAGE;
	private JTextField ISBN;
	private JTextField IN_LIBRARY;


	/**
	 * 
	 */
	private static Logger log = LoggerFactory.getLogger(NewBook.class);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		log.info("Executing Program");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBook frame = new NewBook();
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
	public NewBook() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Please enter the following fields to insert the data of the book:");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblNewLabel.setBounds(30, 11, 415, 46);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("AUTHOR");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(30, 61, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblSubject = new JLabel("SUBJECT");
		lblSubject.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSubject.setBounds(30, 98, 86, 14);
		contentPane.add(lblSubject);
		
		JLabel lblTitle = new JLabel("TITLE");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTitle.setBounds(30, 143, 86, 14);
		contentPane.add(lblTitle);
		
		JLabel lblBind = new JLabel("BIND");
		lblBind.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBind.setBounds(30, 179, 86, 14);
		contentPane.add(lblBind);
		
		JLabel lblLanguage = new JLabel("LANGUAGE");
		lblLanguage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLanguage.setBounds(30, 223, 86, 14);
		contentPane.add(lblLanguage);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIsbn.setBounds(30, 262, 86, 14);
		contentPane.add(lblIsbn);
		
		JLabel lblIsTheBook = new JLabel("IS THE BOOK IN THE LIBRARY?");
		lblIsTheBook.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIsTheBook.setBounds(20, 303, 163, 14);
		contentPane.add(lblIsTheBook);
		
		AUTHOR = new JTextField();
		AUTHOR.setBounds(173, 58, 240, 20);
		contentPane.add(AUTHOR);
		AUTHOR.setColumns(10);
		
		SUBJECT = new JTextField();
		SUBJECT.setBounds(173, 95, 240, 20);
		contentPane.add(SUBJECT);
		SUBJECT.setColumns(10);
		
		TITLE = new JTextField();
		TITLE.setColumns(10);
		TITLE.setBounds(173, 140, 240, 20);
		contentPane.add(TITLE);
		
		BIND = new JTextField();
		BIND.setColumns(10);
		BIND.setBounds(173, 178, 240, 20);
		contentPane.add(BIND);
		
		LANGUAGE = new JTextField();
		LANGUAGE.setColumns(10);
		LANGUAGE.setBounds(173, 220, 240, 20);
		contentPane.add(LANGUAGE);
		
		ISBN = new JTextField();
		ISBN.setColumns(10);
		ISBN.setBounds(173, 259, 240, 20);
		contentPane.add(ISBN);
		
		IN_LIBRARY = new JTextField();
		IN_LIBRARY.setColumns(10);
		IN_LIBRARY.setBounds(173, 300, 240, 20);
		contentPane.add(IN_LIBRARY);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewBook(AUTHOR,SUBJECT,TITLE,BIND,LANGUAGE,ISBN,IN_LIBRARY);
				System.exit(0);
			}
		});
		btnNewButton.setBounds(325, 339, 89, 23);
		contentPane.add(btnNewButton);
	}
	private void createNewBook(final JTextField AUTHOR, final JTextField SUBJECT,
			final JTextField TITLE, final JTextField BIND, final JTextField LANGUAGE,
			final JTextField ISBN, final JTextField IN_LIBRARY) {
			Boolean in_lib;
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			PreparedStatement prep = conn.prepareStatement("insert into BOOK values (?,?,?,?,?,?,?)");) {
			log.info("Connected");
			
			if (IN_LIBRARY.getText() == "yes")
			{
				in_lib = true;
			}else
			{
				in_lib = false;
			}
			
			prep.setString(1, AUTHOR.getText());
			prep.setString(2,  SUBJECT.getText());
			prep.setString(3, TITLE.getText());
			prep.setString(4, BIND.getText());
			prep.setString(5,  LANGUAGE.getText());
			prep.setString(6, ISBN.getText());
			prep.setBoolean(7, in_lib);
			
			int res = prep.executeUpdate();

			if(res == 1 ) {
				JOptionPane.showMessageDialog(null, "Book added successfully");
			}
			log.info("Inserted into database");
		}catch(java.sql.SQLIntegrityConstraintViolationException userExsistException) {
			JOptionPane.showMessageDialog(null, "Book already exist");
		}
		catch (Exception exception) {
			log.error("Failed to insert into table", exception);

		}
	}	
}
