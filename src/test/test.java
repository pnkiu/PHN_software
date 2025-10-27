package test;

import view.MainApplicationFrame;
import model.CarManageDAO;
//import view.CarManageView;


public class test {
    public static void main(String[] args) {
        CarManageDAO dao = CarManageDAO.getInstance();
        MainApplicationFrame viewFrm = new MainApplicationFrame();
        viewFrm.setVisible(true);

    }
}