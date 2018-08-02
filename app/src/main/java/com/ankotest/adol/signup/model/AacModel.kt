package com.ankotest.adol.signup.model

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class courseSignUP(val course: String, val status: MutableList<Int>)
/*status  MutableList<Int> ->
 [a,b,c]
 a - 按鈕類型  0 ~ 99 上課  101 ~ 用餐
 b - 確認
 //c - 預先報名狀況暫停
*/

@Entity
data class SignUpTable(@PrimaryKey(autoGenerate = true)
                       @ColumnInfo(name = "id") var id: Int = 0,
                       @ColumnInfo(name = "month") var month: Int,
                       @ColumnInfo(name = "identity") var identity: String,
                       @ColumnInfo(name = "sex") var sex: String,
                       @ColumnInfo(name = "name") var name: String,
                       @ColumnInfo(name = "course") var course: List<courseSignUP>
)

@Dao
interface SignUpDao {
    @Insert
    fun insertOne(insert: SignUpTable)

    @Insert
    fun insertMatch(list: List<SignUpTable>)

    @Update
    fun upDateSign(up: SignUpTable)

    @Query("select * from SignUpTable where identity = :identity AND month = :mt")
    fun getSignUp(identity: String,mt: Int): List<SignUpTable>

    @Query("select * from SignUpTable where month = :setMonth")
    fun getAll(setMonth: Int): List<SignUpTable>

//    @Query("select * from SignUpTable ")
//    fun getTotal(mt: Int): List<SignUpTable>

    @Query("DELETE FROM SignUpTable")
    fun deleteAll()
}

class SignUpRepository(ctx: Context) {
    private val mWordDao: SignUpDao

    init {
        val db = AppDataBase.getInstance(ctx)
        mWordDao = db!!.signUpDao()
    }

    fun insert(signUp: SignUpTable) {
        mWordDao.insertOne(signUp)
    }

    fun upDate(signUp: SignUpTable) {
        mWordDao.upDateSign(signUp)
    }

    fun getSignUp(identity: String, mt: Int = 4 ): MutableLiveData<List<SignUpTable>> {
        val data = MutableLiveData<List<SignUpTable>>()
        data.postValue(mWordDao.getSignUp(identity,mt))
        return data
    }

    fun getAll(setMonth: Int ): List<SignUpTable> {
        return mWordDao.getAll(setMonth)
//        Gson().toJson(mWordDao.getAll()).pln()
    }

//    fun deleteAll() {
//        mWordDao.deleteAll()
//    }
}

class DataConverter {
    private val gson = Gson()

    @TypeConverter
    fun toJson(courseSignUP: List<courseSignUP>): String {
        return gson.toJson(courseSignUP)
    }

    @TypeConverter
    fun toList(ValuesString: String): List<courseSignUP> {
//        "toList".pln()
        val type = object : TypeToken<List<courseSignUP>>() {}.type
        return gson.fromJson<List<courseSignUP>>(ValuesString, type)
    }
}

@Database(entities = [(SignUpTable::class)], version = 3)
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
//                    .addMigrations(MIGRATION_1_2).
                    .build()
        }
    }
    //修改資料庫版本
//val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                + "`name` TEXT, PRIMARY KEY(`id`))")
//    }
//
//}
}