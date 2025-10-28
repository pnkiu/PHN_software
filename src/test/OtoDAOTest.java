package test;

import dao.OtoDAO;
import model.Oto;

public class OtoDAOTest {
    public static void main(String[] args) {

        for(int i=0; i <5; i++){
            Oto oto = new Oto("HD"+i,"Honda Civic"+i,500000,"Xe Xang", 5,"xe nay vip","HON", i+1);
            OtoDAO.getInstance().insert(oto);
        }
    }
}
