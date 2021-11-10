package uz.umarxon.rxkotlinroom.ROOM_DB.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.umarxon.rxkotlinroom.ROOM_DB.Dao.UserDao
import uz.umarxon.rxkotlinroom.ROOM_DB.Entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao():UserDao

    companion object{
        private var instanse:AppDatabase?= null

        @Synchronized
        fun getInstance(context: Context):AppDatabase{

            when(instanse){
                null->{
                    instanse = Room.databaseBuilder(context,AppDatabase::class.java,"user_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return instanse!!
        }

    }
}