package test;


import dao.ProductDAO;
import view.CarManageView;
import view.ProductView;

public class test {
    public static void main(String[] args) {
        ProductDAO dao = ProductDAO.getInstance();
        ProductView view = new ProductView();
//        new CarManageView();
    }
}