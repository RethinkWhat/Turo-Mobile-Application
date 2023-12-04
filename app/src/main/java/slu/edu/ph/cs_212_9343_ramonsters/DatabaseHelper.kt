package slu.edu.ph.cs_212_9343_ramonsters

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf


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
        private const val columnLocation = "tutorLocation"
        private const val columnSpecialization1 = "tutorSpeciality1"
        private const val columnSpecialization2 = "tutorSpeciality2"
        private const val columnSpecialization3 = "tutorSpeciality3"
        private const val columnRate = "tutorRate"
        private const val columnRating = "tutorRating"
        private const val columnPFP = "profilePicture"
        private const val columnResume = "resume"
        private const val columnPendings = "pendings"
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
                        $columnLocation TEXT,
                        $columnSpecialization1 TEXT,
                        $columnSpecialization2 TEXT,
                        $columnSpecialization3 TEXT,
                        $columnRate DOUBLE,
                        $columnRating INT,
                        $columnPFP BLOB,
                        $columnResume BLOB,
                        $columnPendings TEXT,
                        $columnConfirmation TEXT
                    )
                """.trimIndent()
        db.execSQL(createTableQuery)
    }

    /**
     * Ignore this
     */
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
        values.put(columnLocation, newUser.location)
        values.put(columnSpecialization1, newUser.specialization1)
        values.put(columnSpecialization2, newUser.specialization2)
        values.put(columnSpecialization3, newUser.specialization3)
        values.put(columnRate, newUser.rate)
        values.put(columnRating, newUser.rating)
        values.put(columnPFP, newUser.PFP)
        values.put(columnResume, newUser.resume)
        values.put(columnPendings, newUser.pendings)
        values.put(columnConfirmation, newUser.confirmations)
        database.insert(usersTable,null,values)
        Log.i("addUser", "User added")
    }

    /**
     * Method  to get a user given their username.
     */
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
            val location = cursor.getString(cursor.getColumnIndexOrThrow(columnLocation))
            val specialization1 = cursor.getString(cursor.getColumnIndexOrThrow(columnSpecialization1))
            val specialization2 = cursor.getString(cursor.getColumnIndexOrThrow(columnSpecialization2))
            val specialization3 = cursor.getString(cursor.getColumnIndexOrThrow(columnSpecialization3))
            val rate = cursor.getDouble(cursor.getColumnIndexOrThrow(columnRate))
            val rating = cursor.getInt(cursor.getColumnIndexOrThrow(columnRating))
            val PFP = cursor.getBlob(cursor.getColumnIndexOrThrow(columnPFP))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(columnFullName))
            val contact = cursor.getString(cursor.getColumnIndexOrThrow(columnContactNumber))
            val resume = cursor.getBlob(cursor.getColumnIndexOrThrow(columnResume))
            val pendings = cursor.getString(cursor.getColumnIndexOrThrow(columnPendings))
            val confirmation = cursor.getString(cursor.getColumnIndexOrThrow(columnConfirmation))
            User(username,password,name, contact, status.toInt(), location,specialization1,specialization2,specialization3, rate.toDouble(),rating.toInt(),PFP, resume,pendings,confirmation)
        }
        else {
            null
        }
    }

    /**
     * Method for when a user would like to apply to be a tutor
     * Get the username of the user, their specialization, the rate they are asking, and their resume
     * The user status will be changed to 2. Meaning that they are not tutors yet, but are under evaluation
     */
    fun applyToBecomeTutor(username : String, location : String,
                           specialization1 : String,specialization2 : String,specialization3 : String, rate : Double, resume : ByteArray?) {
        val database = this.writableDatabase
        val values = ContentValues()

        val newUser = getUser(username)

        values.put(columnUserID, newUser!!.userID)
        values.put(columnPassHash, newUser.passHash)
        values.put(columnFullName, newUser.fullName)
        values.put(columnContactNumber, newUser.contactNumber)
        values.put(columnUserType,  2)
        values.put(columnLocation, location)
        values.put(columnSpecialization1, specialization1)
        values.put(columnSpecialization2, specialization2)
        values.put(columnSpecialization3, specialization3)
        values.put(columnRate, rate)
        values.put(columnRating, newUser.rating)
        values.put(columnPFP, newUser.PFP)
        values.put(columnResume, resume)
        values.put(columnPendings, newUser.pendings)
        values.put(columnConfirmation, newUser.confirmations)
        Log.i("applyTutor", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(username))
        database.close()
        Log.i("applyTutor", "User updated")
    }

    /**
     * Method to accept of reject a tutor application.
     * For this method all you need to do is pass in
     * the user who you would like to accept or reject as a tutor and pass in 0 or 1. If you would like
     * to accept the user as a tutor pass in 1 and if you would like to reject the application pass in 0.
     */
    fun acceptOrRejectTutor(username : String, acceptStatus : Int) {
        val database = this.writableDatabase
        val values = ContentValues()
        val newUser = getUser(username)

        values.put(columnUserID, newUser!!.userID)
        values.put(columnPassHash, newUser.passHash)
        values.put(columnFullName, newUser.fullName)
        values.put(columnContactNumber, newUser.contactNumber)
        values.put(columnUserType,  acceptStatus)
        values.put(columnLocation, newUser.location)
        values.put(columnSpecialization1, newUser.specialization1)
        values.put(columnSpecialization2, newUser.specialization2)
        values.put(columnSpecialization3, newUser.specialization3)
        values.put(columnRate, newUser.rate)
        values.put(columnRating, newUser.rating)
        values.put(columnPFP, newUser.PFP)
        values.put(columnResume, newUser.resume)
        values.put(columnPendings, newUser.pendings)
        values.put(columnConfirmation, newUser.confirmations)
        Log.i("acceptOrRejectTutor", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(username))
        database.close()
        Log.i("acceptOrRejectTutor", "User updated")
    }

    /**
     * Method for a user to apply to a tutor.
     * This method is to be used when a user finds a tutor they like and would like to apply to be tutored under them.
     * The method will store the application of the student as a pending application under the pendings class variable
     * of a User object. Pendings will holds a comma separated list of usernames which will hold all the students who
     * applied to a specific tutor.
     *
     * In this method the pendings class variable of both tutor and student will be updated. This is so that we can
     * show the currently pending applications of the student on the student menu and also show the applications the
     * tutor is yet to approve in the tutor menu.
     */
    fun applyToATutor (studentUsername : String, tutorUsername : String) {
        var tutor = getUser(tutorUsername)
        var student = getUser(studentUsername)

        val database = this.writableDatabase
        val values = ContentValues()
        var newPendings = ""
        if (tutor!!.pendings!="") {
            newPendings = tutor!!.pendings + ",$studentUsername"
        } else {
            newPendings = "$studentUsername"
        }

        values.put(columnUserID, tutor!!.userID)
        values.put(columnPassHash, tutor.passHash)
        values.put(columnFullName, tutor.fullName)
        values.put(columnContactNumber, tutor.contactNumber)
        values.put(columnUserType,  tutor.userType)
        values.put(columnLocation, tutor.location)
        values.put(columnSpecialization1, tutor.specialization1)
        values.put(columnSpecialization2, tutor.specialization2)
        values.put(columnSpecialization3, tutor.specialization3)
        values.put(columnRate, tutor.rate)
        values.put(columnRating, tutor.rating)
        values.put(columnPFP, tutor.PFP)
        values.put(columnResume, tutor.resume)
        values.put(columnPendings, newPendings)
        values.put(columnConfirmation, tutor.confirmations)
        Log.i("applyToATutor", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(tutorUsername))
        database.close()
        Log.i("applyToATutor", "User updated")

        var studentNewPendings = ""
        if (tutor!!.pendings!="") {
            studentNewPendings = tutor!!.pendings + ",$tutorUsername"
        } else {
            studentNewPendings = "$tutorUsername"
        }
        values.put(columnUserID, student!!.userID)
        values.put(columnPassHash, student.passHash)
        values.put(columnFullName, student.fullName)
        values.put(columnContactNumber, student.contactNumber)
        values.put(columnUserType,  student.userType)
        values.put(columnLocation, student.location)
        values.put(columnSpecialization1, student.specialization1)
        values.put(columnSpecialization2, student.specialization2)
        values.put(columnSpecialization3, student.specialization3)
        values.put(columnRate, student.rate)
        values.put(columnRating, student.rating)
        values.put(columnPFP, student.PFP)
        values.put(columnResume, student.resume)
        values.put(columnPendings, studentNewPendings)
        values.put(columnConfirmation, student.confirmations)
        Log.i("applyToATutor", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(studentUsername))
        database.close()
        Log.i("applyToATutor", "User updated")
    }

    /**
     * Method for a tutor to accept or reject a student application.
     * This is method is used when the tutor would like to go over the list of students that have applied under him. He
     * has the option to accept or reject the student application. All the student applications are stored under the class variable
     * of a User 'pendings.'
     *
     * If approved the username of the student will be moved to the class variable confirmations which holds a comma seperated list
     * of usernames approved by the tutor.
     */
    fun tutorAcceptOrRejectStudent(studentUsername : String, tutorUsername : String, choice : Int) {
        var tutor = getUser(tutorUsername)
        var student = getUser(studentUsername)

        var studPendings = student!!.pendings
        var tutorPendings = tutor!!.pendings
        var studConfirms = student!!.confirmations
        var tutorConfirms = tutor!!.confirmations

        studPendings!!.replace("$tutorPendings", "")
        tutorPendings!!.replace("$tutorPendings", "")
        // Tutor approves the student
        if (choice == 1) {
            if (studConfirms == "")
                studConfirms += "$tutorUsername"
            else {
                studConfirms += ",$tutorUsername"
            }
        }

        val database = this.writableDatabase
        val values = ContentValues()
        values.put(columnUserID, tutor!!.userID)
        values.put(columnPassHash, tutor.passHash)
        values.put(columnFullName, tutor.fullName)
        values.put(columnContactNumber, tutor.contactNumber)
        values.put(columnUserType,  tutor.userType)
        values.put(columnLocation, tutor.location)
        values.put(columnSpecialization1, tutor.specialization1)
        values.put(columnSpecialization2, tutor.specialization2)
        values.put(columnSpecialization3, tutor.specialization3)
        values.put(columnRate, tutor.rate)
        values.put(columnRating, tutor.rating)
        values.put(columnPFP, tutor.PFP)
        values.put(columnResume, tutor.resume)
        values.put(columnPendings, tutorPendings)
        values.put(columnConfirmation, tutorConfirms)
        Log.i("tutorAcceptOrReject", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(tutorUsername))
        database.close()
        Log.i("tutorAcceptOrReject", "User updated")


        values.put(columnUserID, student!!.userID)
        values.put(columnPassHash, student.passHash)
        values.put(columnFullName, student.fullName)
        values.put(columnContactNumber, student.contactNumber)
        values.put(columnUserType,  student.userType)
        values.put(columnLocation, student.location)
        values.put(columnSpecialization1, student.specialization1)
        values.put(columnSpecialization2, student.specialization2)
        values.put(columnSpecialization3, student.specialization3)
        values.put(columnRate, student.rate)
        values.put(columnRating, student.rating)
        values.put(columnPFP, student.PFP)
        values.put(columnResume, student.resume)
        values.put(columnPendings, studPendings)
        values.put(columnConfirmation, studConfirms)
        Log.i("tutorAcceptOrReject", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(studentUsername))
        database.close()
        Log.i("tutorAcceptOrReject", "User updated")
    }

    /**
     * Method to get an arrayList of all the users that fall under a specific user type.
     * 0 is regular user, 1 is tutor, 2 is pending tutor, 3 is admin.
     * Call the method by passing in the pertinent user type number.
     */
    fun getUsers(userType : Int): ArrayList<User> {
        Log.i("getUsers reached", "method started")
        val database = this.readableDatabase
        Log.i("database", "database val created")
        var cursor : Cursor? = null
        try {
            cursor = database.rawQuery("SELECT * FROM $usersTable WHERE $columnUserType=?", arrayOf("2")) //TODO:Change 0 to userType
        }catch (e : Exception) {
            Log.i("cursor attempt", "No element found")
        }

        var userList: ArrayList<User> = ArrayList()
        Log.i("getUsers", "While statement started")

        while (cursor!!.moveToNext() && cursor != null) {

            val username = cursor.getString(cursor.getColumnIndexOrThrow(columnUserID))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(columnPassHash))
            Log.i("Get User", "Get User Pass Hash Reached")
            val status = cursor.getString(cursor.getColumnIndexOrThrow(columnUserType))
            Log.i("Get User", "Get User Tutor Status Reached")
            val location = cursor.getString(cursor.getColumnIndexOrThrow(columnLocation))
            val specialization1 = cursor.getString(cursor.getColumnIndexOrThrow(columnSpecialization1))
            val specialization2 = cursor.getString(cursor.getColumnIndexOrThrow(columnSpecialization2))
            val specialization3 = cursor.getString(cursor.getColumnIndexOrThrow(columnSpecialization3))
            val rate = cursor.getDouble(cursor.getColumnIndexOrThrow(columnRate))
            val rating = cursor.getInt(cursor.getColumnIndexOrThrow(columnRating))
            val PFP = cursor.getBlob(cursor.getColumnIndexOrThrow(columnPFP))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(columnFullName))
            val contact = cursor.getString(cursor.getColumnIndexOrThrow(columnContactNumber))
            val resume = cursor.getBlob(cursor.getColumnIndexOrThrow(columnResume))
            val pendings = cursor.getString(cursor.getColumnIndexOrThrow(columnPendings))
            val confirmation = cursor.getString(cursor.getColumnIndexOrThrow(columnConfirmation))
            userList.add(
                User(
                    username,
                    password,
                    name,
                    contact,
                    status.toInt(),
                    location,
                    specialization1,
                    specialization2,
                    specialization3,
                    rate,
                    rating,
                    PFP,
                    resume,
                    pendings,
                    confirmation
                )
            )
        }
        return userList
    }

    /**
     * Method to get the pendings of a given user
     */
    fun getPendings(username : String?) : ArrayList<User> {
        val user: User = getUser(username!!)!!
        val usernameList: List<String?> = user!!.pendings!!.split(",")
        val toReturn: ArrayList<User> = ArrayList()

        for (username in usernameList) {
            toReturn!!.add(getUser(username!!)!!)
        }
        return toReturn
    }

    /**
     * Method to get the confirmed of a given user
     */
    fun getConfirmed(username : String?) : ArrayList<User> {
        val user: User = getUser(username!!)!!
        val usernameList: List<String?> = user!!.confirmations!!.split(",")
        val toReturn: ArrayList<User> = ArrayList()

        for (username in usernameList) {
            toReturn!!.add(getUser(username!!)!!)
        }
        return toReturn
    }


}
