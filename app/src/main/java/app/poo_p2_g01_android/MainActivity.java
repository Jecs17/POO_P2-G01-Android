package app.poo_p2_g01_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView cardViewCategoria, cardViewIngreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cardViewCategoria = (CardView) findViewById(R.id.cvCategoria);
        cardViewIngreso = (CardView) findViewById(R.id.cvIngresos);
        cardViewCategoria.setOnClickListener(this);
        cardViewIngreso.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View view) {
        Intent i;
        int vistaId = view.getId();
        if(vistaId == R.id.cvCategoria){
            i = new Intent(this, CategoryActivity.class);
            startActivity(i);
        }else if(vistaId == R.id.cvIngresos) {
            i = new Intent(this, IngresoActivity.class);
            startActivity(i);
        }
    }
}
