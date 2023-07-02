package org.eclipse.jakarta.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String numeroMachine;
    private MachineStatus status;

    public Machine() {
    }

    public Machine(String numeroMachine, MachineStatus status) {
        this.numeroMachine = numeroMachine;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getNumeroMachine() {
        return numeroMachine;
    }

    public void setNumeroMachine(String numeroMachine) {
        this.numeroMachine = numeroMachine;
    }

    public MachineStatus getStatus() {
        return status;
    }

    public void setStatus(MachineStatus status) {
        this.status = status;
    }
    public enum MachineStatus {
        DISPONIBLE,
        OCCUPE;
    }
}
