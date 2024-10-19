package co.com.example.gestionarte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("usuarios");
    FirebaseAuth auth = FirebaseAuth.getInstance();

    Button btnVolverHome;
    EditText inputIdUsuario;
    EditText inputNombreUsuario;
    EditText inputApellidoUsuario;
    EditText inputTelefonoUsuario;
    EditText inputCorreoUsuario;
    EditText inputContrasenaUsuario;
    Button btnGuardarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolverHome = findViewById(R.id.BRegistro_volver);
        inputIdUsuario = findViewById(R.id.ETregistro_id);
        inputNombreUsuario = findViewById(R.id.ETregistro_name);
        inputApellidoUsuario = findViewById(R.id.ETregistro_lastName);
        inputTelefonoUsuario = findViewById(R.id.ETregistro_phone);
        inputCorreoUsuario = findViewById(R.id.ETregistro_email);
        inputContrasenaUsuario = findViewById(R.id.ETregistro_password);
        btnGuardarUsuario = findViewById(R.id.Bregistro_register);

        btnGuardarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearUsuario();
            }
        });

        btnVolverHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irHome();
            }
        });
    }

    public void irHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void crearUsuario() {
        String idUsuario = inputIdUsuario.getText().toString().trim();
        String nombre = inputNombreUsuario.getText().toString().trim();
        String apellido = inputApellidoUsuario.getText().toString().trim();
        String correo = inputCorreoUsuario.getText().toString().trim();
        String telefono = inputTelefonoUsuario.getText().toString().trim();
        String contrasena = inputContrasenaUsuario.getText().toString().trim();

        if (correo.isEmpty() || contrasena.isEmpty() || idUsuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese correo y contraseña.", Toast.LENGTH_SHORT).show();
            return;
        }

        registerAuth(correo, contrasena, idUsuario, nombre, apellido, telefono);
    }

    public void registerAuth(String email, String password, String idUsuario, String nombre, String apellido, String telefono) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                // Guardamos los datos en la base de datos
                                DatabaseReference nuevoUsuario = reference.child(user.getUid());
                                nuevoUsuario.child("id").setValue(idUsuario);
                                nuevoUsuario.child("nombre").setValue(nombre);
                                nuevoUsuario.child("apellido").setValue(apellido);
                                nuevoUsuario.child("correo").setValue(email);
                                nuevoUsuario.child("telefono").setValue(telefono);

                                Toast.makeText(Registro.this, "Usuario Creado Exitosamente", Toast.LENGTH_LONG).show();
                                irHome();
                            }
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                            Toast.makeText(Registro.this, "Error al crear el usuario: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}