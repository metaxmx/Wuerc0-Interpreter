package de.planetmetax.wuerc0;

import java.util.ArrayList;

/**
 * @author metax
 *
 */
public class WuercMemory {

    private int numRegisters;
    
    private WuercByte stackPointer;
    private WuercByte registers[];
    private ArrayList<WuercByte> memory;
    private int maxByte;
    
    public WuercMemory(int numRegisters, int initialBytes) {
        if (numRegisters < 2) {
            throw new IllegalArgumentException("too few registers (" + numRegisters + ")");
        }
        this.numRegisters = numRegisters;
        
        this.stackPointer = new WuercByte(this.numRegisters + 1);
        
        this.registers = new WuercByte[numRegisters];
        for (int i = 0; i < numRegisters; i++) {
            this.registers[i] = new WuercByte();
        }
        
        this.memory = new ArrayList<WuercByte>(initialBytes);
        for (int i = 0; i < initialBytes; i++) {
            this.memory.add(i, new WuercByte());
        }
        this.maxByte = numRegisters + initialBytes;
    }
    
    public int getNumRegisters() {
        return numRegisters;
    }
    
    public int getMaxByte() {
        return this.maxByte;
    }
    
    public WuercByte getByte(int index) throws IndexOutOfBoundsException {
        if (index < 0) {
            throw new IllegalArgumentException("Eine Operation versucht, auf eine ungültige (negative) "
                + "Speicheradresse zuzugreifen.");
        }
        if (index == 0) {
            return stackPointer;
        }
        if (index <= numRegisters) {
            return registers[index - 1];
        }
        int i = index - numRegisters - 1;
        
        while (index > maxByte) {
            maxByte++;
            memory.add(new WuercByte());
        }
        
        return memory.get(i);
    }
    
}
