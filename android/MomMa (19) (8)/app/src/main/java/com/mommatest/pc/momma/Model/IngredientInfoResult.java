package com.mommatest.pc.momma.Model;

import java.util.ArrayList;

public class IngredientInfoResult {
    public ResultData result;

    public class ResultData{
        public IngredientInfo ingredient_info;
        public ArrayList<IngredientRank> rank;

    }
}