package com.sylla.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SwingUtil {

	public static void setCenter(Window window) {
		setCenter(window, true, true);
	}

	public static void setCenter(Window window, boolean X) {
		setCenter(window, X, true);
	}

	public static void setCenter(Window window, boolean X, boolean Y) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension ss = kit.getScreenSize();
		int x = X ? (int) (ss.getWidth() - window.getWidth()) / 2 : 0;
		int y = Y ? (int) (ss.getHeight() - window.getHeight()) / 2 : 0;
		window.setLocation(x, y);
	}

	public static Font getFont(String category) {
		String code = category + ".font.name";
		String name = CommonUtil.getString(code, "Default");
		int style = CommonUtil.getInt(CommonUtil
				.getString(category + ".font.style"), 0);
		int size = CommonUtil.getInt(CommonUtil.getString(category + ".font.size"),
				12);
		return new Font(name, style, size);
	}

	public static void setFont(Container container, Font font) {
		setFont(container, font, true);
	}

	public static void setFont(Container container, Font font, boolean children) {
		if (container == null)
			return;
		container.setFont(font);
		if (children) {
			Component[] comps = container.getComponents();
			for (int i = 0; i < comps.length; i++) {
				if (comps[i] instanceof JMenu) {
					setFont((JMenu) comps[i], font, true);
				} else if (comps[i] instanceof Container) {
					setFont((Container) comps[i], font, true);
				}
			}
		}
	}

	public static void setFont(JMenu menu, Font font, boolean children) {
		if (menu == null)
			return;
		menu.setFont(font);
		if (children) {
			Component[] comps = menu.getMenuComponents();
			for (int i = 0; i < comps.length; i++) {
				if (comps[i] instanceof JMenu) {
					setFont((JMenu) comps[i], font, true);
				} else if (comps[i] instanceof Container) {
					setFont((Container) comps[i], font, true);
				}
			}
		}
	}

	public static Container getAncestor(Container container) {
		Container parent = container;
		while (parent.getParent() != null)
			parent = parent.getParent();
		return parent;
	}

	protected static void addRow(JTable table, Object[] values) {
		if (table == null)
			return;
		TableModel tModel = table.getModel();
		if (tModel instanceof DefaultTableModel) {
			((DefaultTableModel) tModel).addRow(values);
		} else {
			int row = tModel.getRowCount();
			for (int i = 0; i < tModel.getColumnCount(); i++) {
				if (values == null || values.length <= i)
					tModel.setValueAt(null, row, i);
				else
					tModel.setValueAt(values[i], row, i);
			}
		}
	}

	public static void showModalWindow(String title, final Runnable r) {
		final JDialog d = new JDialog((Frame) null, title, true);
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					r.run();
					d.dispose();
				} catch (Exception exp) {
				}
			}
		});
		JLabel l = new JLabel(title);
		l.setForeground(Color.BLUE);
		l.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
		l.setHorizontalAlignment(JLabel.CENTER);
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		p.add(l, BorderLayout.CENTER);
		d.getContentPane().add(p, BorderLayout.CENTER);
		d.setUndecorated(true);
		d.pack();
		d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtil.setCenter(d);
		t.start();
		d.setVisible(true);
	}
}
