package com.example.hobbyapp.UserDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hobbyapp.R

@Entity(tableName = "users_table")
data class User(
    //sets up Photo Object
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name="image") var image:String,
    @ColumnInfo(name="fname") var fname:String,
    @ColumnInfo(name="lname") var lname:String,
    @ColumnInfo(name="email") var email:String,
    @ColumnInfo(name="password") var password:String,
    //added attributes (hobbies)
    @ColumnInfo(name="swimming") var swimming:Boolean,
    @ColumnInfo(name="drawing") var drawing:Boolean,
    @ColumnInfo(name="gardening") var gardening:Boolean,
    @ColumnInfo(name="coding") var coding:Boolean,
    @ColumnInfo(name="traveling") var traveling:Boolean,
    @ColumnInfo(name="photography") var photography:Boolean,
    @ColumnInfo(name="painting") var painting:Boolean,
    @ColumnInfo(name="music") var music:Boolean,
    @ColumnInfo(name="writing") var writing:Boolean,
    @ColumnInfo(name="running") var running:Boolean,
    @ColumnInfo(name="cooking") var cooking:Boolean,
)