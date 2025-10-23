package test;

import controller.CarManageController;
import model.CarManageDAO;
import view.CarManageView;

public class test {
    public static void main(String[] args) {
        CarManageDAO dao = CarManageDAO.getInstance();
        CarManageView view = new CarManageView();
        CarManageController controller = new CarManageController(view, dao);
        view.setVisible(true);
    }
}