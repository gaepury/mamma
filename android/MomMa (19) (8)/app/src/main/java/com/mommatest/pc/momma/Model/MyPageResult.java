package com.mommatest.pc.momma.Model;

import java.util.ArrayList;

public class MyPageResult {

    public ResultData result;

  public class ResultData{
        public MyPageParent parent;
        public MyPageBaby baby;
        public ArrayList<MyPageMyReview> myreview;
        public ArrayList<MyPageBookMark> bookmark;
    }
}