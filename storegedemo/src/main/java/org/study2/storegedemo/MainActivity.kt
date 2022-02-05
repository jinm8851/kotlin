package org.study2.storegedemo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.study2.storegedemo.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val CREATE_REQUEST_CODE = 40
    private val OPEN_REQUEST_CODE = 41
    private val SAVE_REQUEST_CODE = 42


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun newFile(view: View) {
//        새로운 ACTION_CREATE_DOCUMENT 인텐트객체 생성
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
//        파일 디스크립터로 열수있는 파일만 반환하도록 인텐트구성
        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        열고자하는 파일텍스트 타입 "text/plain" 지정 임시파일명 지정 "newfile.txt"
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt")

        startActivityForResult(intent, CREATE_REQUEST_CODE)
    }

    fun saveFile(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"

        startActivityForResult(intent, SAVE_REQUEST_CODE)
    }

    fun openFile(view: View){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        startActivityForResult(intent,OPEN_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var currentUri: Uri? = null
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST_CODE) {
                if (data != null) {
                    binding.fileText.setText("")
                }
            } else if (requestCode == SAVE_REQUEST_CODE) {
                data?.let {
                    currentUri = it.data
                    currentUri?.let {
                        writeFileContent(it)
                    }
                }
            } else if (requestCode == OPEN_REQUEST_CODE) {
                data?.let {
                    currentUri = it.data
                    currentUri?.let {
                        try {
                            val content = readFileContent(it)
                            binding.fileText.setText(content)
                        } catch (e: IOException ){
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    private fun writeFileContent(uri: Uri) {
        try {
            val pfd = contentResolver.openFileDescriptor(uri, "w")
            val fileOutputStream = FileOutputStream(pfd?.fileDescriptor)
            val textContent = binding.fileText.text.toString()

            fileOutputStream.write(textContent.toByteArray())
            fileOutputStream.close()
            pfd?.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFileContent(uri: Uri) : String {
        val inputStream = contentResolver.openInputStream(uri)
        val reader =BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var currentline = reader.readLine()

        while (currentline != null  ) {
            stringBuilder.append(currentline + "\n")
            currentline =reader.readLine()
        }
        inputStream?.close()

        return stringBuilder.toString()
    }
}