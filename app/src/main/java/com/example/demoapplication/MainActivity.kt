package com.example.demoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.demoapplication.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
const val GAME_LENGTH_MILLISECONDS = 3000L
//id is different
const val AD_UNIT_ID = "ca-app-pub-7106981806500353/7793354177"
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var interstitialAd: InterstitialAd? = null
    private var countdownTimer: CountDownTimer? = null
    private var TAG = "MainActivity"
    private var adIsLoading: Boolean = false
    private var timerMilliseconds = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adRequest= AdRequest.Builder().build()
        val adRequest1= AdRequest.Builder().build()

        binding.addView1.loadAd(adRequest1)
        binding.addView2.loadAd(adRequest)
        loadInterAd()

    }

    private fun loadInterAd() {
        val adRequst= AdRequest.Builder().build()

        InterstitialAd.load(this, AD_UNIT_ID,adRequst,object: InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError.message.let{
                    Log.d(TAG,it)
                }
                interstitialAd = null
                adIsLoading=false
                val error =
                    "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
                Toast.makeText(this@MainActivity, "onAdFailedToLoad() with error $error", Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                interstitialAd = ad
                adIsLoading = false
                Toast.makeText(this@MainActivity, "onAdLoaded()", Toast.LENGTH_SHORT).show()
            }
        }
        )
    }


    fun continueToApp(view: View) {
        showAds()
    }

    private fun showAds() {
        if (interstitialAd != null) {
            //loadInterAd()
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d(TAG, "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {

                    // Called when ad is dismissed.
                    Log.d(TAG, "Ad dismissed fullscreen content.")
                    interstitialAd = null

                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {

                    // Called when ad fails to show.
                    Log.e(TAG, "Ad failed to show fullscreen content.")
                    interstitialAd = null

                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad showed fullscreen content.")
                }

            }
            interstitialAd!!.show(this)
        } else {


        }
    }

    fun PaidNContinue() {

    }

    override fun onDestroy() {
        binding.addView1.destroy()
        binding.addView2.destroy()
        super.onDestroy()
    }
}
