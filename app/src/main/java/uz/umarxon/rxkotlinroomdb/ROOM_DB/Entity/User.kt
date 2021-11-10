package uz.umarxon.rxkotlinroom.ROOM_DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {

    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    var name:String? = null
    var number:String? = null

}