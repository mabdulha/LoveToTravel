package ie.com.lovetotravel.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ie.com.lovetotravel.R;

public class JournalViewHolder extends RecyclerView.ViewHolder {

    public ImageButton btnDelete;
    public ImageButton btnUpdate;

    public JournalViewHolder(@NonNull View itemView) {
        super(itemView);

        btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete);
        btnUpdate = (ImageButton) itemView.findViewById(R.id.btn_update);
    }

    public void setTitle (String title) {

        TextView jTitle = (TextView) itemView.findViewById(R.id.tv_card_title);
        jTitle.setText(title);
    }

    public void setEntry (String entry) {

        TextView jEntry = (TextView) itemView.findViewById(R.id.tv_card_entry);
        jEntry.setText(entry);
    }

    public void setDate (String date) {

        TextView jDate = (TextView) itemView.findViewById(R.id.tv_card_date);
        jDate.setText(date);
    }
}
