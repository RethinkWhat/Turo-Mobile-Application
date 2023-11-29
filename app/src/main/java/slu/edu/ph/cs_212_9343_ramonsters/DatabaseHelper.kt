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
        private const val columnFullName = "fullName"
        private const val columnContactNumber = "contact"
        private const val columnUserType = "isTutor"
        private const val columnSpecialization = "tutorSpeciality"
        private const val columnRate = "tutorRate"
        private const val columnRating = "tutorRating"
        private const val columnPFP = "profilePicture"
        private const val columnResume = "resume"
        private const val columnConfirmation = "confirmation"


    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
                    CREATE TABLE $usersTable (
                        $columnUserID TEXT PRIMARY KEY,
                        $columnPassHash TEXT,
                        $columnFullName TEXT,
                        $columnContactNumber TEXT,
                        $columnUserType INTEGER,
                        $columnSpecialization STRING,
                        $columnRate DOUBLE,
                        $columnRating INT,
                        $columnPFP BLOB,
                        $columnResume BLOB,
                        $columnConfirmation STRING
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
        values.put(columnFullName, newUser.fullName)
        values.put(columnContactNumber, newUser.contactNumber)
        values.put(columnUserType,  newUser.userType)
        values.put(columnSpecialization, newUser.specialization)
        values.put(columnRate, newUser.rate)
        values.put(columnRating, newUser.rating)
        values.put(columnPFP, newUser.PFP)
        values.put(columnResume, newUser.resume)
        values.put(columnConfirmation, newUser.confirmations)
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
            val PFP = cursor.getBlob(cursor.getColumnIndexOrThrow(columnPFP))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(columnFullName))
            val contact = cursor.getString(cursor.getColumnIndexOrThrow(columnContactNumber))
            val resume = cursor.getBlob(cursor.getColumnIndexOrThrow(columnResume))
            val confirmation = cursor.getString(cursor.getColumnIndexOrThrow(columnConfirmation))
            User(username,password,name, contact, status.toInt(), specialization, rate.toDouble(),rating.toInt(),PFP, resume,confirmation)
        }
        else {
            null
        }
    }

    fun applyTutor(username : String, specialization : String, rate : Double, resume : ByteArray?) {
        val database = this.writableDatabase
        val values = ContentValues()

        val newUser = getUser(username)

        values.put(columnUserID, newUser!!.userID)
        values.put(columnPassHash, newUser.passHash)
        values.put(columnFullName, newUser.fullName)
        values.put(columnContactNumber, newUser.contactNumber)
        values.put(columnUserType,  2)
        values.put(columnSpecialization, specialization)
        values.put(columnRate, rate)
        values.put(columnRating, newUser.rating)
        values.put(columnPFP, newUser.PFP)
        values.put(columnResume, resume)
        values.put(columnConfirmation, newUser.confirmations)
        Log.i("applyTutor", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(username))
        database.close()
        Log.i("applyTutor", "User updated")
    }

}
