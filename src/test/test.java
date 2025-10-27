package test;

import view.MainApplicationFrame;
import view.ProductManagementPanel;
import model.CarManageDAO;
import view.CarManageView;


public class test {
    public static void main(String[] args) {
        CarManageDAO dao = CarManageDAO.getInstance();
        CarManageView view = new CarManageView();
        MainApplicationFrame viewFrm = new MainApplicationFrame();
        viewFrm.setVisible(true);
        ProductManagementPanel panel = new ProductManagementPanel();

    }
}