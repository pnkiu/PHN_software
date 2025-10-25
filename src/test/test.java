package test;


import model.CarManageDAO;
import view.CarManageView;
import view.ThongKeView;

public class test {
    public static void main(String[] args) {
        CarManageDAO dao = CarManageDAO.getInstance();
//        CarManageView view = new CarManageView();
        ThongKeView view = new ThongKeView();
        view.setVisible(true);
    }
}