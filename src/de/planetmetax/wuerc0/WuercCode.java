package de.planetmetax.wuerc0;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

/**
 * @author metax
 *
 */
public class WuercCode {

    private ArrayList<WuercCommand> lines;
    
    private int programCounter;
    private int lastExecuted;
    
    public WuercCode(String code) {
        this.lines = new ArrayList<WuercCommand>();
        this.programCounter = 0;
        this.lastExecuted = -1;
        parse(code);
    }
    
    private void parse(String code) {
        StringTokenizer tk = new StringTokenizer(code,"\r\n");
        while (tk.hasMoreTokens()) {
            String line = tk.nextToken();
            if (!line.matches("^\\s*") && !line.matches("^//.*") && !line.matches("^--.*")) {
                WuercCommand c = new WuercCommand(line);
                lines.add(c);
            }
        }
    }
    
    public int getProgramCounter() {
        return programCounter;
    }
    
    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }
    
    public void setProgramCounter(String marker) throws MarkNotDefinedException {
        for (int i = 0; i < lines.size(); i++) {
            if (marker.equalsIgnoreCase(lines.get(i).getTextMark())) {
                this.programCounter = i;
                return;
            }
        }
        throw new MarkNotDefinedException();
    }
    
    public void incProgramCounter() {
        this.programCounter++;
    }
    
    public ArrayList<WuercCommand> getLines() {
        return this.lines;
    }
    
    public int getLastExecuted() {
        return lastExecuted;
    }
    
    public void execute(WuercMemory memory, WuercExecute parent) {
        this.lastExecuted = this.programCounter;
        try {
            lines.get(programCounter).execute(memory, this);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(parent,
                "Der ProgramCounter zeigt auf eine nicht existente Adresse.",
                "Fehler im Code", JOptionPane.ERROR_MESSAGE);
        } catch (WuercByte.ValueUninitializedException e) {
            JOptionPane.showMessageDialog(parent, e.toString(),
                "Fehler im Code", JOptionPane.ERROR_MESSAGE);
        } catch (WuercCommand.IllegalCommandException e) {
            JOptionPane.showMessageDialog(parent, e.toString(),
                "Fehler im Code", JOptionPane.ERROR_MESSAGE);
        } catch (WuercCommand.MissingArgumentsException e) {
            JOptionPane.showMessageDialog(parent, e.toString(),
                "Fehler im Code", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage(),
                "Fehler im Code", JOptionPane.ERROR_MESSAGE);
        } catch (MarkNotDefinedException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage(),
                "Fehler im Code", JOptionPane.ERROR_MESSAGE);
        }
        parent.repaint();
    }
    
    public static class MarkNotDefinedException extends RuntimeException {
        private static final long serialVersionUID = -3377529506840106037L;
        public MarkNotDefinedException() {
            super();
        }
        public String toString() {
            return "Es wurde eine Sprungmarke referenziert, die nicht existiert";
        }
    }
}
