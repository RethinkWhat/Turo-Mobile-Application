package slu.edu.ph.cs_212_9343_ramonsters


// Declare what a user is in the system
data class User(
    val userID: String,
    val passHash: String,
    val fullName: String,
    val contactNumber : String,
    // 0 is regular user, 1 is tutor, 2 is pending tutor, 3 is admin
    val userType: Int,

    /** Tutor */
    val specialization: String,
    val rate: Double,
    val rating: Int,
    val PFP: ByteArray?,
    var resume: ByteArray?,
    val confirmations: String?

)