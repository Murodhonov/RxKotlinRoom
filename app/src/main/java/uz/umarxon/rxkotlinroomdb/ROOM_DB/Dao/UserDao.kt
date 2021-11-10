package uz.umarxon.rxkotlinroom.ROOM_DB.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import uz.umarxon.rxkotlinroom.ROOM_DB.Entity.User

@Dao
interface UserDao {

    @Insert
    fun addUser(user: User): Single<Long>

    @Query("select * from user")
    fun getAllUser(): Flowable<List<User>>

    @Query("select * from user where id=:id")
    fun getUserById(id:Int):Maybe<User>

    @Delete
    fun deleteUser(user: User)

}