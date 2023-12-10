package slu.edu.ph.cs_212_9343_ramonsters

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

/**
 * The DatabaseHandler class handles CRUD operations for the SQLite database used by this application.
 * It extends SQLiteOpenHelper and manages user-related data such as accounts, pending applications, and confirmations.
 */
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
    
     /**
     * Creates the usersTable in the database when the DatabaseHandler instance is created.
     */
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
     * This method returns a user based on the passed username
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
     * This method is utilized when a user would like to apply to be a tutor.
     * It needs the username of the user, their specialization, asking rate, and their resume or cv.
     * Afterwards, the status of the user will be changed to '2', which indicates they are pending tutors that are under evaluation from the admins.
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
     * This method will accept or reject a tutor application depending upon the decision of an administrator.
     * This method only needs the pending tutor (user) and the status (1 for accept, 0 for reject)
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

        val database2 = this.writableDatabase
        var studentNewPendings = ""
        if (student!!.pendings!="") {
            studentNewPendings = student!!.pendings + ",$tutorUsername"
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
        database2.update(usersTable, values, "userID=?", arrayOf(studentUsername))
        database2.close()
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

        val database = this.writableDatabase
        val values = ContentValues()
        var newConfirms = ""
        var currTutorPendings = tutor!!.pendings.toString()
        var newTutorPendings = currTutorPendings.replace(studentUsername, "")
        if (tutor!!.confirmations!="") {
            newConfirms = tutor!!.confirmations + ",$studentUsername"
        } else {
            newConfirms = "$studentUsername"
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
        values.put(columnPendings, newTutorPendings)
        values.put(columnConfirmation, newConfirms)
        Log.i("tutorAcceptOrRejectStudent", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(tutorUsername))
        database.close()
        Log.i("tutorAcceptOrRejectStudent", "User updated")

        val database2 = this.writableDatabase
        var studentNewConfirms = ""
        var currStudentPendings = student!!.pendings.toString()
        var newStudentPendings = currStudentPendings.replace(tutorUsername, "")
        if (student!!.confirmations!="") {
            studentNewConfirms = student!!.confirmations + ",$tutorUsername"
        } else {
            studentNewConfirms = "$tutorUsername"
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
        values.put(columnPendings, newStudentPendings)
        values.put(columnConfirmation, studentNewConfirms)
        Log.i("tutorAcceptOrReject", "user update attempted")
        database2.update(usersTable, values, "userID=?", arrayOf(studentUsername))
        database2.close()
        Log.i("tutorAcceptOrReject", "User updated")
    }

    /**
     * Method to get an arrayList of all the users that fall under a specific user type.
     * 0 = regular user
     * 1 = tutor
     * 2 = pending tutor
     * 3 = admin
     */
    fun getUsers(userType : Int): ArrayList<User> {
        Log.i("getUsers reached", "method started")
        val database = this.readableDatabase
        Log.i("database", "database val created")
        var cursor : Cursor? = null
        try {
            cursor = database.rawQuery("SELECT * FROM $usersTable WHERE $columnUserType=?", arrayOf(userType.toString())) //TODO:Change 0 to userType
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
     * This method will return an ArrauList that contains the pendings of the passed user.
     */
    fun getPendings(username : String?) : ArrayList<User> {
        val user: User = getUser(username!!)!!

        if (user == null || user.pendings.isNullOrEmpty()) {
            // Return an empty list if the user is null or has no pendings
            return ArrayList()
        }

        val usernameList: List<String> = if (user.pendings.contains(",")) {
            user.pendings.split(",")
        } else {
            listOf(user.pendings)
        }

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

        if (user == null || user.confirmations.isNullOrEmpty()) {
            // Return an empty list if the user is null or has no confirmations
            return ArrayList()
        }

        val usernameList: List<String> = if (user.confirmations.contains(",")) {
            user.confirmations.split(",")
        } else {
            listOf(user.confirmations)
        }

        val toReturn: ArrayList<User> = ArrayList()

        for (username in usernameList) {
            toReturn!!.add(getUser(username!!)!!)
        }
        return toReturn
    }

    /**
     * Method to change status of a user
     */

    fun changeStatus (username : String, status : Int) {
        var user = getUser(username)

        val database = this.writableDatabase
        val values = ContentValues()

        values.put(columnUserID, user!!.userID)
        values.put(columnPassHash, user.passHash)
        values.put(columnFullName, user.fullName)
        values.put(columnContactNumber, user.contactNumber)
        values.put(columnUserType,  status)
        values.put(columnLocation, user.location)
        values.put(columnSpecialization1, user.specialization1)
        values.put(columnSpecialization2, user.specialization2)
        values.put(columnSpecialization3, user.specialization3)
        values.put(columnRate, user.rate)
        values.put(columnRating, user.rating)
        values.put(columnPFP, user.PFP)
        values.put(columnResume, user.resume)
        values.put(columnPendings, user.pendings)
        values.put(columnConfirmation, user.confirmations)
        Log.i("changeStatus", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(user.userID))
        database.close()
        Log.i("changeStatus", "User updated")
    }

    fun deleteFromConfirms(studentUsername : String, tutorUsername : String) {
        var tutor = getUser(tutorUsername)
        var student = getUser(studentUsername)

        var studConfirmations = student!!.confirmations
        var tutorConfirmations = tutor!!.confirmations


        if (studConfirmations!!.contains(",${tutor.userID}")) {
            studConfirmations = studConfirmations!!.replace(",${tutor.userID}", "")
        } else if (studConfirmations!!.contains("${tutor.userID},")) {
            studConfirmations = studConfirmations!!.replace(",${tutor.userID}", "")
        } else {
            studConfirmations = studConfirmations!!.replace("${tutor.userID}", "")
        }

        if (tutorConfirmations!!.contains(",${student.userID}")) {

            tutorConfirmations = tutorConfirmations!!.replace(",${student.userID}", "")
        } else if (tutorConfirmations!!.contains("${student.userID},")) {

            tutorConfirmations = tutorConfirmations!!.replace(",${student.userID}", "")

        } else { tutorConfirmations = tutorConfirmations!!.replace("${student.userID}", "")
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
        values.put(columnPendings, tutor.pendings)
        values.put(columnConfirmation, tutorConfirmations)
        Log.i("deleteFromConfirms", "user update attempted")
        database.update(usersTable, values, "userID=?", arrayOf(tutorUsername))
        database.close()
        Log.i("deleteFromConfirms", "User updated")

        val database2 = this.writableDatabase
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
        values.put(columnPendings, student.pendings)
        values.put(columnConfirmation, studConfirmations)
        Log.i("deleteFromConfirms", "user update attempted")
        database2.update(usersTable, values, "userID=?", arrayOf(studentUsername))
        database2.close()
        Log.i("deleteFromConfirms", "User updated")
    }



}
