package vn.edu.vhu.ltdd.a10gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // TODO: thay 2201234567 bằng MSSV của bạn
    private static final String TAG = "A10_2201234567";

    public static final String EXTRA_VI_TRI = "extra_vi_tri";

    /** Danh sách ảnh dùng chung cho cả hai màn hình. */
    public static final int[] ANH = {
            R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4,
            R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8,
    };

    private final List<String> chuThich = new ArrayList<>();
    private AnhAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        for (int i = 0; i < ANH.length; i++) {
            chuThich.add(getString(R.string.anh_so, i + 1));
        }

        RecyclerView rv = findViewById(R.id.rvAnh);

        // ---- LayoutManager quyết định CÁCH SẮP XẾP các ô ----
        // GridLayoutManager(this, 3)  → lưới 3 cột   (thay cho GridView cũ)
        // LinearLayoutManager(this)   → danh sách dọc (thay cho ListView cũ)
        // StaggeredGridLayoutManager  → lưới so le, ô cao thấp khác nhau
        rv.setLayoutManager(new GridLayoutManager(this, 3));

        // Kích thước mỗi ô không đổi → bật tối ưu này cho cuộn mượt hơn
        rv.setHasFixedSize(true);

        adapter = new AnhAdapter(ANH, chuThich, viTri -> {
            Log.d(TAG, "Mở ảnh lớn tại vị trí " + viTri);
            Intent i = new Intent(this, ViewerActivity.class);
            i.putExtra(EXTRA_VI_TRI, viTri);
            startActivity(i);
        });
        rv.setAdapter(adapter);

        findViewById(R.id.btnDoiCot).setOnClickListener(v -> doiSoCot(rv));
    }

    /** Đổi qua lại giữa 2, 3 và 4 cột để thấy LayoutManager làm việc gì. */
    private void doiSoCot(RecyclerView rv) {
        GridLayoutManager lm = (GridLayoutManager) rv.getLayoutManager();
        if (lm == null) return;
        int soCot = lm.getSpanCount() + 1;
        if (soCot > 4) soCot = 2;
        lm.setSpanCount(soCot);
        // Số cột đổi → mọi ô phải được vẽ lại
        adapter.notifyDataSetChanged();
        Toast.makeText(this, getString(R.string.so_cot, soCot), Toast.LENGTH_SHORT).show();
    }
}
