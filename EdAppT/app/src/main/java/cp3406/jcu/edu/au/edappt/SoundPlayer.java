package cp3406.jcu.edu.au.edappt;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

//this class helps play correct and wrong sounds
public class SoundPlayer {
    private static SoundPool soundPool;
    private static int correct;
    private static int wrong;


    public SoundPlayer(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        correct = soundPool.load(context, R.raw.correct, 1);
        wrong = soundPool.load(context, R.raw.wrong, 1);
    }

    public void playCorrect(Boolean gameSound) {
        if (gameSound) {
            soundPool.play(correct, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void playWrong(Boolean gameSound) {
        if (gameSound) {
            soundPool.play(wrong, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }
}
