package msku.ceng.madlab.week9;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText txtUrl;
    Button btnDownload;
    ImageView imgView;
    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSION_STORAGE={
            Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        StrictMode.ThreadPolicy policy=new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        txtUrl=findViewById(R.id.txtUrl);
        btnDownload=findViewById(R.id.btnDownload);
        imgView=findViewById(R.id.imgView);





        btnDownload.setOnClickListener(new View(View.OnClickListener) {
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSION_STORAGE, REQUEST_EXTERNAL, REQUEST_EXTERNAL_STORAGE);
                }
            }

            /*String fileName = "temp;jpg"
            String imagePath =
                    Enviroment.getExternalStoragePublicDirectory(Envioment.DIRECTORY_DOWNLOAD);

            downloadFile(txtUrl.get.Text().toString(),imagePath);


            preview(imagePath)*/

            else{
               /* DownloadTask backgroundTask = new DownloadTask();
                String[] urls = new String[1];
                urls[0]=txtUrl.getText().toString();
                backgroundTask.execute(urls);

*/
                Thread backgroundThread=new Thread(DownloadRunnable(txtUrl.getText().toString()));
                backgroundThread.start();
            }
        }


        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            /*String fileName = "temp;jpg";
            String imagePath =
                    Enviroment.getExternalStoragePublicDirectory(Envioment.DIRECTORY_DOWNLOAD);

            downloadFile(txtUrl.get.Text().toString(), imagePath);


            preview(imagePath);*/

            DownloadTask backgroundTask = new DownloadTask();
            String[] urls = new String[1];
            urls[0]=txtUrl.getText().toString();
            backgroundTask.execute(urls);

        }
        Thread backgroundThread=new Thread(DownloadRunnable(txtUrl.getText().toString()));
        backgroundThread.start();


        else{
            Toast.makeText(this,"External storage permission is not granted",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void preview(String imagePath){
        Bitmap image= BitmapFactory.decodeFile(imagePath);
        float imageWidth=image.getWidth();
        float imageHeight=image.getHeight();
        int rescaledWidth=400;
        int rescaledHeight=int()((imageHeight +rescaledWidth)/imageWidth);

        Bitmap bitmap=Bitmap.createScaledBitmap(image,rescaledWidth,rescaledHeight);

    }

    private void downloadFile(String url,String imagePath){
        try{
            URL strUrl=new URL(url);
            URLConnection connection=strUrl.openConnection();
            connection.connect();

            InputStream inputStream= new BufferedInputStream(strUrl.openStream(),8192);
            OutputStream outputStream= new FileOutputStream(imagePath);

            byte data [] = new byte[1024];
            int count;
            while((count =inputStream.read(data)) !=-1){
                outputStream.write(data,0,count);

            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    private Bitmap rescaleBitmap(String imagepath){
        float w = image.getWidth();
        float h = image.getHeight();
        int W = 400;
        int H = (int) ( (h*W)/w);
        Bitmap bitmap = Bitmap.createScaledBitmap(image, W, H, false);
        return bitmap;

    }

    class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
        ProgressDialog PD;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PD = new ProgressDialog(MainActivity.this);
            PD.setMax(100);
            PD.setIndeterminate(false);
            PD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            PD.setTitle("Downloading");
            PD.setMessage("Please wait..");
            PD.show();
        }
        protected void onProgressUpdate(Integer... progress) {
            PD.setProgress(progress[1]);
        }
        protected  void onPostExecute() {

        }


        @Override
        protected Bitmap doInBackground( String... urls) {
            String fileName = "temp.jpg";
            String imagePath = (Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS)).toString()
                    + "/" + fileName;
            downloadFile(urls[0],imagePath);
            return rescaleBitmap(this imagePath + );
            float w = image.getWidth();
            float h = image.getHeight();
            int W = 400;
            int H = (int) ( (h*W)/w);
            Bitmap b = Bitmap.createScaledBitmap(image, W, H, false);
            return b;
        }
        @Override
        protected void onPostExecute(Bitmap b) {
            imgView.setImageBitmap(b);
        }
    }

    class DownloadRunnable implements Runnable {
    String url;
    public  DownloadRunnable(String url){
        this.url=url;



        @Override
        public void run(){
            String fileName= "temp.jpg";
            String imagePath =  Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS).toString()
                    + "/" + fileName;
            Bitmap bitmap = rescaleBitmap(imagePath+"/"+fileName);

            runOnUiThread(new UpDateBitmap(bitmap));

        }

        class UpdateBitmap implements Runnable{
            Bitmap bitmap;

            public UpdateBitmap(Bitmap bitmap) {
                this.bitmap = bitmap;
            }

            @Override
            public void run() {
                imgView.setImageBitmap(bitmap);

            }
        }


    }



    }

