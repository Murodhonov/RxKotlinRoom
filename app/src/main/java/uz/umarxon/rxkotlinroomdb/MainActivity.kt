package uz.umarxon.rxkotlinroomdb

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.umarxon.rxkotlinroom.Adapter.RvAdapter
import uz.umarxon.rxkotlinroom.ROOM_DB.DataBase.AppDatabase
import uz.umarxon.rxkotlinroom.ROOM_DB.Entity.User
import uz.umarxon.rxkotlinroomdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var appDatabase: AppDatabase
    lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)
        rvAdapter = RvAdapter(object : RvAdapter.OnItemCLickListener {
            override fun onItemClick(user: User) {
                appDatabase.userDao().deleteUser(user)
            }
        })

        appDatabase.userDao().getAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<User>>,
                io.reactivex.functions.Consumer<List<User>> {
                override fun accept(t: List<User>) {
                    rvAdapter.submitList(t)
                }
            })

        binding.rv.adapter = rvAdapter


        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val number = binding.etNumber.text.toString().trim()

            if (name.isNotEmpty() && number.isNotEmpty()){
                val user = User()

                user.name = name
                user.number = number

                appDatabase.userDao().addUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{t->
                        Log.d("Single", "onCreate: $t")
                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                    }
            }else{
                Toast.makeText(this, "Malumotlar yetarli emas", Toast.LENGTH_SHORT).show()
            }

        }
    }
}