package test;

import controller.CarManageController;
import model.CarManageDAO;
import view.CarManageView;

public class test {
    public static void main(String[] args) {
        // 1. Khởi tạo Model (DAO)
        CarManageDAO dao = new CarManageDAO();

        // 2. Khởi tạo View
        CarManageView view = new CarManageView();

        // 3. Khởi tạo Controller, truyền View và DAO vào
        // Controller sẽ tự động lắng nghe các sự kiện của View
        CarManageController controller = new CarManageController(view, dao);

        // 4. Hiển thị View
        // (Chúng ta sẽ chuyển view.setVisible(true) từ trong View ra đây)
        view.setVisible(true);
    }
}