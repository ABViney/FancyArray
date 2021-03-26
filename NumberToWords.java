import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NumberToWords {

	private JFrame frmNumberToWords;
	private JTextField txtInputField;
	private JTextField txtOutputField;
	private String output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NumberToWords window = new NumberToWords();
					window.frmNumberToWords.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NumberToWords() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Frame creation
		frmNumberToWords = new JFrame();
		frmNumberToWords.setTitle("Number to Words");
		frmNumberToWords.setBounds(100, 100, 450, 300);
		frmNumberToWords.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Prompt Label
		JLabel lblPromptInput = new JLabel("Input any number:");
		lblPromptInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		//Input Field
		txtInputField = new JTextField();
		txtInputField.setFont(new Font("Arial", Font.PLAIN, 12));
		txtInputField.setColumns(10);
		
		JButton btnConvertInput = new JButton("Convert");
		btnConvertInput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				output = NumToWord.numConvert(txtInputField.getText());
				txtOutputField.setText(output);
			}
		});
		
		txtOutputField = new JTextField();
		txtOutputField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtOutputField.setEditable(false);
		txtOutputField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frmNumberToWords.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPromptInput)
								.addComponent(txtInputField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(169)
							.addComponent(btnConvertInput))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtOutputField, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPromptInput, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtInputField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnConvertInput)
					.addGap(18)
					.addComponent(txtOutputField, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		frmNumberToWords.getContentPane().setLayout(groupLayout);
	}
}
