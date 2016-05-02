package edu.aca.dbpmla.lvq.traces;

public class Trace {

    private Long programCounter;
    private int takenNTaken;

    public Long getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(Long programCounter) {
        this.programCounter = programCounter;
    }

    public int getTakenNTaken() {
        return takenNTaken;
    }

    public void setTakenNTaken(int takenNTaken) {
        this.takenNTaken = takenNTaken;
    }

}
