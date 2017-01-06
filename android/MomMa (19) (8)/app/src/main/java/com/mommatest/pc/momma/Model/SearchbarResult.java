package com.mommatest.pc.momma.Model;

import java.util.ArrayList;

public class SearchbarResult {

    public ResultData result;
    public class ResultData{
        public ArrayList<Searchmanufactor> manufactor;
        public ArrayList<Searchmilk> drymilk;
        public ArrayList<Searchingredient> ingredient;
    }
}
