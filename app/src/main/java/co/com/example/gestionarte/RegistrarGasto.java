package co.com.example.gestionarte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarGasto extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("gastos");

    EditText inputName;
    EditText inputAmount;
    EditText inputDate;
    EditText inputDescription;
    Button btnRegistrarGasto;
    Button btnVolverRegistrarGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_gasto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputName = findViewById(R.id.ETregistrarGasto_name);
        inputAmount = findViewById(R.id.ETregistrarGasto_amount);
        inputDate = findViewById(R.id.ETregistrarGasto_date);
        inputDescription = findViewById(R.id.ETregistrarGasto_description);
        btnRegistrarGasto = findViewById(R.id.BregistroGasto_register);
        btnVolverRegistrarGasto = findViewById(R.id.BregistrarGasto_volver);

        btnVolverRegistrarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irDashboardRegistrarGasto();
            }
        });

        btnRegistrarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarGasto();
            }
        });

    }

    public void irDashboardRegistrarGasto () {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
    public void registrarGasto() {
        FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        String name = inputName.getText().toString().trim();
        String amount = inputAmount.getText().toString();
        String date = inputDate.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();

        if (usuarioActual == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        String idUsuario = usuarioActual.getUid();

        if (name.isEmpty() || amount.isEmpty() || date.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference usuarioGastosRef = reference.child(idUsuario);
        String idGasto = reference.push().getKey();

        Map<String, Object> gastoData = new HashMap<>();
        gastoData.put("nombre", name);
        gastoData.put("monto", amount);
        gastoData.put("fecha", date);
        gastoData.put("descripcion", description);

        if (idGasto != null) {
            usuarioGastosRef.child(idGasto).setValue(gastoData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Gasto registrado exitosamente", Toast.LENGTH_SHORT).show();

                            inputName.setText("");
                            inputAmount.setText("");
                            inputDate.setText("");
                            inputDescription.setText("");
                        } else {
                            Toast.makeText(this, "Error al registrar el gasto", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}