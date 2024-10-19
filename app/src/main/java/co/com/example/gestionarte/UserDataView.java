package co.com.example.gestionarte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class UserDataView extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("usuarios");

    Button btnVolverDashboardDataView;
    TextView textViewName;
    TextView textViewId;
    TextView textViewFullName;
    TextView textViewPhone;
    TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_data_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolverDashboardDataView = findViewById(R.id.BdataView_volver);
        textViewName = findViewById(R.id.ETdataView_name);
        textViewId = findViewById(R.id.TVdataView_id);
        textViewFullName = findViewById(R.id.TVdataView_fullName);
        textViewPhone = findViewById(R.id.TVdataView_phone);
        textViewEmail = findViewById(R.id.TVdataView_email);

        btnVolverDashboardDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverDashboard();
            }
        });

        recuperarDataUsuario();

    }

    public void volverDashboard () {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void recuperarDataUsuario () {
        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioActual != null){
            String userId = usuarioActual.getUid();

            reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("nombre").getValue(String.class);
                        String lastName = snapshot.child("apellido").getValue(String.class);
                        String phone = snapshot.child("telefono").getValue(String.class);
                        String email = snapshot.child("correo").getValue(String.class);
                        String id = snapshot.child("id").getValue(String.class);
                        String fullName = name + " " + lastName;

                        textViewId.setText(id);
                        textViewName.setText(name);
                        textViewFullName.setText(fullName);
                        textViewPhone.setText(phone);
                        textViewEmail.setText(email);
                    } else {
                        Toast.makeText(UserDataView.this, "Datos no encontrados", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UserDataView.this, "Error al recuperar los datos", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No hay un usuario autenticado", Toast.LENGTH_SHORT).show();
        }
    }
}