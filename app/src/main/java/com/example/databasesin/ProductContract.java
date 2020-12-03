package com.example.databasesin;

import android.provider.BaseColumns;

public class ProductContract {

    public static abstract class ProductEntity implements BaseColumns {
        public static final String TABLE_NAME ="product";
        public static final String CODE = "id";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
    }
}
