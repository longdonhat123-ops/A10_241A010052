package vn.edu.vhu.ltdd.a10gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter cho RecyclerView.
 *
 * So với BaseAdapter của ListView (Lab A9):
 *   - ViewHolder là BẮT BUỘC, không còn chuyện quên tái sử dụng View.
 *   - Ba phương thức rõ ràng: onCreateViewHolder (tạo ô), onBindViewHolder (đổ dữ liệu),
 *     getItemCount (đếm).
 *   - Việc sắp xếp ô do LayoutManager lo, adapter không cần biết là danh sách hay lưới.
 */
public class AnhAdapter extends RecyclerView.Adapter<AnhAdapter.AnhViewHolder> {

    /** Giao diện callback để Activity xử lý sự kiện bấm — adapter không tự mở màn hình. */
    public interface OnAnhClickListener {
        void onAnhClick(int viTri);
    }

    static class AnhViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgAnh;
        final TextView tvChuThich;

        AnhViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            tvChuThich = itemView.findViewById(R.id.tvChuThich);
        }
    }

    private final int[] anh;
    private final List<String> chuThich;
    private final OnAnhClickListener listener;

    public AnhAdapter(int[] anh, List<String> chuThich, OnAnhClickListener listener) {
        this.anh = anh;
        this.chuThich = chuThich;
        this.listener = listener;
    }

    /** Được gọi khi cần MỘT ô mới. Số lần gọi chỉ bằng số ô nhìn thấy, không phải số ảnh. */
    @NonNull
    @Override
    public AnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anh, parent, false);
        return new AnhViewHolder(v);
    }

    /** Được gọi mỗi khi một ô cần hiển thị dữ liệu của vị trí mới. */
    @Override
    public void onBindViewHolder(@NonNull AnhViewHolder holder, int position) {
        holder.imgAnh.setImageResource(anh[position]);
        holder.tvChuThich.setText(chuThich.get(position));

        // Dùng getBindingAdapterPosition(), KHÔNG dùng biến position,
        // vì vị trí có thể đã đổi khi người dùng bấm.
        holder.itemView.setOnClickListener(v -> {
            int p = holder.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) listener.onAnhClick(p);
        });
    }

    @Override
    public int getItemCount() {
        return anh.length;
    }
}
