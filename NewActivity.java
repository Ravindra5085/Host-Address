package c.rocky.websiteipfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.Thread.sleep;

/**
 * Created by rocky on 2/3/18.
 */

public class NewActivity extends AppCompatActivity
{

    WebView webView;
    TextView infoview,textView9;
    InetAddress ipp;
    EditText editText;
    String url,host,info,host1,sndtxt,ki;
    String ss;
    StringBuilder sb;
    URL url2;
    Button button,button4;
    private ProgressBar pbar;
    private TextView textView;
    private ProgressDialog progressDialog;
    private int progressCount;
    private Handler progressHandler = new Handler();
    private static Handler progressbarHandler;
    HttpURLConnection httpConn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.mm);
        infoview =(TextView)findViewById(R.id.infoview);
        ki= getIntent().getExtras().getString("value");
        sb = new StringBuilder("");
        progressDialog =new ProgressDialog(this);
                progressDialog.setMessage("fetching please wait...!! ");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(true);
                progressDialog.setProgress(10);
                progressDialog.show();
                final int totalProgressTime = 100;
                Toast.makeText(this, "Click again if not at all exist !!!", Toast.LENGTH_SHORT).show();
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            url2 = new URL(ki);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try
                        {
                            httpConn =  (HttpURLConnection)url2.openConnection();
                            httpConn.setInstanceFollowRedirects( false );
                            httpConn.setRequestMethod( "HEAD" );
                            httpConn.connect();
                        }
                        catch (NullPointerException ne)
                        {
                            ne.printStackTrace();
                        }
                        catch(java.net.ConnectException e){
                            e.printStackTrace();
                        }
                        catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            int jumpTime = 20;


                            host1 = url2.getHost();
                            ipp = InetAddress.getByName(host1);
                            host = ipp.getHostAddress();

                               ss= getDomainName(ki);

                            sb.append("Domain Name:"+ss);

                            sb.append(" \n\n Address :\t"+ host);
                            sb.append("\n\n Host:\t" + url2.getHost());
                            if (url2.getProtocol() != null) {
                                sb.append("\n\n Protocol:\t" + url2.getProtocol());
                            } else {sb.append("\n\n Protocol not find");}
                            sb.append("\n\n Port:\t" + url2.getPort());
                            sb.append("\n\n Default port :\t" + url2.getDefaultPort());
                            if (url2.getQuery() != null) {
                                sb.append("\n\n Query:\t" + url2.getQuery());
                            }
                            else {
                                sb.append("\n\n Query :not exist");}
                            sb.append("\n\n Server Response code:\t"+httpConn.getResponseCode());
                            sb.append("\n\n Authority:\t" + url2.getAuthority());
                            sb.append("\n\n Content:\t" + url2.getContent());
                            sb.append("\n\n User Info:\t" + url2.getUserInfo());
                            sb.append("\n\n Path:\t" + url2.getPath());
                            sb.append("\n\n Reference:\t" + url2.getRef());
                            sb.append("\n\n File :" + url2.getFile());

                            org.apache.commons.net.whois.WhoisClient whois = new org.apache.commons.net.whois.WhoisClient();
                            whois.connect("whois.icann.org", 43);

                            String domainWhois = whois.query(host1);

                            whois.disconnect();

                        sb.append(domainWhois);
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
                                    infoview.setText(sb.toString());
                                }
                            });

                        } catch (MalformedURLException my) {
                            my.printStackTrace();
                        } catch (IOException ioe) {
                            System.out.println(ioe);
                        } catch (IndexOutOfBoundsException ee) {
                            System.out.println(ee);
                        } catch (RuntimeException re) {
                            System.out.println(re);
                        } catch (Exception e) {
                            System.out.println(e);
                        } catch (Throwable t) {
                            System.out.println(t);
                        }

                    }
                });
                thread.start();

        Toast.makeText(this, "Request Accepted Please wait ", Toast.LENGTH_SHORT).show();
        }


    private boolean isNetworkAvailable() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

}


