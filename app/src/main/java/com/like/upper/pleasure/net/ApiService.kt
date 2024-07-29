package com.like.upper.pleasure.net


import com.ecuador.mvvm.base.base.BaseResult
import com.like.upper.pleasure.entity.BankAcountInfoItem
import com.like.upper.pleasure.entity.BaseUserInfo
import com.like.upper.pleasure.entity.CodeResult
import com.like.upper.pleasure.entity.DictionaryInfo
import com.like.upper.pleasure.entity.HomeInfo
import com.like.upper.pleasure.entity.ImageInfo
import com.like.upper.pleasure.entity.LoginResult
import com.like.upper.pleasure.entity.ProductInfo
import com.like.upper.pleasure.entity.TryLoanResultInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("/creditoverde/communication/followGladBoot")
    @Headers("classicalAboveCrossroads: true")
    @FormUrlEncoded
    suspend fun sendVerifyCode(@FieldMap data: HashMap<String, String>) : BaseResult<CodeResult?>

    @POST("/creditoverde/communication/insureImmediateSleeve")
    @Headers("classicalAboveCrossroads: true")
    @FormUrlEncoded
    suspend fun login(@FieldMap data: HashMap<String, String>) : BaseResult<LoginResult?>

    @POST("/creditoverde/pot/rollSkillfulSoul")
    @Headers("classicalAboveCrossroads: true")
    @FormUrlEncoded
    suspend fun home(@FieldMap data: HashMap<String, String>) : BaseResult<HomeInfo?>

    @POST("/creditoverde/pot/prepareSmartClerk")
    @Headers("classicalAboveCrossroads: true")
    @FormUrlEncoded
    suspend fun home4(@FieldMap data: HashMap<String, String>) : BaseResult<MutableList<DictionaryInfo>?>

    @POST("/creditoverde/pot/rewindBlankLicense")
    @Headers("classicalAboveCrossroads: true")
    @FormUrlEncoded
    suspend fun getGender(@FieldMap data: HashMap<String, String>) : BaseResult<MutableList<DictionaryInfo>?>

    @POST("/creditoverde/pot/rewindBlankLicense")
    @Headers("classicalAboveCrossroads: true")
    @FormUrlEncoded
    suspend fun getRelation(@FieldMap data: HashMap<String, String>) : BaseResult<MutableList<DictionaryInfo>?>

    @POST("/creditoverde/toughCamel/bringBeautifulToothpaste")
    @FormUrlEncoded
    suspend fun saveBaseInfo(@FieldMap data: HashMap<String, String>) : BaseResult<String?>

    @POST("/creditoverde/toughCamel/layConvenientGrape")
    @FormUrlEncoded
    suspend fun getBaseInfo(@FieldMap data: HashMap<String, String>) : BaseResult<BaseUserInfo?>

    @POST("/creditoverde/toughCamel/picnicForgetfulGuest")
    @FormUrlEncoded
    suspend fun saveBankInfo(@FieldMap data: HashMap<String, String>) : BaseResult<String?>

    @POST("/creditoverde/toughCamel/regretExactMiss")
    @FormUrlEncoded
    suspend fun getBankInfo(@FieldMap data: HashMap<String, String>) : BaseResult<MutableList<BankAcountInfoItem>?>

    @POST("/creditoverde/toughCamel/snakeTiresomePassport")
    @FormUrlEncoded
    suspend fun getImageInfo(@FieldMap data: HashMap<String, String>) : BaseResult<ImageInfo?>

    @POST("/creditoverde/hen/castNewLid")
    @FormUrlEncoded
    suspend fun getProductList(@FieldMap data: HashMap<String, String>) : BaseResult<ProductInfo?>

    @POST("/creditoverde/hen/carveAnotherCamp")
    @FormUrlEncoded
    suspend fun tryLoan(@FieldMap data: HashMap<String, String>) : BaseResult<TryLoanResultInfo?>

    @POST("/creditoverde/pork/raiseUsualAlarm")
    @FormUrlEncoded
    suspend fun preSubmitLoan(@FieldMap data: HashMap<String, String>) : BaseResult<TryLoanResultInfo?>

    @POST("/creditoverde/pork/ridHumanLawyer")
    @FormUrlEncoded
    suspend fun submitLoan(@FieldMap data: HashMap<String, String>) : BaseResult<String?>

    @POST("/creditoverde/toughCamel/plantAgriculturalDrawer")
    @Multipart
    suspend fun uploadImg(@Part file : MultipartBody.Part,
                          @Part("ripeBelgiumEssayNeedle") appId: RequestBody,
                          @Part("uglyReasonClearMeans") userId: RequestBody,
                          @Part("asleepSelfCrazySummerSauce") gps: RequestBody,
                          @Part("flamingTail") es: RequestBody,
                          @Part("spiritualSurroundingLeaf") type: RequestBody,
                          ) : BaseResult<String?>


}