package pe.edu.cibertec.notificaciones;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CAMERA = 1;
    static final int REQUEST_TAKE_PICTURE = 2 ;

    String currentPathImage; // Ruta absoluta de la imagen
    Button btCamera;
    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCamera = findViewById(R.id.btCamera);
        ivPhoto = findViewById(R.id.ivPhoto);

        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });


    }

    private void takePicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Validar que la camara está disponible
        if (cameraIntent.resolveActivity(getPackageManager())!= null){

            // verificar que se disponga del permiso
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){

                requestPermission();


            } else{

                File photofile = null;
                try {
                    photofile = createImge();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                startActivityForResult(cameraIntent,REQUEST_TAKE_PICTURE);
            }
        }

    }

    private File createImge() throws IOException {
        // para crear el archivo , se genera primero un nombre,// pPARA CALCULAR FECHA Y AHORA DEL MOMENTO

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        //asignarle un directorio de almacenamiento
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //crear el archivo
        File image = File.createTempFile(
                imageFileName, // nombre
                ".jpg",
                storageDir) ;

        currentPathImage = image.getAbsolutePath();
        return image;
    }



    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent information) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PICTURE && resultCode == RESULT_OK){
            // Glide.with(this).load(currentPathImage).into(ivPhoto);
            Bitmap bitmap = (Bitmap) information.getExtras().get("data");
            ivPhoto.setImageBitmap(bitmap);
        }


    }
    // PARA GUARDAR UNA FOTO SE TIENE Q CREAR UN ARCHIVO
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode ==  REQUEST_CAMERA){
            if (//grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){ // SABER SI ESTAN DANDO PERMISOS
                Toast.makeText(MainActivity.this, "Se dio permiso", Toast.LENGTH_SHORT).show();

                takePicture();

            }
        }
    }
}