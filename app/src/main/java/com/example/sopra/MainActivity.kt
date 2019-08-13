package com.example.sopra

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.io.IOException
import android.content.ContentUris
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAKE_PHOTO: Int = 1
    val CHOOSE_PHOTO: Int = 2

    var picture: ImageView? = null
    var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var outputImage = File(externalCacheDir,"output_image.jpg")

            try{
                if(outputImage.exists()) {
                    outputImage.delete()
                }
            }catch (ex: IOException) {
                ex.printStackTrace()
            }

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imageUri = Uri.fromFile(outputImage)
            }else {
                imageUri = FileProvider.getUriForFile(MainActivity@this,"com.example.fredric.takephoto.fileprovider",outputImage)
            }

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, TAKE_PHOTO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
