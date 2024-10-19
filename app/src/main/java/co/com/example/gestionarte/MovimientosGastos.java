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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovimientosGastos extends AppCompatActivity {
    List<ListElement> elements;
    Button btnVolverDashboardMovimientos;
    TextView textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movimientos_gastos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolverDashboardMovimientos = findViewById(R.id.Bmovimientos_volver);
        textViewTotal = findViewById(R.id.TVmovimientos_total);
        init();

        btnVolverDashboardMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrDashboardMovimientos();
            }
        });
    }

    public void IrDashboardMovimientos () {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void init(){
        elements = new ArrayList<>();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("gastos").child(userId);

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.getDefault());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                elements.clear();
                double gastoTotal = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String nombre = snapshot.child("nombre").getValue(String.class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    double monto = Double.parseDouble(snapshot.child("monto").getValue(String.class));
                    String fecha = snapshot.child("fecha").getValue(String.class);

                    String montoFormatted = formatoMoneda.format(monto);
                    gastoTotal += monto;

                    elements.add(new ListElement(nombre, descripcion, montoFormatted, fecha));
                }

                String gastoTotalFormatted = formatoMoneda.format(gastoTotal);
                textViewTotal.setText(gastoTotalFormatted);

                ListAdapter listAdapter = new ListAdapter(elements, MovimientosGastos.this);
                RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MovimientosGastos.this));
                recyclerView.setAdapter(listAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de Firebase
            }
        });

    }
}

