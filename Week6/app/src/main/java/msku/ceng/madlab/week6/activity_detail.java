package msku.ceng.madlab.week6;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class activity_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail2);

        Movie movie=(Movie)getIntent().getSerializableExtra(name:"movie")
        FragmentTransaction fts= getSupportFragmentManager().beginTransaction();
        DetailsFragment df= DetailsFragment.newInstance(movie);
        fts.add(R.id.container,df);
        fts.commit()
        });
    }
}