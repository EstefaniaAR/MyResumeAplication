package com.example.estefaniaar.myresumeaplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import org.json.JSONObject
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import android.widget.TextView
import java.io.BufferedInputStream
import java.net.HttpURLConnection


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("CREATING ACTIVITY....")

        GetWeatherTask(findViewById(R.id.text)).execute()

    }
}
private class GetWeatherTask(textView: TextView) : AsyncTask<Unit, Unit, String>() {

    val innerTextView: TextView? = textView

    override fun doInBackground(vararg params: Unit?): String? {
        val url = URL("https://gist.githubusercontent.com/EstefaniaAR/daa98710a94d0e8f62a8663cca6a8583/raw/38fb5c097a495eeffde6d8c70f21bb7f18a29797/resume.json")
        val httpClient = url.openConnection() as HttpURLConnection
        if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
            try {
                val stream = BufferedInputStream(httpClient.inputStream)
                val data: String = readStream(inputStream = stream)
                println("DATA RESPONSE: $data")
                return data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                httpClient.disconnect()
            }
        } else {
            println("ERROR ON RESPONSE: ${httpClient.responseCode}")
        }
        return null
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)


        val name = JSONObject(result).get("name").toString()
        val position = JSONObject(result).get("position").toString()
        val objective = JSONObject(result).get("objective").toString()
        val experience = JSONObject(result).getJSONArray("experience")
        val technologies = JSONObject(result).getJSONArray("technologies")
        val language = JSONObject(result).getJSONArray("language")
        val bachelor = JSONObject(result).get("bachelor").toString()
        val master = JSONObject(result).get("master").toString()
        val courses = JSONObject(result).getJSONArray("course")
        val certifications = JSONObject(result).getJSONArray("technologies")

        innerTextView?.text = technologies.toString()
        /**
         * ... Work with the weather data
         */

    }
}


