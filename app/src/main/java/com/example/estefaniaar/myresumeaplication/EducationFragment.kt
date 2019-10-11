package com.example.estefaniaar.myresumeaplication

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_education.view.*
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EducationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EducationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EducationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_education, container, false)
        val tech= view.techList
        val lang=view.langList
        val cert=view.certList

        GetEducation(this.requireContext(),tech,lang,cert).execute()
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EducationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EducationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

private class GetEducation(val con:Context,techList: ListView,langList: ListView,certList: ListView ) : AsyncTask<Unit, Unit, String>() {

    val inTech: ListView? = techList
    val inLang: ListView? = langList
    val inCert: ListView? = certList
    val context = con

    override fun doInBackground(vararg params: Unit?): String? {
        val url =
            URL("https://gist.githubusercontent.com/EstefaniaAR/daa98710a94d0e8f62a8663cca6a8583/raw/38fb5c097a495eeffde6d8c70f21bb7f18a29797/resume.json")
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


        val techs=JSONObject(result).getJSONArray("technologies")
        val language = JSONObject(result).getJSONArray("language")
        val courses = JSONObject(result).getJSONArray("course")

        println("TECHS: ${techs}")
        var strTech=ArrayList<String>()
        var strLang=ArrayList<String>()
        var strCour=ArrayList<String>()
        for(n in 0 until techs.length())
        {
            var jsonObject = techs.get(n).toString()
            strTech.add(jsonObject)
        }
        for(n in 0 until language.length())
        {
            var jsonObject = language.get(n).toString()
            strLang.add(jsonObject)
        }
        for(n in 0 until courses.length())
        {
            var jsonObject = courses.get(n).toString()
            strCour.add(jsonObject)
        }

        val textView = TextView(context)
        textView.text = "Technologies"

        inTech?.addHeaderView(textView)

        val textView2 = TextView(context)
        textView2.text = "Languages"

        inLang?.addHeaderView(textView2)

        val textView3 = TextView(context)
        textView3.text = "Courses"

        inCert?.addHeaderView(textView3)


        inTech?.adapter= ArrayAdapter(context,android.R.layout.simple_list_item_1,strTech)
        inLang?.adapter= ArrayAdapter(context,android.R.layout.simple_list_item_1,strLang)
        inCert?.adapter= ArrayAdapter(context,android.R.layout.simple_list_item_1,strCour)

    }
}