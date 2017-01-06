package com.mommatest.pc.momma.Model;

import java.util.ArrayList;

public class DetailMilkResult {

    public ResultData result;

    public class ResultData {
        public DetailInfo_milk milk;
        public ArrayList<String> ingredient;
        public ArrayList<DetailInfo_review> review;
        }

}


