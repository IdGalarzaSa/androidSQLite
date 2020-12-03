package com.example.databasesin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Views
    private Button mInsertButton;
    private Button mSearchButton;
    private Button mListButton;

    private EditText mProductCode;
    private EditText mDescription;
    private EditText mSalePrice;
    private EditText mProductList;

    // DataBAse
    private DatabaseHelper mProductDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProductDbHelper = new DatabaseHelper(this);
        initViews();
    }

    private void initViews() {
        mInsertButton = (Button) findViewById(R.id.bt_insert);
        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                cleanFields();
            }
        });
        mSearchButton = (Button) findViewById(R.id.bt_search);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData();
            }
        });
        mListButton = (Button) findViewById(R.id.bt_list);
        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData();
            }
        });

        mProductCode = (EditText) findViewById(R.id.et_productCode);
        mDescription = (EditText) findViewById(R.id.et_description);
        mSalePrice = (EditText) findViewById(R.id.et_salePrice);
        mProductList = (EditText) findViewById(R.id.et_productList);
    }


    private void insertData() {

        String productCode = mProductCode.getText().toString();
        String productDescription = mDescription.getText().toString();
        Double price = Double.valueOf(mSalePrice.getText().toString());

        Product newProduct = new Product(productCode,productDescription,price);

        mProductDbHelper.newProduct(newProduct);
    }

    private void searchData() {
        int productCode = Integer.valueOf(mProductCode.getText().toString());
        Cursor c = mProductDbHelper.getProductByCode(productCode);

        // Validamos si existe ese registro
        if (c.getCount() > 0) {

            // En caso de que exista el registro lo mostramos en los campos de texto
            String code = c.getString(c.getColumnIndex(ProductContract.ProductEntity.CODE));
            String description = c.getString(c.getColumnIndex(ProductContract.ProductEntity.DESCRIPTION));
            String price = c.getString(c.getColumnIndex(ProductContract.ProductEntity.PRICE));

            mProductCode.setText(code);
            mDescription.setText(description);
            mSalePrice.setText(price);

        } else {
            // En caso de que no exista el registro revolvemos un error
            Toast.makeText(this,"No existe ese producto",Toast.LENGTH_LONG).show();
        }
    }

    private void listData() {
        // Limpiamos la lista anterior
        mProductList.setText("");
        List<Product> products = mProductDbHelper.getAllProducts();

        String listText = "";

        for (int i=0; i < products.size(); i++){
            Product product = products.get(i);
            String currentItem = String.valueOf(i+1);
            listText += "- Producto "+ currentItem +" - "+ product.getCode() + " - " + product.getDescription() + " - " + product.getPrice() + "\n";
        }

        mProductList.setText(listText);
    }

    private void cleanFields() {
        mProductCode.setText("");
        mDescription.setText("");
        mSalePrice.setText("");
    }

}