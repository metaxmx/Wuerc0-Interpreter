package de.planetmetax.wuerc0;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * @author metax
 *
 */
public class WuercExecute extends JDialog {

    private static final long serialVersionUID = 6767752218162586664L;
    private WuercMain parent;
    
    private WuercCode code;
    private WuercMemory memory;
    
    private WuercExecute selfLink;

    public WuercExecute(WuercMain parent) {
        super(parent, true);
        this.setTitle("Würc0 Simulator - Ausführung");
        
        this.setResizable(true);
        
        this.parent = parent;
        
        this.selfLink = this;
        
        this.setSize(900, 600);
        this.setLocationRelativeTo(parent);
        
        this.setLayout(new BorderLayout());
        
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Schließen");
        controls.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            } 
        });
        JButton execButton = new JButton("Nächste Anweisung");
        controls.add(execButton);
        execButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                code.execute(memory, selfLink);
            } 
        });
        
        code = new WuercCode(this.parent.getEditor().getText());
        WuercCodeRenderer codeRenderer = new WuercCodeRenderer(code);
        
        memory = new WuercMemory(WuercMain.NUM_REGISTERS, WuercMain.INITIAL_BYTES);
        WuercMemoryRenderer memoryRenderer = new WuercMemoryRenderer(memory);

        
        this.getContentPane().add(controls, BorderLayout.SOUTH);
        this.getContentPane().add(codeRenderer, BorderLayout.WEST);
        this.getContentPane().add(memoryRenderer, BorderLayout.CENTER);
        
    }
    
}
