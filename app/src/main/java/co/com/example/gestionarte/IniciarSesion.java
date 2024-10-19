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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

public class IniciarSesion extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    Button btnInciarSesion;
    Button btnVolverHomeInciarSesion;
    EditText inputCorreoSesion;
    EditText inputPasswordSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnInciarSesion = findViewById(R.id.BiniciarSesion_inciarSesion);
        btnVolverHomeInciarSesion = findViewById(R.id.BiniciarSesion_volver);
        inputCorreoSesion = findViewById(R.id.ETiniciarSesion_user);
        inputPasswordSesion = findViewById(R.id.ETiniciarSesion_password);

        btnVolverHomeInciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irHome();
            }
        });

        btnInciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo = inputCorreoSesion.getText().toString();
                String contrasena = inputPasswordSesion.getText().toString();

                inicarSesion(correo,contrasena);
            }
        });
    }

    public void irHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void irDashboardDesdeIncioSesion() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void inicarSesion(String email, String password){

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            irDashboardDesdeIncioSesion();

                            Toast.makeText(IniciarSesion.this, "Has Iniciado sesión", Toast.LENGTH_SHORT).show();
                            /*
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                        } else {

                            Toast.makeText(IniciarSesion.this, "Valide sus credenciales", Toast.LENGTH_SHORT).show();

                            /*
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);*/
                        }
                    }
                });
    }

}