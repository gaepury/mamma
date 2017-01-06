package com.mommatest.pc.momma.Model;

import java.util.ArrayList;

public class ReviewViewResult {

    public ResultData result;

    public class ResultData{
        public ReviewViewSimilar similar;
        public ArrayList<ReviewViewRank> reviewrank;
    }
}

