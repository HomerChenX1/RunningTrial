package com.example.runningtrial.subsys;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;

/**
 *  watch.stdtime.gov.tw
 * 	time.stdtime.gov.tw
 * 	clock.stdtime.gov.tw
 * 	tick.stdtime.gov.tw
 */
public class SubSysTTS implements TextToSpeech.OnInitListener {
    private final String TAG = getClass().getSimpleName();
    private TextToSpeech mTextToSpeech;
    private Locale locale = Locale.US;  // 不要用 Locale.ENGLISH, 會預設用英文(印度)
    private boolean isLoaded = false;

    public SubSysTTS(Context context) {
        this.mTextToSpeech = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isLoaded = true;

            if (mTextToSpeech.isLanguageAvailable(locale) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                // language available
                mTextToSpeech.setLanguage(locale);
                mTextToSpeech.setPitch(1);    //語調(1為正常語調；0.5比正常語調低一倍；2比正常語調高一倍)
                mTextToSpeech.setSpeechRate(1);    //速度(1為正常速度；0.5比正常速度慢一倍；2比正常速度快一倍)
                mTextToSpeech.setOnUtteranceProgressListener(listener);
            } else isLoaded = false;
        } else isLoaded = false;
    }

    private void close(){
        if(mTextToSpeech != null)
        {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

    // 發音任務將被添加到當前任務列隊之後
    public void queueSpeak(String text) {
        if (isLoaded) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD,null,"UniqueID");
        }
    }

    // 會中斷當前實例正在運行的任務
    public void flushSpeak(String text) {
        if (isLoaded) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH,null,"UniqueID");
        }
    }

    private final UtteranceProgressListener listener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
            Log.d(TAG, "TTS start: " + utteranceId);
        }

        @Override
        public void onDone(String utteranceId) {
            Log.d(TAG, "TTS complete: " + utteranceId);
            close();
        }

        @Override
        public void onError(String utteranceId) {
            Log.d(TAG, "TTS error: " + utteranceId);
        }
    };
}
