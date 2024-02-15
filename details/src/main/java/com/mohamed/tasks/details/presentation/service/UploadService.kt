package com.mohamed.tasks.details.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.mohamed.tasks.details.R
import com.tinify.AccountException
import com.tinify.ClientException
import com.tinify.ConnectionException
import com.tinify.Options
import com.tinify.ServerException
import com.tinify.Source
import com.tinify.Tinify
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class UploadService : Service() {
    private val CHANNEL_ID = "UploadChannel"
    private val NOTIFICATION_ID = 1

    @Inject
    lateinit var serviceNavigation:ServiceNavigation

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)

        GlobalScope.launch(Dispatchers.IO) {
            delay(3000)
            val imageUrl = intent?.getStringExtra("imageUrl")
            val width = intent?.getIntExtra("width", 0)
            val height = intent?.getIntExtra("height", 0)
            if (imageUrl != null) {
                uploadImageToTinyPNG(imageUrl, width, height)
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Upload Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Foreground service channel"
                enableLights(true)
                lightColor = Color.BLUE
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
         val intent = Intent(this, serviceNavigation.getHomeClassName())
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Uploading Image")
            .setContentText("Please wait...")
            .setSmallIcon(R.drawable.ic_upload)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .build()
    }

    private fun uploadImageToTinyPNG(imageUrl: String, width: Int?, height: Int?) {
        try {
            val source: Source = Tinify.fromUrl(imageUrl)
            val options: Options = Options()
                .with("method", "fit")
                .with("width", width)
                .with("height", height)
            val resized: Source = source.resize(options)
           val result= resized.result()
        } catch (e: AccountException) {
            Timber.e("The error message is: " + e.message)
            // Verify your API key and account limit.
        } catch (e: ClientException) {
            // Check your source image and request options.
            Timber.e("The error message is: " + e.message)
        } catch (e: ServerException) {
            // Temporary issue with the Tinify API.
            Timber.e("The error message is: " + e.message)
        } catch (e: ConnectionException) {
            // A network connection error occurred.
            Timber.e("The error message is: " + e.message)
        } catch (e: Exception) {
            // Something else went wrong, unrelated to the Tinify API.
            Timber.e("The error message is: " + e.message)
        }
    }
    /*private fun uploadImageToTinyPNG(imageUrl: String, width: Int?, height: Int?) {
        val client = OkHttpClient()

        // Create request URL with query parameters
        val url = "https://api.tinify.com/shrink".toHttpUrlOrNull()!!.newBuilder()
            //.addQueryParameter("resize", "fit")
           // .addQueryParameter("width", width.toString())
            //.addQueryParameter("height", height.toString())
            .build()

        // Create request
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Basic " + android.util.Base64.encodeToString(("xsMMW1rQRfGNy2LwGCbjfZMgVXV1S1fh").toByteArray(), android.util.Base64.NO_WRAP))
            //.header("Authorization", "Basic xsMMW1rQRfGNy2LwGCbjfZMgVXV1S1fh")
            .post(
                RequestBody.create(
                    "application/json".toMediaTypeOrNull(),
                    "{\"source\": {\"url\": \"$imageUrl\"}}"
                )
            )
            .build()

        // Execute the request
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.bytes()
                    if (responseData != null) {
                        //saveImageToFile(responseData, outputFile)
                        //callback(true)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Timber.e(e.message)
            }
        })
    }
*/
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}