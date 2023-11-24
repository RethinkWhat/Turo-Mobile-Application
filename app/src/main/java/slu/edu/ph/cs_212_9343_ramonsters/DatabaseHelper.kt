package slu.edu.ph.cs_212_9343_ramonsters

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context : Context) :
    SQLiteOpenHelper(context, database_name, null, database_version) {

    companion object {

        //These are called above in specifying the database name and version
        private const val database_name = "userAccounts.db"
        private const val database_version = 1

        // These define the columns of the database
        private const val usersTable = "usersTable"
        private const val columnUserID = "userID"
        private const val columnPassHash = "passHash"
        private const val columnUserType = "isTutor"
        private const val columnSpecialization = "tutorSpeciality"
        private const val columnRate = "tutorRate"
        private const val columnRating = "tutorRating"


    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
                    CREATE TABLE $usersTable (
                        $columnUserID TEXT PRIMARY KEY,
                        $columnPassHash TEXT,
                        $columnUserType INTEGER,
                        $columnSpecialization STRING,
                        $columnRate DOUBLE,
                        $columnRating INT
                    )
                """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addUser(newUser: User) {
        val database = this.writableDatabase
        val values = ContentValues()
        values.put(columnUserID, newUser.userID)
        values.put(columnPassHash, newUser.passHash)
        values.put(columnUserType,  newUser.userType)
        values.put(columnSpecialization, newUser.specialization)
        values.put(columnRate, newUser.rate)
        values.put(columnRating, newUser.rating)
        database.insert(usersTable,null,values)
        Log.i("addUser", "User added")
    }

    fun getUser(username : String) : User? {
        Log.i("Get User" , "Get User Reached")
        val database = this.readableDatabase
        val cursor : Cursor = database.rawQuery("SELECT * FROM $usersTable WHERE $columnUserID=?", arrayOf(username))
        Log.i("Get User" , "Query Success")
        return if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndexOrThrow(columnUserID))
            Log.i("Get User" , "Get User ID reached")
            val password = cursor.getString(cursor.getColumnIndexOrThrow(columnPassHash))
            Log.i("Get User" , "Get User Pass Hash Reached")
            val status = cursor.getString(cursor.getColumnIndexOrThrow(columnUserType))
            Log.i("Get User" , "Get User Tutor Status Reached")
            val specialization = cursor.getString(cursor.getColumnIndexOrThrow(columnSpecialization))
            val rate = cursor.getString(cursor.getColumnIndexOrThrow(columnRate))
            val rating = cursor.getString(cursor.getColumnIndexOrThrow(columnRating))

                User(username,password,status.toInt(), specialization, rate.toDouble(),rating.toInt() )
        }

        else {
            null
        }


    }

}
