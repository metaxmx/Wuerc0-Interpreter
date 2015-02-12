package de.planetmetax.wuerc0;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author metax
 *
 */
public class WuercCommand {

    private String commandText;
    private String textMark;
    
    private WuercCodeFragment[] fragments;
    
    public WuercCommand(String command) {
        this.commandText = command;
        this.textMark = null;
        this.parse();
    }
    
    public WuercCommand(String command, String textMark) {
        this(command);
        this.textMark = textMark;
    }
    
    public String getCommandText() {
        return commandText;
    }
    
    public String getTextMark() {
        return textMark;
    }
    
    private void parse() {
        ArrayList<WuercCodeFragment> list = new ArrayList<WuercCodeFragment>();
        
        Pattern markp = Pattern.compile("(\\w+):.*");
        Matcher markm = markp.matcher(this.commandText);
        
        if (markm.matches()) {
            this.textMark = markm.group(1);
            this.commandText = this.commandText.replaceAll("^\\w+:\\s*", "");
        }
        
        this.commandText = this.commandText.replaceAll("//.*$", "");
        this.commandText = this.commandText.replaceAll("^\\s*", "");
        this.commandText = this.commandText.replaceAll("\\s*$", "");
        
        if (false) {
            
        } else {
            list.add(new WuercCodeFragment.WuercCodeError(this.commandText));
        }
        list.trimToSize();
        this.fragments = new WuercCodeFragment[list.size()];
        this.fragments = list.toArray(this.fragments);
    }
    
    public WuercCodeFragment[] getFragments() {
        return fragments;
    }
    
    // Execute Würc0 Command
    public void execute(WuercMemory memory, WuercCode code)
        throws WuercByte.ValueUninitializedException, WuercCode.MarkNotDefinedException {
        code.incProgramCounter();
        
        String[] f = this.commandText.split("( |,)+");
        
        //System.out.println(Arrays.deepToString(f));
        
        int i1 = Integer.MIN_VALUE; // Parameters (as integers)
        int i2 = Integer.MIN_VALUE; // MINVALUE == Not set (for error)
        int i3 = Integer.MIN_VALUE; 
        if (f.length > 1) {
            try {
                i1 = Integer.parseInt(f[1]);
            } catch (NumberFormatException e) { }
        }
        if (f.length > 2) {
            try {
                i2 = Integer.parseInt(f[2]);
            } catch (NumberFormatException e) { }
        }
        if (f.length > 3) {
            try {
                i3 = Integer.parseInt(f[3]);
            } catch (NumberFormatException e) { }
        }
        
        // ------------- Commands ---------------
        
        // #### Ladebefehle
        
        if (f[0].equalsIgnoreCase("LDI")) { // LDI
            if (unset(i1) || unset(i2) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(i2);
            return;
        }
        
        if (f[0].equalsIgnoreCase("L")) { // L
            if (unset(i1) || unset(i2) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue());
            return;
        }
        
        if (f[0].equalsIgnoreCase("LD")) { // LD
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(
                memory.getByte(
                    memory.getByte(i2).getValue() + i3
                    ).getValue()
                );
            return;
        }
        
        if (f[0].equalsIgnoreCase("ST")) { // ST
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(
                memory.getByte(i1).getValue() + i3
                ).setValue(memory.getByte(i2).getValue());
            return;
        }
        
        // #### Arithmetik
        
        if (f[0].equalsIgnoreCase("ADD")) { // ADD
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() + memory.getByte(i3).getValue());
            return;
        }
        
        if (f[0].equalsIgnoreCase("ADDI")) { // ADDI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() + i3);
            return;
        }
        
        if (f[0].equalsIgnoreCase("SUB")) { // SUB
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() - memory.getByte(i3).getValue());
            return;
        }
        
        if (f[0].equalsIgnoreCase("SUBI")) { // SUBI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() - i3);
            return;
        }
        
        if (f[0].equalsIgnoreCase("MUL")) { // MUL
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() * memory.getByte(i3).getValue());
            return;
        }
        
        if (f[0].equalsIgnoreCase("MULI")) { // MULI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() * i3);
            return;
        }
        
        if (f[0].equalsIgnoreCase("DIV")) { // DIV
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() / memory.getByte(i3).getValue());
            return;
        }
        
        if (f[0].equalsIgnoreCase("DIVI")) { // DIVI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i2).getValue() / i3);
            return;
        }
        
        if (f[0].equalsIgnoreCase("INC")) { // INC
            if (unset(i1) || !unset(i2) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i1).getValue() + 1);
            return;
        }
        
        if (f[0].equalsIgnoreCase("DEC")) { // DEC
            if (unset(i1) || !unset(i2) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(memory.getByte(i1).getValue() - 1);
            return;
        }
        
        // #### Vergleiche
        
        if (f[0].equalsIgnoreCase("SEQ")) { // SEQ
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() == memory.getByte(i3).getValue()) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SEQI")) { // SEQI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() == i3) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SNE")) { // SNE
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() != memory.getByte(i3).getValue()) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SNEI")) { // SNEI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() != i3) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SLT")) { // SLT
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() < memory.getByte(i3).getValue()) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SLTI")) { // SLTI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() < i3) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SGT")) { // SGT
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() > memory.getByte(i3).getValue()) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SGTI")) { // SGTI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() > i3) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SLE")) { // SLE
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() <= memory.getByte(i3).getValue()) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SLEI")) { // SLEI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() <= i3) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SGE")) { // SGE
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() >= memory.getByte(i3).getValue()) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("SGEI")) { // SGEI
            if (unset(i1) || unset(i2) || unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i2).getValue() >= i3) {
                memory.getByte(i1).setValue(1);
            } else {
                memory.getByte(i1).setValue(0);
            }
            return;
        }
        
        // #### Verzweigungen
        
        if (f[0].equalsIgnoreCase("BEQZ")) { // BEQZ
            if (unset(i1) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i1).getValue() == 0) {
                if (!unset(i2)) {
                    code.setProgramCounter(i2);
                } else {
                    code.setProgramCounter(f[2]); // Marke
                }
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("BNEZ")) { // BNEZ
            if (unset(i1) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (memory.getByte(i1).getValue() != 0) {
                if (!unset(i2)) {
                    code.setProgramCounter(i2);
                } else {
                    code.setProgramCounter(f[2]); // Marke
                }
            }
            return;
        }
        
        // #### Sprünge
        
        if (f[0].equalsIgnoreCase("JMP")) { // JMP
            if (!unset(i2) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            if (!unset(i1)) {
                code.setProgramCounter(i1);
            } else {
                code.setProgramCounter(f[1]); // Marke
            }
            return;
        }
        
        if (f[0].equalsIgnoreCase("RET")) { // RET
            if (unset(i1) || !unset(i2) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            code.setProgramCounter(memory.getByte(i1).getValue());
            return;
        }
        
        if (f[0].equalsIgnoreCase("JAL")) { // JAL
            if (unset(i1) || !unset(i3)) {
                throw new MissingArgumentsException();
            }
            memory.getByte(i1).setValue(code.getProgramCounter());
            if (!unset(i2)) {
                code.setProgramCounter(i2);
            } else {
                code.setProgramCounter(f[2]); // Marke
            }
            return;
        }
        
        
        // ----------- End Commands -------------
        
        throw new IllegalCommandException();
    }
    
    public static boolean unset(int i) {
        return i == Integer.MIN_VALUE;
    }
    
    public static class IllegalCommandException extends RuntimeException {
        private static final long serialVersionUID = 6724695374022857469L;
        public IllegalCommandException() {
            super();
        }
        public String toString() {
            return "Es wurde eine unbekannte Operation ausgeführt.";
        }
    }
    
    public static class MissingArgumentsException extends RuntimeException {
        private static final long serialVersionUID = 8011358370536995904L;
        public MissingArgumentsException() {
            super();
        }
        public String toString() {
            return "Es wurde eine Operation mit zu wenigen oder zu vielen Argumenten aufgerufen.";
        }
    }
    
}
