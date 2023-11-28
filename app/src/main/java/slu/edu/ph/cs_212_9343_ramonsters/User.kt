package slu.edu.ph.cs_212_9343_ramonsters

import java.sql.Blob


// Declare what a user is in the system
data class User (
    val userID : String,
    val passHash : String,
    // 0 is regular user, 1 is tutor, 2 is pending tutor, 3 is admin
    val userType : Int,
    val specialization : String,
    val rate : Double,
    val rating : Int,
    val PFP : ByteArray?

)