package exercicios.aprendendo_jframe;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;

public class View {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(700, 700);
        window.getContentPane().setBackground(new Color(200, 30, 50));
        window.setLocationRelativeTo(null);
        JLabel label = new JLabel("Hello world!", JLabel.CENTER);
        label.setFont(new Font("Calibri", Font.BOLD, 40));
        label.setForeground(Color.white);

        JTextField textField = new JTextField("Enter text here", 20);
        label.add(textField);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Arquivo");
        JMenu editMenu = new JMenu("Editar");
        JMenu formatMenu = new JMenu("formatar");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        // 4. Create JMenuItem objects for File menu
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");

        // 5. Add JMenuItem objects to File menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator(); // Add a separator
        fileMenu.add(exitItem);

        // 4. Create JMenuItem objects for Edit menu
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");

        // 5. Add JMenuItem objects to Edit menu
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        window.setJMenuBar(menuBar);

        window.add(label);
        window.setVisible(true);
    }
}
