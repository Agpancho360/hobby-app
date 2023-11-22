package com.example.hobbyapp.UserDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    //sets up Photo Object
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name="image") var image:String,
    @ColumnInfo(name="fname") var fname:String,
    @ColumnInfo(name="lname") var lname:String,
    @ColumnInfo(name="email") var email:String,
    @ColumnInfo(name="password") var password:String
)