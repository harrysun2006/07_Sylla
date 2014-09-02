/*
 * Created on 2006-1-22
 *
 * created by harry
 */
package com.sylla;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sylla.util.SwingUtil;

/**
 * @author harry
 */
public class CodeChecker {

	private static final GridBagConstraints GBC_CODE = new GridBagConstraints(
			0, GridBagConstraints.RELATIVE, 
			0, 1, 0, 0, GridBagConstraints.NORTH,
			GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

	private static final Object COMPONENT_LOCK = new Object();

	private static JPanel codePane;

	private static JFrame codeFrame;

	static {
		JDialog.setDefaultLookAndFeelDecorated(true);
		codeFrame = new JFrame();
		codeFrame.setTitle("Please tell me the code!");
		codeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Container mainPane = codeFrame.getContentPane();
		mainPane.setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane();
		codePane = new JPanel(new GridBagLayout());
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getViewport().add(codePane);
		/*
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				Dimension d = codeFrame.getSize();
				d.width += e.getValue();
				codeFrame.setPreferredSize(d);
				codeFrame.pack();
			}
		});
		*/
		mainPane.add(scrollPane, BorderLayout.NORTH);
		codeFrame.setResizable(false);
		codeFrame.setVisible(false);
	}

	private CodeChecker() {
	}

	public static String getCode(final byte[] data) {

		final Samephore s = new Samephore(0);
		final StringBuffer code = new StringBuffer();
		
		// must synchronize the adding, otherwise sometimes the text is uneditable
		synchronized(COMPONENT_LOCK) {
			final JPanel p1 = new JPanel(new GridBagLayout());
	
			GridBagConstraints c1 = new GridBagConstraints(GridBagConstraints.RELATIVE,
					0, 1, 1, 25, 0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
	
			ImageIcon icon = new ImageIcon(data);
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
	
			Dimension d0 = new Dimension(w + 64, h + 2);
			Dimension d1 = new Dimension(60, h);
	
			JLabel label = new JLabel(icon);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			p1.add(label, c1);
	
			final JTextField text = new JTextField();
			text.setPreferredSize(d1);
			text.setEditable(true);
			text.setEnabled(true);
			text.grabFocus();
			p1.add(text, c1);
	
			p1.setPreferredSize(d0);
			p1.setEnabled(true);
	
			codePane.add(p1, GBC_CODE);
			codeFrame.pack();
			if (codePane.getComponentCount() == 1) {
				Dimension d = codeFrame.getSize();
				d.height = 120;
				codeFrame.setPreferredSize(d);
				codeFrame.pack();
			}
			// SwingUtil.setCenter(codeFrame, true, false);
			// codeFrame.setPreferredSize(new Dimension(160, 160));
			SwingUtil.setCenter(codeFrame);
	
			codeFrame.setVisible(true);
			codeFrame.toFront();
			// System.out.println(p1.hashCode() + "@" + codePane.getComponentCount());
	
			text.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == '\n') {
						code.append(text.getText());
						codePane.remove(p1);
						// System.out.println(code.toString() + ": " + codePane.getComponentCount());
						codeFrame.pack();
						if (codePane.getComponentCount() == 0) {
							codeFrame.dispose();
						}
						s.v();
					}
				}
			});
		}

		s.p();
		return code.toString();
	}

}
