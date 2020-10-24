package org.csuf.cspc411.Dao.claim

import org.csuf.cspc411.Dao.Dao
import org.csuf.cspc411.Dao.Database
import java.util.*

class ClaimDao : Dao() {

    fun addClaim(cObj : Claim) {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare the sql statement
        sqlStmt = "INSERT INTO CLAIM (ID, TITLE, DATE, ISSOLVED) VALUES " +
                "('${cObj.id.toString()}', '${cObj.title}', '${cObj.date}', '${cObj.isSolved}')"

        // 3. submit the sql statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare the sql statement
        sqlStmt = "SELECT ID, TITLE, DATE, ISSOLVED FROM CLAIM"

        // 3. submit the sql statement
        val claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // 4. Convert into Kotlin object format
        while (st?.step()!!) {
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnNull(3)
            claimList.add(Claim(UUID.fromString(id) , title, date, isSolved))
        }

        return claimList
    }
}