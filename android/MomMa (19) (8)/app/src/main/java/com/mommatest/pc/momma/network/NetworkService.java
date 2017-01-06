package com.mommatest.pc.momma.network;

import com.mommatest.pc.momma.Model.Bookmark;
import com.mommatest.pc.momma.Model.BookmarkResult;
import com.mommatest.pc.momma.Model.CreateReviewResult;
import com.mommatest.pc.momma.Model.DetailMilkResult;
import com.mommatest.pc.momma.Model.DetailReviewResult;
import com.mommatest.pc.momma.Model.Detailingredient_rankResult;
import com.mommatest.pc.momma.Model.DetailmanufactorResult;
import com.mommatest.pc.momma.Model.IngredientInfoResult;
import com.mommatest.pc.momma.Model.LoginInfo;
import com.mommatest.pc.momma.Model.LoginResult;
import com.mommatest.pc.momma.Model.HomeRank;

import com.mommatest.pc.momma.Model.MyPageEditResult;
import com.mommatest.pc.momma.Model.MyPageInfo;
import com.mommatest.pc.momma.Model.MyPageResult;
import com.mommatest.pc.momma.Model.ReviewBResult;
import com.mommatest.pc.momma.Model.ReviewDetailReadResult;
import com.mommatest.pc.momma.Model.ReviewGResult;
import com.mommatest.pc.momma.Model.ReviewRankResult;
import com.mommatest.pc.momma.Model.ReviewResult;
import com.mommatest.pc.momma.Model.ReviewViewResult;
import com.mommatest.pc.momma.Model.ReviewWriteInfo;
import com.mommatest.pc.momma.Model.SearchRankResult;
import com.mommatest.pc.momma.Model.SearchbarResult;
import com.mommatest.pc.momma.Model.SignUpResult;
import com.mommatest.pc.momma.Model.TabIngredientRankResult;
import com.mommatest.pc.momma.Model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NetworkService {



//    @GET("/posts")
//    Call<MainResult> getMainListData();
//
//
//    @GET("/posts/{id}")
//    Call<DetailResult> getDetailData(@Path("id") int id);
//

    @POST("/sign/up")
    Call<SignUpResult> getSignResult(@Body UserInfo userInfo);

    @POST("/sign/in")
    Call<LoginResult> getLoginResult(@Body LoginInfo loginInfo);

    @GET("/homeview")
    Call<HomeRank> getHomeRank();

    @GET("/mrmilk") // 리뷰가 많은 분유 보기
    Call<ReviewRankResult> getReviewRankList();

    @GET("/msmilk")
    Call<SearchRankResult> getSearchRankList();

    @GET("/detailview/{milk_name}")
    Call<DetailMilkResult> getDetailmilk(@Path("milk_name") String milk_name);

    @GET("/ingredientview/{name}")
    Call<IngredientInfoResult> getIngredientInfoResult(@Path("name") String name);

    @GET("/detailview/ingredient/{milk_name}")
    Call<Detailingredient_rankResult> getDetailingredientrank(@Path("milk_name") String milk_name);

    @POST("/review/post/{milk_name}/{review_writer}")
    Call<ReviewResult> getReviewWrite(@Body ReviewWriteInfo reviewWriteInfo, @Path("milk_name") String milk_name, @Path("review_writer") String review_writer);

    @GET("/review/{milk_name}/{review_id}")
    Call<ReviewDetailReadResult> getReviewDetailReadResult(@Path("milk_name") String milk_name, @Path("review_id") String review_id);

    @GET("/ingre_ranking/{ingre_name}/{stage}")
    Call<TabIngredientRankResult> getTabingredientrankresult (@Path("ingre_name") String ingre_name, @Path("stage") int stage);

    @GET("/mypage/{p_nickname}")
    Call<MyPageResult> getMyPageResult(@Path("p_nickname") String nickname);

    @GET("/review/post/{milk_name}/{review_writer}")
    Call<CreateReviewResult> getcreatereviewresult(@Path("milk_name") String milk_name, @Path("review_writer") String review_writer);

    @GET("/review/{milk_name}/{review_id}/{p_nickname}")
    Call<DetailReviewResult> getDetailReviewResult(@Path("milk_name")String milk_name,@Path("review_id") String review_id, @Path("p_nickname") String p_nickname);

    @POST("/reviewgood/{p_email}/{review_id}")
    Call<ReviewGResult> getReviewGResult (@Path("p_email")String p_email, @Path("review_id") String review_id);

    @POST("/reviewbad/{p_email}/{review_id}")
    Call<ReviewBResult> getReviewBResult (@Path("p_email")String p_email, @Path("review_id") String review_id);

    @GET("/search/{keyword}")
    Call<SearchbarResult> getSearchResult (@Path("keyword") String keyword);

    @GET("/reviewview/{nickname}")
    Call<ReviewViewResult> getReviewViewResult (@Path("nickname") String nickname);

    @GET("/manufactor/{manufactor_name}")
    Call<DetailmanufactorResult> getDetailmanufactorResult (@Path("manufactor_name") String manufactor_name);

    @POST("/mypage/update/{p_nickname}")    // 마이페이지 수정
    Call<MyPageEditResult> getMyPageEditResult(@Path("p_nickname") String nickname, @Body MyPageInfo myPageInfo);

    @POST("/detailview/bookmark/") // 즐겨찾기 추가
    Call<BookmarkResult> getBookmarkResult(@Body Bookmark bookmark);

}
