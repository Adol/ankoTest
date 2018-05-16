package com.ankotest.adol.pickertest.aacwithroom

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import android.content.Context
import com.ankotest.adol.pickertest.api.pln
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class SignUP(val signUp: MutableMap<String, Array<Int>>)
/*Array [a,b,c]
 a - 按鈕類型  0 用餐 1 上課
 b - 確認
 //c - 預先報名狀況暫停
*/

@Entity
data class SignUpTable(@PrimaryKey(autoGenerate = true)
                       @ColumnInfo(name = "id") var id: Int = 0,
                       @ColumnInfo(name = "month") var month: Int,
                       @ColumnInfo(name = "Type") var Type: String,
                       @ColumnInfo(name = "sex") var sex: String,
                       @ColumnInfo(name = "name") var name: String,
                       @ColumnInfo(name = "userData") var userData: SignUP
)

@Dao
interface SignUpDao {
    @Insert
    fun insertOne(insert: SignUpTable)

    @Insert
    fun insertMatch(list: List<SignUpTable>)

    @Update
    fun upDateSign(up: SignUpTable)

    @Query("select * from SignUpTable")
    fun getAll(): List<SignUpTable>

    @Query("select * from SignUpTable where Type = :stype")
    fun getSignUp(stype:String): List<SignUpTable>

    @Query("DELETE FROM SignUpTable")
    fun deleteAll()
}

class SignUpRepository(ctx: Context) {
    private val mWordDao: SignUpDao

    init {
        val db = AppDataBase.getInstance(ctx)
        mWordDao = db!!.signUpDao()
    }

    fun getAll(): MutableLiveData<List<SignUpTable>> {
        val data = MutableLiveData<List<SignUpTable>>()
        data.postValue(mWordDao.getAll())
        return data
    }

    fun getSignUp(type: String): MutableLiveData<List<SignUpTable>> {
        val data = MutableLiveData<List<SignUpTable>>()
        data.postValue(mWordDao.getSignUp(type))
        return data
//        return mWordDao.selectKotlin()
    }

    fun insert(signUp: SignUpTable) {
        mWordDao.insertOne(signUp)
    }

    fun upDate(signUp: SignUpTable) {
        signUp.pln()
        mWordDao.upDateSign(signUp)
    }

    fun delall() {
        mWordDao.deleteAll()
    }
}

class DataConverter {
    private val gson = Gson()
    private val type = object : TypeToken<SignUP>() {}.type

    @TypeConverter
    fun toJson(signUP: SignUP): String {
        return gson.toJson(signUP)
    }

    @TypeConverter
    fun toList(ValuesString: String): SignUP {
        return gson.fromJson<SignUP>(ValuesString, type)
    }
}

@Database(entities = arrayOf(SignUpTable::class), version = 2)
@TypeConverters(DataConverter::class)//特定類型＿輸出輸入時自動轉換
abstract class AppDataBase : RoomDatabase() {
    abstract fun signUpDao(): SignUpDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        internal fun getInstance(context: Context): AppDataBase? {
            // synchronized 鎖定物件
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context.applicationContext,
                    AppDataBase::class.java, "SignUp_database")
                    .build()
        }
    }
}
