package controller;

import dao.ProductDAO;
import java.util.ArrayList;
import model.ProductModel;
import view.CarManageView;

public class CarManageController{
    private CarManageView carManageView;
    private ProductDAO dao;
    public CarManageController(CarManageView carManageView, ProductDAO dao){
        this.carManageView = carManageView;
        this.dao = dao;
    }
    public void hienThiXeBanChay(){
        ArrayList<ProductModel> top5 = dao.selectXeBanChayNhat();
        carManageView.hienThiXeBanChayNhat(top5);
    }

}