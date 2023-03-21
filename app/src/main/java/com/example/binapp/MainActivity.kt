package com.example.binapp


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.binapp.adapter.HistoryAdapter
import com.example.binapp.databinding.ActivityMainBinding
import com.example.binapp.model.BankModel
import com.example.binapp.model.HistoryModel
import org.json.JSONObject
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {

    private var bin_num: EditText? = null

    lateinit var adapter: HistoryAdapter
    var historyList = arrayListOf<String>()
    var index = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HistoryAdapter()

        bin_num = findViewById(R.id.bin_num)

        initialStart()

        binding.mainBtn.setOnClickListener() {
            if (bin_num?.text?.toString()?.equals("")!!) {
                Toast.makeText(this, "Enter Bin", Toast.LENGTH_LONG).show()
            } else {
                getResult(bin_num!!.text.toString())
                initial()
                saveTextFile(bin_num!!.text.toString())

            }
        }
    }

    private fun initial(){
        binding.apply {
            searchHistory.layoutManager = LinearLayoutManager(this@MainActivity)
            searchHistory.adapter = adapter
            historyList.add(bin_num!!.text.toString())
            saveTextFile(bin_num!!.text.toString())
            val his = HistoryModel(historyList[index])
            adapter.addHis(his)
            index++
        }
    }

    private fun initialStart(){
        binding.apply {
            val fileOutputStream: FileInputStream =
                openFileInput("mytextfile.txt")
            val outputReader = InputStreamReader(fileOutputStream)
            val read = outputReader.readLines()
            outputReader.close()
            searchHistory.layoutManager = LinearLayoutManager(this@MainActivity)
            searchHistory.adapter = adapter
            read.forEach{
                historyList.add(it)
                val his = HistoryModel(historyList[index])
                adapter.addHis(his)
                index++
            }
        }
    }
    fun saveTextFile(bin: String) {
            val fileOutputStream: FileOutputStream =
                openFileOutput("mytextfile.txt", Context.MODE_PRIVATE)
            val outputWriter = OutputStreamWriter(fileOutputStream)
            outputWriter.write(bin)
            outputWriter.close()
            //display file saved message
        }

    private fun getResult(bin: String) {
        val url = "https://lookup.binlist.net/$bin"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result -> parseBankData(result) },
            { error -> Log.d("MyLog", "Eror: $error") })
        queue.add(request)

    }

    private fun parseBankData(result: String) {
        val mainObject = JSONObject(result)
        val item = BankModel(
            mainObject.optString("scheme"),
            mainObject.optString("type"),
            mainObject.optString("brand"),
            mainObject.optString("prepaid"),
            mainObject.getJSONObject("country").optString("alpha2"),
            mainObject.getJSONObject("country").optString("name"),
            mainObject.getJSONObject("country").optString("latitude"),
            mainObject.getJSONObject("country").optString("longitude"),
            mainObject.getJSONObject("bank").optString("name"),
            mainObject.getJSONObject("bank").optString("url"),
            mainObject.getJSONObject("bank").optString("phone"),
            mainObject.getJSONObject("bank").optString("city")
        )
        Log.d("MyLog", "Name: ${item.b_name}")
        binding.scheme.text = item.scheme
        binding.bName.text = item.b_name
        binding.brand.text = item.brand
        binding.site.text = item.url
        binding.phone.text = item.phone
        binding.type.text = item.type
        binding.prepaid.text = item.prepaid
        binding.country.text = "${item.alpha2} ${item.c_name}"
        binding.latlong.text = "${item.latitude}n ${item.longitude}w"

    }

}