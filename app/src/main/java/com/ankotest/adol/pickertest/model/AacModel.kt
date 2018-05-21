package com.ankotest.adol.pickertest.model

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


//data class SignUpData(val data:List<Data>){
    data class SignUpData(val title: String, val signUp:MutableList<Int>)
//}
/*Array [a,b,c]
 a - 按鈕類型  0 用餐 1 上課
 b - 確認
 //c - 預先報名狀況暫停
*/

@Entity
data class SignUpTable(@PrimaryKey(autoGenerate = true)
                       @ColumnInfo(name = "id") var id: Int = 0,
                       @ColumnInfo(name = "month") var month: Int,
                       @ColumnInfo(name = "title") var Type: String,
                       @ColumnInfo(name = "sex") var sex: String,
                       @ColumnInfo(name = "name") var name: String,
                       @ColumnInfo(name = "userData") var userData: List<SignUpData>
)

@Dao
interface SignUpDao {
    @Insert
    fun insertOne(insert: SignUpTable)

    @Insert
    fun insertMatch(list: List<SignUpTable>)

    @Update
    fun upDateSign(up: SignUpTable)

    @Query("select * from SignUpTable where month = :mt")
    fun getAll(mt:Int = 4): List<SignUpTable>

    @Query("select * from SignUpTable where title = :stype")
    fun getSignUp(stype: String): List<SignUpTable>

    @Query("DELETE FROM SignUpTable")
    fun deleteAll()
}

class SignUpRepository(ctx: Context) {
    private val mWordDao: SignUpDao

    init {
        val db = AppDataBase.getInstance(ctx)
        mWordDao = db!!.signUpDao()
    }

    fun getSignUp(type: String): MutableLiveData<List<SignUpTable>> {
        val data = MutableLiveData<List<SignUpTable>>()
        data.postValue(mWordDao.getSignUp(type))
        return data
    }

    fun getAll():  List<SignUpTable>{
        return mWordDao.getAll()
    }

    fun insert(signUp: SignUpTable) {
        mWordDao.insertOne(signUp)
    }

    fun upDate(signUp: SignUpTable) {
        mWordDao.upDateSign(signUp)
    }

    fun deleteAll() {
        mWordDao.deleteAll()
    }
}

class DataConverter {
    private val gson = Gson()

    @TypeConverter
    fun toJson(signUP: List<SignUpData>): String {
//        "toJson".pln()
//        gson.toJson(signUP).pln()
        return gson.toJson(signUP)
    }

    @TypeConverter
    fun toList(ValuesString: String): List<SignUpData> {
//        "toList".pln()
        val type = object : TypeToken<List<SignUpData>>() {}.type
        return gson.fromJson<List<SignUpData>>(ValuesString, type)
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
                INSTANCE
                        ?: buildDatabase(context).also {
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
//val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                + "`name` TEXT, PRIMARY KEY(`id`))")
//
//    }
//
//}
}