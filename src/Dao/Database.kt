package org.csuf.cspc411.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor (var dbPath : String = "") {

    init {
        // Create the database, create tables and keeps the db connection
        dbPath = "C:\\Users\\Jake\\Desktop\\claimDB.db"
        val dbConn = SQLiteConnection(File(dbPath))
        dbConn.open()
        val sqlStmt = "CREATE TABLE IF NOT EXISTS CLAIM (ID TEXT, TITLE TEXT, DATE TEXT, " +
                "ISSOLVED INTEGER)"
        dbConn.exec(sqlStmt)
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbPath))
        dbConn.open()
        return dbConn
    }

    // single object pattern
    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }
}

fun main() {

}