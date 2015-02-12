package de.planetmetax.wuerc0;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * @author metax
 *
 */
public class WuercMain extends JFrame {

    private static final long serialVersionUID = -2606650896147691001L;
    
    public static final int NUM_REGISTERS = 8;
    public static final int INITIAL_BYTES = 8;
    
    private JEditorPane editor;
    
    private WuercMain self;
    
    public WuercMain() {
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.setTitle("Würc0 Simulator");
        
        this.setLayout(new BorderLayout(6,6));
        
        // Elements
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);
        JMenu menu_programm = new JMenu("Programm");
        JMenuItem menu_programm_exit = new JMenuItem("Beenden");
        menu_programm_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenuItem menu_programm_start = new JMenuItem("Simulation starten");
        this.self = this;
        menu_programm_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WuercExecute execution = new WuercExecute(self);
                execution.setVisible(true);
            }
        });
        menu_programm.add(menu_programm_start);
        menu_programm.addSeparator();
        menu_programm.add(menu_programm_exit);
        menubar.add(menu_programm);
        
        editor = new JEditorPane("text/plain", "ABC");
        editor.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.getContentPane().add(editor, BorderLayout.CENTER);
        
        JPanel controls = new JPanel();
        JButton start = new JButton("Simulation starten");
        start.addActionListener(menu_programm_start.getActionListeners()[0]);
        JButton exit = new JButton("Beenden");
        exit.addActionListener(menu_programm_exit.getActionListeners()[0]);
        JButton clear = new JButton("Leeren");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.setText("// WÜRC0 Code hier eingeben:\n\n");
            }
        });
        controls.setLayout(new FlowLayout(FlowLayout.CENTER));
        controls.add(clear);
        controls.add(exit);
        controls.add(start);
        
        this.getContentPane().add(controls, BorderLayout.SOUTH);
        
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        
        editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        editor.setText("// Beispiel-Würc0-Programm\n"
            + "// Diese Anwendung wird nach dem Hornerschema das Polynom "
            + "3x^3 + 4x^2 + 5x - 7 an der Stelle x = 7 berechnen.\n"
            + "// Das Ergebnis sollte 1253 sein.\n\n"
            + "-- Koeffizienten und X in die Register laden:\n\n"
            + "load: LDI 1, 7\nLDI 2, -7\nLDI 3, 5\nLDI 4, 4\nLDI 5, 3\n\n"
            + "-- Start der Berechnung:\n\n"
            + "calc: MUL 6, 1, 5\nADD 6, 6, 4\nMUL 6, 6, 1\nADD 6, 6, 3\n"
            + "MUL 6, 6, 1\nADD 6, 6, 2\n\n"
            + "-- Kopiere das Ergebnis nach Register 1\n\n"
            + "ready: L 1, 6\n\n"
            + "// Viel Spaß beim selbst ausprobieren ;-)\n"
            + "// Du musst nur auf den Leeren-Knopf unten drücken ...");
        
        this.setVisible(true);
    }
    
    public JEditorPane getEditor() {
        return editor;
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new WuercMain();
    }

}
