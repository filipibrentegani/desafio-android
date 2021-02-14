package com.picpay.desafio.android

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    @ColumnInfo(name = "img") @SerializedName("img") val img: String?,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String?,
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "username") @SerializedName("username") val username: String?
) : Parcelable