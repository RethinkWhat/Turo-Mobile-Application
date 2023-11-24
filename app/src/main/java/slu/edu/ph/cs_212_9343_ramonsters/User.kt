package slu.edu.ph.cs_212_9343_ramonsters


// Declare what a user is in the system
data class User (
    val userID : String,
    val passHash : String,
    val tutor : Boolean,
    val specialization : String,
    val rate : Double,
    val rating : Int,
)