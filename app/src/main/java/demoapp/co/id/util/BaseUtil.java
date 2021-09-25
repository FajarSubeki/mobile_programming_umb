package demoapp.co.id.util;

public class BaseUtil {

    // function untuk menghitung luas lingkaran dengan parameter jari-jari
    public static double luasLingkaran(int jari){
        // initial variable luas dan nilai phi
        double luas;
        double phi = 3.14;

        // rumus ruas lingkaran
        luas = phi*jari*jari;

        // return / mengembalikan luas
        return luas;
    }


}
