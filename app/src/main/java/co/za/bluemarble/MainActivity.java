package co.za.bluemarble;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.za.bluemarble.features.GetAllImages.GetAllInfoActivity;
import co.za.bluemarble.features.common.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_enhanced)
    Button btn_enhanced;
    @BindView(R.id.btn_natrual) Button btn_natrual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_enhanced)
    public void getEnhancedImages() {
        loadEnhancedData();
    }

    private void loadEnhancedData() {

        Intent intent = new Intent(this, GetAllInfoActivity.class);
        startActivity(intent);
    }

}






