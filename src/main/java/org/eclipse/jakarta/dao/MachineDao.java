package org.eclipse.jakarta.dao;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.eclipse.jakarta.model.Machine;

import java.util.List;

@ApplicationScoped
public class MachineDao {
    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager entityManager;
    @Transactional
    public void addMachine(Machine machine) {
        entityManager.persist(machine);
    }

    public List<Machine> getAllMachines() {
        return entityManager.createQuery("SELECT m FROM Machine m", Machine.class).getResultList();
    }

    public Machine getMachineById(int id) {
        return entityManager.find(Machine.class, id);
    }
@Transactional
    public void updateMachine(Machine machine) {
        entityManager.merge(machine);
    }
    @Transactional
    public void deleteMachine(Machine machine) {
        Machine mergedMachine = entityManager.merge(machine);
        entityManager.remove(mergedMachine);
    }

}
