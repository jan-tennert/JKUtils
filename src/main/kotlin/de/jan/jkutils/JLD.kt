package de.jan.jkutils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import java.io.File

class JLD(val json: JSONObject) {

    fun writeToFile(file: File, encrypted: Boolean = false) {
        var string = json.toString()
        if(encrypted) {
            string = AESEncyption().encrypt(string)!!
        }
        file.writeBytes(string.toByteArray())
    }

    fun <C> toObject(cl: Class<C>) : C {
        try {
            return Gson().fromJson(json.toString(), cl)
        } catch (e: JsonSyntaxException) {
            throw JLDException("Couldn't convert the json object to a java object")
        }
    }


}

class JLDException(exception: String) : Exception(exception)

fun File.fileToDatabase(encrypted: Boolean) : JLD {
    if(encrypted) {
        return JLD(JSONObject(AESEncyption().decrypt(this.inputStream().readBytes().toString(Charsets.UTF_8))))
    } else {
        return JLD(JSONObject(this.inputStream().readBytes().toString(Charsets.UTF_8)))
    }
}

fun Any.objectToDatabase() : JLD {
    return JLD(JSONObject(this))
}

