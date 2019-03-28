package rans.rankeber;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import io.realm.Realm;
import rans.rankeber.Realm.AturanRealm;
import rans.rankeber.Realm.UserDB;

public class Splashscreen extends AppCompatActivity {

    Realm realm;

    List<AturanRealm> aturanList;

    Boolean logged, inserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent intent;
//                checkAturan();
                checkUser();
                if (logged){
                    intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                } else{
                    intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 3000); //delay 3 detik
    }

//    private void insertData(){
//        realm.beginTransaction();
//
//        AturanRealm dompet = realm.createObject(AturanRealm.class, "1");
//        dompet.setNamaAturan("Voucher PUBG");
//        dompet.setHargaAturan("25000");
//        dompet.setKategori(1);    //1=bag
//        dompet.setPambayaran(3);  //1=transfer 2=cod 3=both
//        dompet.setRefund(0);
//
//        AturanRealm sambal = realm.createObject(AturanRealm.class, "2");
//        sambal.setNamaAturan("Steam Wallet");
//        sambal.setHargaAturan("35000");
//        sambal.setKategori(2);    //2==food
//        sambal.setPambayaran(3);
//        sambal.setRefund(0);
//
//        AturanRealm kopi = realm.createObject(AturanRealm.class, "3");
//        kopi.setNamaAturan("Humbang Arabica Coffee");
//        kopi.setHargaAturan("65000");
//        kopi.setKategori(2);  //2=food
//        kopi.setPambayaran(3);
//        kopi.setRefund(1);
//
//        AturanRealm tote = realm.createObject(AturanRealm.class, "4");
//        tote.setNamaAturan("Tote Bag");
//        tote.setHargaAturan("110000");
//        tote.setKategori(1);  //1=bag
//        tote.setPambayaran(1);
//        tote.setRefund(1);
//
//        AturanRealm buku = realm.createObject(AturanRealm.class, "5");
//        buku.setNamaAturan("Novel Back to Ubud");
//        buku.setHargaAturan("112500");
//        buku.setKategori(3);  //3=buku
//        buku.setPambayaran(2);
//        buku.setRefund(1);
////
////        AturanRealm dress1 = realm.createObject(AturanRealm.class, "6");
////        dress1.setNamaAturan("Long Dress Hitam Batik");
////        dress1.setHargaAturan("250000");
////        dress1.setKategori(4);  //4=dress
////        dress1.setPambayaran(2);
////        dress1.setRefund(0);
////
////        AturanRealm talenan = realm.createObject(AturanRealm.class, "7");
////        talenan.setNamaAturan("Talenan");
////        talenan.setHargaAturan("30000");
////        talenan.setKategori(7);  //6=other
////        talenan.setPambayaran(2);
////        talenan.setRefund(0);
////
////        AturanRealm tshirt = realm.createObject(AturanRealm.class, "8");
////        tshirt.setNamaAturan("Kemeja Hijau Batik Tenun");
////        tshirt.setHargaAturan("550000");
////        tshirt.setKategori(5);  //5=tshirt
////        tshirt.setPambayaran(3);
////        tshirt.setRefund(1);
////
////        AturanRealm dress2 = realm.createObject(AturanRealm.class, "9");
////        dress2.setNamaAturan("Long Dress Merah Batik");
////        dress2.setHargaAturan("400000");
////        dress2.setKategori(4);  //4=dress
////        dress2.setPambayaran(1);
////        dress2.setRefund(1);
////
////        AturanRealm ulos = realm.createObject(AturanRealm.class, "10");
////        ulos.setNamaAturan("Ulos Batak");
////        ulos.setHargaAturan("250000");
////        ulos.setKategori(6);  //6=ulos
////        ulos.setPambayaran(2);
////        ulos.setRefund(0);
//
//        realm.commitTransaction();
//        finish();
//    }

    public boolean checkUser(){
        realm.beginTransaction();
        UserDB user = realm.where(UserDB.class).findFirst();
        realm.commitTransaction();

        logged = user != null;

        return logged;
    }

//    public void checkAturan(){
//        realm.beginTransaction();
//        aturanList = realm.where(AturanRealm.class).findAll();
//        realm.commitTransaction();
//
//        if (aturanList.size() == 0)
//            insertData();
//    }
}
