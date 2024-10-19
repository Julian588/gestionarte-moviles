package co.com.example.gestionarte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_movimientos, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems (List<ListElement> items) {mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCard, descriptionCard, priceCard, dateCard;

        ViewHolder(View itemView) {
            super(itemView);
            nombreCard = itemView.findViewById(R.id.nameTextView);
            descriptionCard = itemView.findViewById(R.id.descriptionTextView);
            priceCard = itemView.findViewById(R.id.cantidadTextView);
            dateCard = itemView.findViewById(R.id.dateTextView);
        }

        void bindData(final ListElement item){
            nombreCard.setText(item.getNombreCard());
            descriptionCard.setText(item.getDescriptionCard());
            priceCard.setText(item.getPriceCard());
            dateCard.setText(item.getDateCard());
        }
    }

 }
