package c.rocky.websiteipfinder;


/*   created by ravi rocky ravindra   */
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.whois.WhoisClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.Thread.sleep;

public class Main4Activity extends AppCompatActivity {
TextView textview7;
StringBuilder sb;
private ProgressDialog progressDialog;
String hu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
textview7=(TextView)findViewById(R.id.textView7);
hu=getIntent().getExtras().getString("value");
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data please wait ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(10);
        progressDialog.show();
        final int totalProgressTime = 90;

        Toast.makeText(this, ""+hu, Toast.LENGTH_SHORT).show();

        Thread th1= new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    int jumpTime = 20;

                    URL url = new URL(hu);
                    URLConnection urlc = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            urlc.getInputStream(), "UTF-8"));
                    String inputLine;
                    sb = new StringBuilder();
                    while ((inputLine = in.readLine()) != null)
                        sb.append(inputLine);
                    in.close();

                    while(jumpTime < totalProgressTime) {
                        try {
                            sleep(300);
                            jumpTime += 3;
                            progressDialog.setProgress(jumpTime);

                            progressDialog.cancel();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textview7.setText(sb.toString());
                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        th1.start();

        Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show();

    }
}
