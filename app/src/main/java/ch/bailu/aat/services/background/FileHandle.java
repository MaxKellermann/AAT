package ch.bailu.aat.services.background;

import ch.bailu.simpleio.foc.Foc;

public abstract class FileHandle extends ProcessHandle {
    private final Foc file;
    
    
    
    public FileHandle(Foc f) {
        file = f;
    }
    

    @Override 
    public String toString() {
        return file.toString();
    }

    public Foc getFile() {
        return file;
    }
}
