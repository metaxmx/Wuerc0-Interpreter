package de.planetmetax.wuerc0;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * @author metax
 *
 */
public class WuercCodeRenderer extends JPanel {

    private static final long serialVersionUID = 8352279452594453652L;
    private WuercCode code;
    
    public WuercCodeRenderer(WuercCode c) {
        this.code = c;
        
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(200,1));
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
        
        
        
        int vpointer = 60;
        int lcounter = 0;
        int stdhpointer = 0;
        
        int maxBlockWidth = 0;
        int maxElementWidth = 200;
        
        for (WuercCommand cmd : code.getLines()) {
            int hpointer = stdhpointer + 30;
            if (code.getProgramCounter() == lcounter) {
                g2d.setColor(Color.yellow);
                g2d.fillRect(stdhpointer + 10, vpointer, 10, 20);
            }

            if (cmd.getTextMark() == null) {
                g2d.setColor(Color.cyan);
                hpointer = drawString(g2d, lcounter + " ", hpointer, vpointer);
                g2d.setColor(Color.white);
                hpointer = drawString(g2d, ": ", hpointer, vpointer);
            } else {
                g2d.setColor(Color.magenta);
                hpointer = drawString(g2d, cmd.getTextMark() + " ", hpointer, vpointer);
                g2d.setColor(Color.white);
                hpointer = drawString(g2d, ": ", hpointer, vpointer);
            }
            
            for (WuercCodeFragment cf : cmd.getFragments()) {
                g2d.setColor(cf.getFragmentColor());
                hpointer = drawString(g2d, cf.toString() + " ", hpointer, vpointer);
            }
            
            if (code.getLastExecuted() == lcounter) {
                g2d.setColor(Color.yellow);
                g2d.drawRect(stdhpointer + 25, vpointer, hpointer - stdhpointer - 25, 20);
            }
            
            if (maxBlockWidth < hpointer) {
                maxBlockWidth = hpointer;
                maxElementWidth = Math.max(maxElementWidth, hpointer);
            }
            vpointer += 20;
            lcounter++;
            if (vpointer + 20 > this.getHeight()) {
                g2d.setColor(Color.gray);
                g2d.drawLine(maxBlockWidth, 70, maxBlockWidth, this.getHeight() - 10);
                vpointer = 60;
                stdhpointer = maxBlockWidth;
            }
        }
        
        g2d.setColor(Color.yellow);
        g2d.drawRoundRect(10, 10, maxElementWidth - 20, 30, 8, 8);
        String counter = "ProgramCounter: " + code.getProgramCounter();
        g2d.drawString(counter,(maxElementWidth - g.getFontMetrics().stringWidth(counter)) / 2, 30);
        
        if (this.getWidth() != maxElementWidth) {
            this.setPreferredSize(new Dimension(maxElementWidth, this.getHeight()));
            this.getParent().getLayout().layoutContainer(this.getParent());
        }

    }
    
    private static int drawString(Graphics2D g, String text, int hpointer, int vpointer) {
        int size = g.getFontMetrics().stringWidth(text);
        g.drawString(text, hpointer, vpointer + 14);
        return hpointer + size;
    }
    
}
