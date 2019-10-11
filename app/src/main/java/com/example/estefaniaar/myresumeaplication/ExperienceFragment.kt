package com.example.estefaniaar.myresumeaplication

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_experience.*
import kotlinx.android.synthetic.main.fragment_experience.view.*
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
 * [ExperienceFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ExperienceFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ExperienceFragment : Fragment() {
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

        val view = inflater.inflate(R.layout.fragment_experience, container, false)
        val expList = view.exp_list
        GetExp(this.requireActivity(),expList).execute()
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
         * @return A new instance of fragment ExperienceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExperienceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

private class GetExp(activity:Activity,expView:ListView) : AsyncTask<Unit, Unit, String>() {

    val expView = expView
    val activity = activity

    override fun doInBackground(vararg params: Unit?): String? {
        val url =
            URL("https://gist.githubusercontent.com/EstefaniaAR/daa98710a94d0e8f62a8663cca6a8583/raw/38fb5c097a495eeffde6d8c70f21bb7f18a29797/resume.json")
        val httpClient = url.openConnection() as HttpURLConnection
        if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
            try {
                val stream = BufferedInputStream(httpClient.inputStream)
                val data: String = readStream(inputStream = stream)
                //println("DATA RESPONSE: $data")
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


        var exps  =JSONObject(result).getJSONArray("experience")
        var expList =ArrayList<Experience>()
        for (n in 0 until exps.length())
        {
            var enter = exps.getJSONObject(n).get("enterprise").toString()
            var date = exps.getJSONObject(n).get("date").toString()
            var position = exps.getJSONObject(n).get("position").toString()
            var tasks  =exps.getJSONObject(n).getJSONArray("task")
            var strTask:String=""
            for(x in 0 until tasks.length())
            {
                strTask+=tasks.get(x).toString().replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")+"\n"
            }
            expList.add(Experience(enter,date,position,strTask))
        }


        var adapter  : CustomAdapter?=null;
        adapter= CustomAdapter(this.activity,expList)
        expView.adapter = adapter
    }
}