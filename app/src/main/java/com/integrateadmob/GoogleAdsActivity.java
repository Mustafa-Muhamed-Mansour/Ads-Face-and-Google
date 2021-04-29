package com.integrateadmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class GoogleAdsActivity extends AppCompatActivity
{

    private AdView adView;

    private AdRequest adRequest;

    private InterstitialAd ad;

    private String videoInterstitialAdKey = "ca-app-pub-3940256099942544/8691691433"; //Unit ID [Test]

    private RewardedAd mRewardedAd;

    private TextView textViewScore;
    private Button buttonAds;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_ads);

        adView = findViewById(R.id.ad_view_banner);

        textViewScore = findViewById(R.id.txt_score);
        buttonAds = findViewById(R.id.btn_ads);
        buttonAds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showRewdedAd();
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener()
        {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus)
            {
                loadRewardedAd();
            }
        });


    }

    private void videoInterstitialAdLoader(Context context, String key, AdRequest request)
    {
        InterstitialAd.load(context, key, request, new InterstitialAdLoadCallback()
        {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd)
            {
                super.onAdLoaded(interstitialAd);

                ad = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
            {
                super.onAdFailedToLoad(loadAdError);

                Toast.makeText(getApplicationContext(), loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRewardedAd()
    {
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        videoInterstitialAdLoader(this, videoInterstitialAdKey, adRequest);
        RewardedAd.load(this, "App. Unit ID", adRequest, new RewardedAdLoadCallback()
                {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
                    {
                        Toast.makeText(GoogleAdsActivity.this, loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd)
                    {
                        mRewardedAd = rewardedAd;

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback()
                        {
                            @Override
                            public void onAdShowedFullScreenContent()
                            {
                                Toast.makeText(GoogleAdsActivity.this, "جاري زيادة النتيجة", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError)
                            {
                                Toast.makeText(GoogleAdsActivity.this, adError.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent()
                            {
                                Toast.makeText(GoogleAdsActivity.this, "تم زيادة النتيجة بنجاح", Toast.LENGTH_SHORT).show();
                                loadRewardedAd();
                            }
                        });
                    }
                });

    }

    private void showRewdedAd()
    {
        if (mRewardedAd != null)
        {
            mRewardedAd.show(this, new OnUserEarnedRewardListener()
            {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem)
                {
                  /*  int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType(); */

                    int increaseTheScore = Integer.parseInt(textViewScore.getText().toString());
                    textViewScore.setText(String.valueOf(increaseTheScore + 5));
                }
            });
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        if (ad != null)
        {
            ad.show(GoogleAdsActivity.this);
        }

    }
}