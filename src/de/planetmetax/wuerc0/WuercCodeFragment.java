package de.planetmetax.wuerc0;

import java.awt.Color;

/**
 * @author metax
 *
 */
public abstract class WuercCodeFragment {
    
    public Color getFragmentColor() {
        return Color.white;
    }
    
    public abstract String stringValue();
    public abstract int intValue();
    
    public String toString() {
        return stringValue();
    }
    
    public static class WuercCodeCommand extends WuercCodeFragment {
        private String cmd;
        public WuercCodeCommand(String command) {
            this.cmd = command;
        }
        // Dummy
        public int intValue() {
            return 0;
        }
        public String stringValue() {
            return this.cmd;
        }
    }
    
    public static class WuercCodeError extends WuercCodeFragment {
        private String cmd;
        public Color getFragmentColor() {
            return Color.red;
        }
        public WuercCodeError(String command) {
            this.cmd = command;
        }
        // Dummy
        public int intValue() {
            return 0;
        }
        public String stringValue() {
            return this.cmd;
        }
    }
}
