package com.like.upper.pleasure.entity

data class UserInfo(
    var birthday : String?,
    var firstName : String?,
    var lastName : String?,
    var userId : String?,
    var token : String?,
    var avator : String?,
    var sex : Int?,
    var type : Int?, //type (0 email login)  (1 apple login) (2 google login)
    var email : String?,
    var flag : String? // 0 need to set pwd 1 not need set pwd
)
