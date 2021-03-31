package de.jan.jkutils

import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.io.IOException
import java.io.BufferedOutputStream

import java.io.FileOutputStream

import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL


fun InputStream.toFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
}

fun URL.downloadFile(outputFile: File) {
    val url = URLDownloader()
    url.download(this.toString(), outputFile)
}

fun URI.downloadFile(outputFile: File) {
    val url = URLDownloader()
    url.download(this.toString(), outputFile)
}

fun File.toJSONObject() : JSONObject {
    return JSONObject(this.bufferedReader().use { it.readText() })
}

fun File.toJSONArray() : JSONArray {
    return JSONArray(this.bufferedReader().use { it.readText() })
}

infix fun String.writeTo(file: File) {
    file.writeText(this)
}


class URLDownloader {
    /**
     * Download a file from an url
     * @param url
     * @param output
     * @return
     */
    fun download(url: String?, output: File?): File? {
        try {
            val url = URL(url)
            val http: HttpURLConnection = url.openConnection() as HttpURLConnection
            val `in` = BufferedInputStream(http.inputStream)
            val fos = FileOutputStream(output)
            val bout = BufferedOutputStream(fos, 1024)
            val buffer = ByteArray(1024)
            var read: Int
            while (`in`.read(buffer, 0, 1024).also { read = it } >= 0) {
                bout.write(buffer, 0, read)
            }
            bout.close()
            `in`.close()
            return output
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    /**
     * Download a file from an url with a listener
     * @param url
     * @param output
     * @param listener
     * @return
     */
    fun asyncDownload(url: String, output: File, listener: DownloadListener): File? {
        try {
            val URL = URL(url)
            val http: HttpURLConnection = URL.openConnection() as HttpURLConnection
            val fileSize = http.contentLengthLong.toDouble()
            val `in` = BufferedInputStream(http.inputStream)
            val fos = FileOutputStream(output)
            val bout = BufferedOutputStream(fos, 1024)
            val buffer = ByteArray(1024)
            var downloaded = 0.00
            var read: Int
            var percentDownloaded: Double
            while (`in`.read(buffer, 0, 1014).also { read = it } >= 0) {
                bout.write(buffer, 0, read)
                downloaded += read.toDouble()
                percentDownloaded = downloaded * 100 / fileSize
                val percent = String.format("%.4f", percentDownloaded)
                listener.onDownloading(percent.replace(",", ".").toDouble())
            }
            bout.close()
            `in`.close()
            listener.onCompletion(output)
            return output
        } catch (e: IOException) {
            listener.onError(e)
        }
        return null
    }

    /**
     * Set the user agent
     * @param a
     */
    fun setUserAgent(a: String?) {
        System.setProperty("http.agent", a)
    }
}

interface DownloadListener {
    fun onDownloading(percentComplete: Double)
    fun onCompletion(file: File)
    fun onError(e: IOException?)
}