package com.eynav.hackathonbullyguard;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordingAdapter  extends RecyclerView.Adapter<RecordingAdapter.RecordingAdapterHolder>{
    Context context;
    List<Recording> recordings;
    public RecordingAdapter(Context context, List<Recording> recordings) {
        this.context = context;
        this.recordings = recordings;
    }

    @NonNull
    @Override
    public RecordingAdapter.RecordingAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recording_card_view,parent,false);
        return new RecordingAdapter.RecordingAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordingAdapter.RecordingAdapterHolder holder, int position) {
        Recording recording = recordings.get(position);
        holder.recording = recording;
        String text = recording.getDate() + "  " + recording.getHour();
        holder.tvHallName.setText(text);
        String text2 = "רמת הבריונות שזוהתה: " +String.valueOf(recording.getLevel());
        holder.tvHallArea.setText(text2);
        String textCountPeople = "כמות הפעמיים שההקלטה נשמעה: " + recording.getAmountHearing();
        holder.tvHallCountPeople.setText(textCountPeople);

        holder.imageView.setOnClickListener(l ->{

        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPopupWindow(view,"ניתן לשמוע את ההקלטה פעמיים, אחרי זה יתאפשר רק לשלוח");

            }
        });
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPopupWindow(view,"הרמה היא 1-3, כאשר 1 היא הרמה הנמוכה ביותר");

            }
        });
    }


    private void displayPopupWindow(View anchorView, String text) {
        PopupWindow popup = new PopupWindow(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.popup_content, null);
        TextView tvCaption = layout.findViewById(R.id.tvCaption);
        tvCaption.setText(text);
        popup.setContentView(layout);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
    }


    @Override
    public int getItemCount() {
        return this.recordings.size();
    }

    public class RecordingAdapterHolder extends RecyclerView.ViewHolder {
        Recording recording;
        TextView tvHallName, tvHallArea, tvHallCountPeople;
        LinearLayout cartRecording;
        ImageView imageView2, imageView;

        public RecordingAdapterHolder(View itemView) {
            super(itemView);
            tvHallName = itemView.findViewById(R.id.tvHallName);
            tvHallArea = itemView.findViewById(R.id.tvHallArea);
            tvHallCountPeople = itemView.findViewById(R.id.tvHallCountPeople);
            cartRecording = itemView.findViewById(R.id.cartRecording);
            imageView = itemView.findViewById(R.id.imageView);
            imageView2 = itemView.findViewById(R.id.imageView2);

            itemView.setOnClickListener((v) ->{

            });
        }
    }
}

