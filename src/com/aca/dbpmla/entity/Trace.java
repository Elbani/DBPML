package com.aca.dbpmla.entity;

/**
 * Created by elban on 4/23/16.
 */
public class Trace {
    long microOpCount;
    long instructionAddress;
    long sourceRegister1;
    long sourceRegister2;
    long destinationRegister;
    String conditionRegister;
    String takenNotBranch;
    String loadStore;
    long immediate;
    long addressForMemoryOp;
    long fallthroughPC;
    long targetAddressTakenBranch;
    String macroOperation;
    String microOperation;

    public long getMicroOpCount() {
        return microOpCount;
    }

    public void setMicroOpCount(long microOpCount) {
        this.microOpCount = microOpCount;
    }

    public long getInstructionAddress() {
        return instructionAddress;
    }

    public void setInstructionAddress(long instructionAddress) {
        this.instructionAddress = instructionAddress;
    }

    public long getSourceRegister1() {
        return sourceRegister1;
    }

    public void setSourceRegister1(long sourceRegister1) {
        this.sourceRegister1 = sourceRegister1;
    }

    public long getSourceRegister2() {
        return sourceRegister2;
    }

    public void setSourceRegister2(long sourceRegister2) {
        this.sourceRegister2 = sourceRegister2;
    }

    public long getDestinationRegister() {
        return destinationRegister;
    }

    public void setDestinationRegister(long destinationRegister) {
        this.destinationRegister = destinationRegister;
    }

    public String getConditionRegister() {
        return conditionRegister;
    }

    public void setConditionRegister(String conditionRegister) {
        this.conditionRegister = conditionRegister;
    }

    public String getTakenNotBranch() {
        return takenNotBranch;
    }

    public void setTakenNotBranch(String takenNotBranch) {
        this.takenNotBranch = takenNotBranch;
    }

    public String getLoadStore() {
        return loadStore;
    }

    public void setLoadStore(String loadStore) {
        this.loadStore = loadStore;
    }

    public long getImmediate() {
        return immediate;
    }

    public void setImmediate(long immediate) {
        this.immediate = immediate;
    }

    public long getAddressForMemoryOp() {
        return addressForMemoryOp;
    }

    public void setAddressForMemoryOp(long addressForMemoryOp) {
        this.addressForMemoryOp = addressForMemoryOp;
    }

    public long getFallthroughPC() {
        return fallthroughPC;
    }

    public void setFallthroughPC(long fallthroughPC) {
        this.fallthroughPC = fallthroughPC;
    }

    public long getTargetAddressTakenBranch() {
        return targetAddressTakenBranch;
    }

    public void setTargetAddressTakenBranch(long targetAddressTakenBranch) {
        this.targetAddressTakenBranch = targetAddressTakenBranch;
    }

    public String getMacroOperation() {
        return macroOperation;
    }

    public void setMacroOperation(String macroOperation) {
        this.macroOperation = macroOperation;
    }

    public String getMicroOperation() {
        return microOperation;
    }

    public void setMicroOperation(String microOperation) {
        this.microOperation = microOperation;
    }
}
