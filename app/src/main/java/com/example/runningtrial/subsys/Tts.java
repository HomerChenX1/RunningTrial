package com.example.runningtrial.subsys;

import android.content.Context;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;

public class Tts implements android.speech.tts.TextToSpeech.OnInitListener {
    private final String TAG = getClass().getSimpleName();
    private android.speech.tts.TextToSpeech mTextToSpeech;
    private Locale locale = Locale.TAIWAN;  // 不要用 Locale.ENGLISH, 會預設用英文(印度)
    private boolean isLoaded = false;

    public Tts(Context context) {
        this.mTextToSpeech = new android.speech.tts.TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == android.speech.tts.TextToSpeech.SUCCESS) {
            isLoaded = true;

            if (mTextToSpeech.isLanguageAvailable(locale) == android.speech.tts.TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                // language available
                mTextToSpeech.setLanguage(locale);
                mTextToSpeech.setPitch(1);    //語調(1為正常語調；0.5比正常語調低一倍；2比正常語調高一倍)
                mTextToSpeech.setSpeechRate(1);    //速度(1為正常速度；0.5比正常速度慢一倍；2比正常速度快一倍)
                mTextToSpeech.setOnUtteranceProgressListener(listener);
            } else isLoaded = false;
        } else isLoaded = false;
    }

    public void close(){
        if(mTextToSpeech != null)
        {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

    // 發音任務將被添加到當前任務列隊之後
    public void queueSpeak(String text, String utteranceId) {
        if (isLoaded) {
            // utteranceId = (text.length() >2 ) ? text.substring(0, 2): text;
            mTextToSpeech.speak(text, android.speech.tts.TextToSpeech.QUEUE_ADD,null,utteranceId);
        }
    }
    // 發音任務將被添加到當前任務列隊之後
    public void queueSpeak(String text) {
        queueSpeak(text, null);
    }


    // 會中斷當前實例正在運行的任務
    public void flushSpeak(String text, String utteranceId) {
        if (isLoaded) {
            // utteranceId = (text.length() >2 ) ? text.substring(0, 2): text;
            mTextToSpeech.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH,null, utteranceId);
        }
    }
    public void flushSpeak(String text) {
        flushSpeak(text, null);
    }

    private final UtteranceProgressListener listener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
            Log.d(TAG, "TTS start: " + utteranceId);
        }

        @Override
        public void onDone(String utteranceId) {
            Log.d(TAG, "TTS complete: " + utteranceId);
            // close();
        }

        @Override
        public void onError(String utteranceId) {
            Log.d(TAG, "TTS error: " + utteranceId);
        }
    };
}
