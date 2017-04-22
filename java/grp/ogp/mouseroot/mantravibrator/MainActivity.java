package grp.ogp.mouseroot.mantravibrator;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mantraInput;
    private EditText txtSeconds;
    private SeekBar seekPulse;
    private Button btnVibrate;
    private TextView labelPulse;
    private Vibrator vibe;
    private boolean isRunning = false;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public long[] createMorseLetter(String s) {
        long[] letterTone = new long[5];
        switch(s.toLowerCase()) {
            case "a":

                break;

            case "b":
                break;

            case "c":
                break;

            case "d":
                break;

            case "e":
                break;

            case "f":
                break;

            case "g":
                break;

            case "h":
                letterTone = new long[4];
                letterTone[0] = 1;
                letterTone[1] = 1;
                letterTone[2] = 1;
                letterTone[3] = 1;
                break;

            case "i":
                letterTone = new long[2];
                letterTone[0] = 1;
                letterTone[1] = 1;
                break;

            case "j":
                break;

            case "k":
                break;

            case "l":
                break;

            case "m":
                break;

            case "n":
                break;

            case "o":
                break;

            case "p":
                break;

            case "q":
                break;

            case "r":
                break;

            case "s":
                break;

            case "t":
                break;

            case "u":
                break;

            case "v":
                break;

            case "w":
                break;

            case "x":
                break;

            case "y":
                break;

            case "z":
                break;

            case " ":
                break;

            case ".":
                break;

            case ",":
                break;


        }
        return letterTone;
    }
/*
    lol my code
    private long[] generateVibrations(String mantra, long delay) {
        int len = mantra.length();
        //Double the length becaues data is in pairs, +1 to create the pause or delay
        long[] returnData = new long[len*2+1];
        int c = 0;
        for(int i=0;i < len;i++) {
            int val = (int)mantra.charAt(i);
            returnData[c] = 1000;
            c++;
            returnData[c] = val * 2;
            c++;
        }
        returnData[returnData.length-1] = (delay * 1000);
        return returnData;
    }
*/
/*
    //Stolen from a geunius, modified by a rodent.
    private long[] generateVibrations(String mantra, long delay, int intensity) {
        int len = mantra.length();
        long[] returnData = new long[len*2 + 1];
        for(int i=0;i < len;++i) {
            System.out.println(mantra.charAt(i) + " " + i);
            int val = (int)mantra.charAt(i);
            returnData[2*i] = 1000 - (val + intensity);
            returnData[2*i + 1] = val * intensity;
        }
        returnData[len*2] = (delay * 1000);
        return returnData;
    }
    */
    private long[] generateVibrations(String mantra, long delay) {
        int len = mantra.length();
        long[] returnData;
        ArrayList<Long> listData = new ArrayList<Long>();
        for(int i=0;i < len;i++) {
            int val = (int)mantra.charAt(i);
            listData.add((long)1000 - val);
            listData.add((long)val * 2);
        }
        listData.add(delay * 1000);
        returnData = new long[listData.size()];
        for(int i=0;i < listData.size();i++) {
            returnData[i] = listData.get(i);
        }
        return returnData;

    }


    private long[] generateMorseCode(String mantra, long delay) {
        int len = mantra.length();
        ArrayList<Long> listData = new ArrayList<Long>();
        long[] returnData;
        //returnData[len*2] = (delay * 1000);
        for(int i=0;i < len;i++) {
            long[] morse = createMorseLetter(Character.toString(mantra.charAt(i)));


            for (long m : morse) {
                listData.add(100L);
                listData.add(m*10);
            }

        }
        listData.add(delay * 10);

        returnData = new long[listData.size()];
        for(int i=0;i < listData.size();i++) {
            returnData[i] = (Long)listData.get(i);
        }

        return returnData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Vibrator
        vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(!vibe.hasVibrator()) {
            Toast.makeText(this, "Device cannot vibrate",Toast.LENGTH_LONG).show();
        }

        //Inputs
        mantraInput = (EditText)findViewById(R.id.txtMantra);

        //How long to wait between each set
        txtSeconds = (EditText)findViewById(R.id.txtSeconds);

        //The button
        btnVibrate = (Button)findViewById(R.id.btnVibrate);

        //Btn Click
        btnVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int delay;
                if(isRunning) {
                    vibe.cancel();
                    isRunning = false;
                    btnVibrate.setText("Vibrate");
                }
                else {
                    isRunning = true;
                    btnVibrate.setText("Stop");

                    String mantra = mantraInput.getText().toString();
                    try {
                        delay = Integer.parseInt(txtSeconds.getText().toString());
                        delay = delay > 0 ? delay : 1;

                    } catch (NumberFormatException e) {
                        delay = 1;

                    }

                    long[] vibeData = generateVibrations(mantra,delay);
                    //long[] vibeData = {0,100,100,100,100,100,100,100,100,7000,100,100,100,100};
                    //long[] vibeData = generateMorseCode("hi",delay);
                    vibe.vibrate(vibeData, 0);
                    for(int i=0;i < vibeData.length;i++) {
                        System.out.println("Vibe: " + vibeData[i]);
                    }
                }


            }
        });

    }
}
