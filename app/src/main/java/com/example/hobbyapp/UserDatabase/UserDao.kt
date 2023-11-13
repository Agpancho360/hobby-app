package com.example.hobbyapp.UserDatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @MapInfo(keyColumn="id")
    //Will return all users in database
    @Query("SELECT * FROM users_table order by id ASC")
    fun getUsers(): Flow<Map<Int,User>>

    //inserts a user to Database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    //deletes ALL users to Database
    @Query("DELETE FROM users_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM users_table WHERE id = :id")
    suspend fun getUser(id:Int): User

    //Deletes a certain user from table based on ID
    @Query("DELETE FROM users_table WHERE id=:id")
    suspend fun deleteUser(id: Int)

    //Updates certain photo in database
    @Update
    suspend fun updateUser(user: User)

    //Used for login purposes
    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password:String):User //add a ? at the end if needed
}