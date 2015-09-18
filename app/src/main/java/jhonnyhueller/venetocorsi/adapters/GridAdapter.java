package jhonnyhueller.venetocorsi.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.models.CourseCell;
import jhonnyhueller.venetocorsi.models.CourseCellInformation;

/**
 * Created by jhonny
 */
public class GridAdapter extends RecyclerView.Adapter<CourseCell>{
    private final String TAG = getClass().getSimpleName();
    private ArrayList<CourseCellInformation> cells;

    public GridAdapter(ArrayList<CourseCellInformation> cells) {
        this.cells = cells;
    }

    public GridAdapter(){}

    @Override
    public CourseCell onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_cell,parent,false);
        CourseCell cell=new CourseCell(v);
        return cell;
    }

    @Override
    public void onBindViewHolder(CourseCell holder, int position) {
        holder.getTitle().setText(cells.get(position).getTitle());
        holder.getIcon().setImageResource(cells.get(position).getId_icon());
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
