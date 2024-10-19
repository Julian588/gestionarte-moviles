package co.com.example.gestionarte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    Button btnVerInfoUsuario;
    Button btnRegistrarGasto;
    Button btnMovimientos;
    TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVerInfoUsuario = findViewById(R.id.Bdashboard_info);
        btnRegistrarGasto = findViewById(R.id.Bdashboard_registrarGasto);
        btnMovimientos = findViewById(R.id.Bdashboard_movimientos);
        textViewName = findViewById(R.id.TVdashboard_name);

        mostrarNombre(textViewName);

        btnVerInfoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irVerInfoUsuario();
            }
        });

        btnRegistrarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irRegistrarGasto();
            }
        });

        btnMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irMovimientos();
            }
        });
    }

    public void irVerInfoUsuario () {
        Intent intent = new Intent(this, UserDataView.class);
        startActivity(intent);
    }

    public void irRegistrarGasto () {
        Intent intent = new Intent(this, RegistrarGasto.class);
        startActivity(intent);
    }

    public void irMovimientos () {
        Intent intent = new Intent(this, MovimientosGastos.class);
        startActivity(intent);
    }

    public void mostrarNombre(TextView textView) {
        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioActual != null) {
            String userId = usuarioActual.getUid();
            DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(userId);

            usuarioRef.child("nombre").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String nombre = snapshot.getValue(String.class);
                        textView.setText(nombre);
                    } else {
                        textView.setText("Nombre no encontrado");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    textView.setText("Error al cargar el nombre");
                }
            });
        } else {
            textView.setText("No hay usuario autenticado");
        }
    }
}