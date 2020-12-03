package com.example.databasesin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "products.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createDataBase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    /*=====================================
        Metodos para la base de datos
     =====================================*/

    private void createDataBase(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + ProductContract.ProductEntity.TABLE_NAME + " ("
                + ProductContract.ProductEntity.CODE + " INTEGER NOT NULL,"  // Product code
                + ProductContract.ProductEntity.DESCRIPTION + " TEXT NOT NULL,"
                + ProductContract.ProductEntity.PRICE + " TEXT NOT NULL,"
                + "UNIQUE (" + ProductContract.ProductEntity.CODE + "))");
    }

    public long newProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntity.CODE, product.getCode());
        values.put(ProductContract.ProductEntity.DESCRIPTION, product.getDescription());
        values.put(ProductContract.ProductEntity.PRICE, product.getPrice());

        return sqLiteDatabase.insert( ProductContract.ProductEntity.TABLE_NAME,null, values);
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        Cursor c =  getReadableDatabase().query(
                        ProductContract.ProductEntity.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        while(c.moveToNext()){
            String code = c.getString(c.getColumnIndex(ProductContract.ProductEntity.CODE));
            String description = c.getString(c.getColumnIndex(ProductContract.ProductEntity.DESCRIPTION));
            String price = c.getString(c.getColumnIndex(ProductContract.ProductEntity.PRICE));

            Double priceAsDouble = Double.parseDouble(price);

            Product product = new Product(code, description, priceAsDouble);
            productList.add(product);
        }

        return productList;
    }

    public Cursor getProductByCode(int productCode) {

        String paramToSearch = String.valueOf(productCode);

        Cursor c = getReadableDatabase().query(
                ProductContract.ProductEntity.TABLE_NAME,
                null,
                ProductContract.ProductEntity.CODE + " LIKE ?",
                new String[]{paramToSearch},
                null,
                null,
                null);
        return c;
    }

    public void deleteAllProducts() {
        getReadableDatabase().delete(
                ProductContract.ProductEntity.TABLE_NAME,
                null,
                null);
    }


}
