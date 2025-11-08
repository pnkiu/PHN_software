package controller;

import dao.CustomerDAO;
import dao.ProductDAO;
import dao.TransactionDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.CustomerModel;
import model.ProductModel;
import view.CarManageView;

public class CarManageController{
    private CarManageView carManageView;
    private ProductDAO dao;
    private CustomerDAO customerDao;
    private TransactionDAO transacionDAO;
    public CarManageController(CarManageView carManageView, ProductDAO dao){
        this.carManageView = carManageView;
        this.customerDao = CustomerDAO.getInstance();
        this.transacionDAO = TransactionDAO.getInstance();
        this.dao = dao;
    }
    public void hienThiXeBanChay(){
        ArrayList<ProductModel> top5 = dao.selectXeBanChayNhat();
        carManageView.hienThiXeBanChayNhat(top5);
    }

    public void thongKeTheoKhoangThoiGian(Date start, Date end) {
        ArrayList<ProductModel> xeBanChay = dao.getXeBanChayNhat(start, end);
        carManageView.hienThiXeBanChayNhat(xeBanChay);

        List<CustomerModel> topKhTheoSoLan = customerDao.getTopKhachHangTheoSoLanMua((java.sql.Date) start, (java.sql.Date) end);

        List<CustomerModel> topKhTheoTien = customerDao.getTopKhachHangTheoTongTien((java.sql.Date) start, (java.sql.Date) end);

        StringBuilder sbSoLan = new StringBuilder();
        for (CustomerModel kh : topKhTheoSoLan) {
            sbSoLan.append(kh.getTenKH())
                    .append(" (")
                    .append(kh.getSoLanMua())
                    .append(" lần)\n");
        }

        StringBuilder sbTien = new StringBuilder();
        for (CustomerModel kh : topKhTheoTien) {
            sbTien.append(kh.getTenKH())
                    .append(" (")
                    .append(String.format("%,.0f VNĐ", kh.getTongChiTieu()))
                    .append(")\n");
        }

        //
        double tongDoanhThu = transacionDAO.getTongDoanhThu(start, end);
        //
        carManageView.capNhatThongKe(sbSoLan.toString(), sbTien.toString(), tongDoanhThu);
    }
}