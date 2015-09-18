package jhonnyhueller.venetocorsi.models;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jhonnyhueller.venetocorsi.R;

/**
 * Created by jhonny
 */
public class CourseCell extends RecyclerView.ViewHolder {
    private final String TAG = getClass().getSimpleName();
    private CardView cardView;
    private TextView title;
    private ImageView icon;

    public CourseCell(View itemView) {
        super(itemView);
        title= (TextView) itemView.findViewById(R.id.textView_cell_title_course);
        icon= (ImageView) itemView.findViewById(R.id.imageView_course_cell_icon);
        cardView= (CardView) itemView.findViewById(R.id.card_view_course_cell);
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }
}
