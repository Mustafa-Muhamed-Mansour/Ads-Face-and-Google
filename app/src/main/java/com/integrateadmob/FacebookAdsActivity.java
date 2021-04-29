package com.integrateadmob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class FacebookAdsActivity extends AppCompatActivity
{

    private AdView adView;

    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_ads);


        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer =  findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();

        AudienceNetworkAds.initialize(this);

        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener()
        {
            @Override
            public void onInterstitialDisplayed(Ad ad)
            {
            }

            @Override
            public void onInterstitialDismissed(Ad ad)
            {
            }

            @Override
            public void onError(Ad ad, AdError adError)
            {
                Toast.makeText(FacebookAdsActivity.this, adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad)
            {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad)
            {
            }

            @Override
            public void onLoggingImpression(Ad ad)
            {
            }
        };

        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
    }
}