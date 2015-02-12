package de.planetmetax.wuerc0;

/**
 * @author metax
 *
 */
public class WuercByte {

    private int value;
    
    public static final int UNINITIALIZED = -666666; // Ich hoffe, dieser Wert wird nie erreicht ;-)
    
    public WuercByte() {
        this.value = UNINITIALIZED;
    }
    
    public WuercByte(int value) {
        this();
        initialize(value);
    }
    
    public void initialize(int value) {
        this.value = value;
    }
    
    public int getValue() throws ValueUninitializedException {
        if (this.value == UNINITIALIZED) {
            throw new ValueUninitializedException();
        }
        return value;
    }
    
    public boolean isInitialized() {
        return this.value != UNINITIALIZED;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public static class ValueUninitializedException extends RuntimeException {
        private static final long serialVersionUID = 3047257385917131651L;
        public ValueUninitializedException() {
            super();
        }
        public String toString() {
            return "Es wurde eine Operation auf eine nicht " +
            "initialisierte oder ungültige Stelle im Speicher ausgeführt.";
        }
    }
    
}
