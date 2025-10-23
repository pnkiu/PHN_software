package controller;

import dao.OtoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Oto;
import view.CarManageView;

public class CarManageController implements ActionListener {
        private CarManageView view;
        private OtoDAO dao;
    
        public CarManageController(CarManageView view){
            this.view = view;
            this.dao = OtoDAO.getInstance();
        }
        
        public void hienThiDB(){
            ArrayList<Oto> list = dao.selectAll();
            view.hienThiDuLieu(list);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String button = e.getActionCommand();
            if(button.equals("Xóa")){
                xoaOTO();
            }
        }
         private void xoaOTO() {
            Oto selected = view.getSelectedOto();
            if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn 1 xe để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc muốn xóa xe " + selected.getTenOTO() + " không?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int kq = OtoDAO.getInstance().delete(selected);
            if (kq > 0) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                hienThiDB();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!");
            }
        }
    }
}


