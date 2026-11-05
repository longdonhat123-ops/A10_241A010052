package vn.edu.vhu.ltdd.a10gallery;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Màn hình xem ảnh lớn, vuốt ngang để chuyển ảnh.
 *
 * ViewPager2 là bản thay thế hiện đại cho widget Gallery (đã bỏ từ API 16)
 * và cho ViewPager đời đầu. Bên trong nó chính là một RecyclerView nằm ngang,
 * nên nó dùng luôn RecyclerView.Adapter — bạn không phải học thêm API mới.
 */
public class ViewerActivity extends AppCompatActivity {

    private ViewPager2 pager;
    private TextView tvChiSo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        pager = findViewById(R.id.pager);
        tvChiSo = findViewById(R.id.tvChiSo);

        pager.setAdapter(new AnhPagerAdapter(MainActivity.ANH));

        // Nhảy thẳng tới ảnh người dùng đã bấm; false = không chạy hiệu ứng trượt
        int viTri = getIntent().getIntExtra(MainActivity.EXTRA_VI_TRI, 0);
        pager.setCurrentItem(viTri, false);
        capNhatChiSo(viTri);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                capNhatChiSo(position);
            }
        });
    }

    private void capNhatChiSo(int viTri) {
        tvChiSo.setText(getString(R.string.chi_so, viTri + 1, MainActivity.ANH.length));
    }
}
