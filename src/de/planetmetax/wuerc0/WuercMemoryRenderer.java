package de.planetmetax.wuerc0;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;;

/**
 * @author metax
 *
 */
public class WuercMemoryRenderer extends JPanel {

    private static final long serialVersionUID = 726908081601142111L;
    
    private static final int BOX_HEIGHT = 25;
    private static final int BOX_WIDTH = 120;
    private static final int BOX_TEXT_REPLACEMENT = 17;
    private static final int BOX_VSPACE = 10;
    private static final int BOX_HSPACE = 30;
    
    private WuercMemory memory;
    
    public WuercMemoryRenderer(WuercMemory memory) {
        this.memory = memory;
        
        this.setBackground(Color.gray);
    }
    

    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
        
        int hpointer = BOX_HSPACE;
        int vpointer = BOX_VSPACE;
        
        g2d.setColor(Color.yellow);
        g2d.drawString("StackPointer:", hpointer, vpointer + BOX_TEXT_REPLACEMENT);
        vpointer += BOX_HEIGHT;
        int[] buffer;
        buffer = drawByte(g2d, memory.getByte(0), hpointer, vpointer, 0);
        hpointer = buffer[0];
        vpointer = buffer[1];
        
        g2d.setColor(Color.blue);
        g2d.drawString("Register:", hpointer, vpointer + BOX_TEXT_REPLACEMENT);
        vpointer += BOX_HEIGHT;
        for (int i = 0; i < memory.getNumRegisters(); i++) {
            buffer = drawByte(g2d, memory.getByte(i + 1), hpointer, vpointer, i + 1);
            hpointer = buffer[0];
            vpointer = buffer[1];
        }
        
        vpointer = BOX_VSPACE;
        hpointer += BOX_WIDTH + BOX_HSPACE;
        
        g2d.setColor(Color.white);
        g2d.drawString("Hauptspeicher:", hpointer, vpointer + BOX_TEXT_REPLACEMENT);
        vpointer += BOX_HEIGHT;
        for (int i = memory.getNumRegisters(); i < memory.getMaxByte(); i++) {
            buffer = drawByte(g2d, memory.getByte(i + 1), hpointer, vpointer, i + 1);
            hpointer = buffer[0];
            vpointer = buffer[1];
        }
        

    }
    
    public int[] drawByte(Graphics2D g, WuercByte b, int hpointer, int vpointer, int memindex) {
        int hbuffer = hpointer;
        int vbuffer = vpointer;
        
        if (vbuffer + BOX_HEIGHT > getHeight()) {
            vbuffer = BOX_VSPACE;
            hbuffer += BOX_WIDTH + BOX_HSPACE;
        }
        g.drawRoundRect(hbuffer, vbuffer, BOX_WIDTH, BOX_HEIGHT, 8, 8);
        
        g.drawString(memindex + "", hbuffer + 10, vbuffer + BOX_TEXT_REPLACEMENT);
        
        g.drawLine(hbuffer + 30, vbuffer + 5, hbuffer + 30, vbuffer + 20);
        
        if (b.isInitialized()) {
            g.drawString(b.getValue() + "", hbuffer + 70, vbuffer + BOX_TEXT_REPLACEMENT);
        } else {
            Color c = g.getColor();
            g.setColor(Color.red);
            g.drawString("undefined", hbuffer + 43, vbuffer + BOX_TEXT_REPLACEMENT);
            g.setColor(c);
        }
        
        vbuffer += BOX_HEIGHT + BOX_VSPACE;
        
        int[] buffer = new int[2];
        buffer[0] = hbuffer;
        buffer[1] = vbuffer;
        return buffer;
    }

}
