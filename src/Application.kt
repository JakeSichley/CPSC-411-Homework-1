package org.csuf.cspc411

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import java.util.*
import org.csuf.cspc411.Dao.Database
import org.csuf.cspc411.Dao.claim.Claim
import org.csuf.cspc411.Dao.claim.ClaimDao

fun main(args: Array<String>): Unit {
    // Register PersonStore callback functions

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // extension
    // @annotation
    // routing constructor takes only one parameter which is a lambda function
    // DSL - Domain Specific Language
    routing{
        get("/ClaimService/add") {
            val contentType = call.request.contentType()
            val callData = call.request.receiveChannel()
            val dataLength = callData.availableForRead
            val output = ByteArray(dataLength)
            callData.readAvailable(output)

            val json = Gson().fromJson(String(output), Claim::class.java)
            val claim = Claim(UUID.randomUUID(), json.title, json.date, false)
            val dao = ClaimDao().addClaim(claim)

            println("HTTP message is using POST method with /post $contentType ${String(output)}")
            call.respondText("The POST request was successfully processed.",
                status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }

        post("/ClaimService/getAll") {
            val claimList = ClaimDao().getAll()
            println("Retrieved ${claimList.size} claims.")
            val json = Gson().toJson(claimList)
            call.respondText(json, status = HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }
    }
}

