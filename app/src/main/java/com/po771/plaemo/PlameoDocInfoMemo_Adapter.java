package com.po771.plaemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.item.Item_memo;

import java.util.List;

public class PlameoDocInfoMemo_Adapter extends RecyclerView.Adapter<PlameoDocInfoMemo_Adapter.ViewHolder> {

    private List<Item_memo> items;
    private Context context;

    public PlameoDocInfoMemo_Adapter(List<Item_memo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        context = viewGroup.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.plaemobookmemolist_item, viewGroup, false);

        Log.d("qweqwe","qweqwe");
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView page_start;
        private TextView content;
        private TextView date;
        private ImageView imageView;
//        private Button edit;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.bookimg);
            //imageView.setImageResource(R.drawable.book1);
            page_start = itemView.findViewById(R.id.memopage);
            content = itemView.findViewById(R.id.memocontent);
            date = itemView.findViewById(R.id.memodate);
//            edit = itemView.findViewById(R.id.memo_edit);

            content.setOnClickListener(this);
//            edit.setOnClickListener(this);
        }

        public void setItem(Item_memo item){
            page_start.setText(item.getPage_start());
            content.setText(item.getContent());
            date.setText(item.getDate());
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.memocontent){
                if(content.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
                    content.getLayoutParams().height = 128;
                }else {
                    content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                content.requestLayout();
            }
//            if (v.getId() == R.id.memo_edit){
//                Intent intent = new Intent(v.getContext(), PlaemoEditMemoActivity.class);
////                intent.putExtra("start_page",(item_book.get_id()));
////                intent.putExtra("end_page",);
////                intent.putExtra("memo_title",);
////                intent.putExtra("memo_content", );
//                v.getContext().startActivity(intent);
//            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PlameoDocInfoMemo_Adapter.ViewHolder viewHolder, int position) {
        Item_memo item = items.get(position);
        viewHolder.imageView.setImageResource(R.drawable.book1);
        viewHolder.page_start.setText("p."+String.valueOf(item.getPage_start())+" ~ p."+String.valueOf(item.getPage_end()));
        viewHolder.content.setText(item.getContent());
        viewHolder.date.setText(item.getDate());
        //viewHolder.setItem(item);

    }
}