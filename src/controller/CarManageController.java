package controller;

import dao.CustomerDAO;
import dao.ProductDAO;
import dao.TransactionDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
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

    public void taiThongKeMacDinh(Date startThang, Date endThang) {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            
            private String sbSoLanText;
            private String sbTienText;
            private double tongDoanhThu;

            @Override
            protected Void doInBackground() throws Exception {
                // 1. Lấy top khách hàng (số lần) - MỌI THỜI ĐẠI
                List<CustomerModel> topKhTheoSoLan = customerDao.getTopKhachHangTheoSoLanMua_AllTime();

                // 2. Lấy top khách hàng (tổng tiền) - MỌI THỜI ĐẠI
                List<CustomerModel> topKhTheoTien = customerDao.getTopKhachHangTheoTongTien_AllTime();

                // 3. Lấy tổng doanh thu - (THEO THÁNG HIỆN TẠI - như cũ)
                tongDoanhThu = transacionDAO.getTongDoanhThu(startThang, endThang);

                // 4. Xử lý chuỗi (giữ nguyên)
                StringBuilder sbSoLan = new StringBuilder();
                if (topKhTheoSoLan.isEmpty()) sbSoLan.append("Không có dữ liệu");
                for (CustomerModel kh : topKhTheoSoLan) {
                    sbSoLan.append(kh.getTenKH()).append(" (").append(kh.getSoLanMua()).append(" lần)\n");
                }
                sbSoLanText = sbSoLan.toString();

                StringBuilder sbTien = new StringBuilder();
                if (topKhTheoTien.isEmpty()) sbTien.append("Không có dữ liệu");
                for (CustomerModel kh : topKhTheoTien) {
                    sbTien.append(kh.getTenKH()).append(" (").append(String.format("%,.0f VNĐ", kh.getTongChiTieu())).append(")\n");
                }
                sbTienText = sbTien.toString();
                
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); 
                    // 5. Cập nhật KẾT QUẢ lên View (giữ nguyên)
                    carManageView.capNhatThongKe(sbSoLanText, sbTienText, tongDoanhThu);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(carManageView, "Lỗi khi tải thống kê.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void thongKeTheoKhoangThoiGian(Date start, Date end) {
    // 1️⃣ Lấy danh sách xe bán chạy nhất trong khoảng
    ArrayList<ProductModel> xeBanChay = dao.getXeBanChayNhat(start, end);
    carManageView.hienThiXeBanChayNhat(xeBanChay);

    // 2️⃣ Lấy top khách hàng theo số lần mua
    List<CustomerModel> topKhTheoSoLan = customerDao.getTopKhachHangTheoSoLanMua(start, end);

    // 3️⃣ Lấy top khách hàng theo tổng chi tiêu
    List<CustomerModel> topKhTheoTien = customerDao.getTopKhachHangTheoTongTien(start, end);

    // 4️⃣ Chuyển danh sách thành chuỗi để hiển thị trên label
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

    // 5️⃣ Lấy tổng doanh thu
    double tongDoanhThu = transacionDAO.getTongDoanhThu(start, end);


        // 5️⃣ Gửi tất cả dữ liệu về View để hiển thị
        carManageView.capNhatThongKe(sbSoLan.toString(), sbTien.toString(), tongDoanhThu);
}
}