package tour.donnees.journal.data.remote.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.*
import tour.donnees.journal.data.remote.response.*


interface JournalService {

    @FormUrlEncoded
    @POST("/token")
    fun token(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<Response<TokenResponse>>

    /**
     * Users Endpoints
     *
     *
     */

    @GET("/users/me")
    fun getUserInfo(
        @Header("Authorization") token: String
    ): Observable<Response<UserInfoResponse>>

    /**
     * Category Endpoints
     *
     *
     */

    @GET("/category")
    fun getAllCategories(
        @Header("Authorization") token: String
    ): Observable<Response<List<CategoryResponse>>>

    @GET("/category/{categoryId}")
    fun getCategoryById(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Int
    ): Observable<Response<CategoryResponse>>

    /**
     * Front Page Endpoints
     *
     *
     */

    @GET("/frontpage")
    fun getFrontPage(
        @Header("Authorization") token: String
    ): Observable<Response<FrontPageWithNewsResponse>>

    @GET("/frontpage/update")
    fun getFrontPageUpdate(
        @Header("Authorization") token: String
    ): Observable<Response<FrontPageResponse>>

    /**
     * News Endpoints
     *
     *
     */

    @GET("/news/category/{categoryId}")
    fun getNewsByCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<Response<List<NewsResponse>>>

    @GET("/news/{articleId}")
    fun getNewsByArticleId(
        @Header("Authorization") token: String,
        @Path("articleId") articleId: Int
    ): Observable<Response<NewsResponse>>

    /**
     * Articles Endpoints
     *
     *
     */

    @GET("/article/{articleId}/paragraphs")
    fun getArticleParagraphs(
        @Header("Authorization") token: String,
        @Path("articleId") articleId: Int
    ): Observable<Response<List<ParagraphResponse>>>



}