package org.eclipse.jakarta.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.dao.MachineDao;
import org.eclipse.jakarta.dao.UserDao;
import org.eclipse.jakarta.model.Machine;

import static org.eclipse.jakarta.model.Machine.MachineStatus.DISPONIBLE;
import static org.eclipse.jakarta.model.Machine.MachineStatus.OCCUPE;

@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
    @Inject
    private MachineDao machineDao;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("add")) {
                String name = request.getParameter("name");
                Machine machine = new Machine();
                machine.setNumeroMachine(name);
                machine.setStatus(DISPONIBLE);
                machineDao.addMachine(machine);
            } else if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Machine machine = machineDao.getMachineById(id);
                if (machine != null) {
                    machineDao.deleteMachine(machine);
                }
            } else if (action.equals("reserve")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Machine machine = machineDao.getMachineById(id);
                if (machine != null) {
                    // Changer le statut de la machine ici
                    machine.setStatus(OCCUPE);
                    machineDao.updateMachine(machine);
                }
            }
            else if (action.equals("liberer")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Machine machine = machineDao.getMachineById(id);
                if (machine != null) {
                    machine.setStatus(DISPONIBLE);
                    machineDao.updateMachine(machine);
                }
            }
        }

        List<Machine> machines = machineDao.getAllMachines();
        request.setAttribute("machines", machines);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/welcome.jsp");
        dispatcher.forward(request, response);
    }

}
