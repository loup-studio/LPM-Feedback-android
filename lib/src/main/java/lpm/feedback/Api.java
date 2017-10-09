package lpm.feedback;


import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

interface Api {

    @POST
    @FormUrlEncoded
    Observable<Response<Void>> rate(
            @Url String url,
            @Field("email") String email,
            @Field("score") int score,
            @Field("comment") String comment,
            @Field("os") String os
    );
}