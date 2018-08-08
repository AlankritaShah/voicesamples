package alankrita.in.voicesamples;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.ViewHolder>
        implements Filterable {

    private Context context;
    public List<String> barcodeInventoryList;
    public List<String> barcodeInventoryListFiltered;
    private SpeakersAdapterListener barcodeListAdapterListener;


    public SpeakersAdapter(Context context,
                              List<String> barcodeInventoryList,
                              SpeakersAdapterListener barcodeListAdapterListener) {
        this.barcodeInventoryList = barcodeInventoryList;
        this.context = context;
        this.barcodeInventoryListFiltered = barcodeInventoryList;
        this.barcodeListAdapterListener = barcodeListAdapterListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView barcodeNumberText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            barcodeNumberText = mView.findViewById(R.id.users_list_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barcodeListAdapterListener.onSpeakerSelected(barcodeInventoryListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.username, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String user = barcodeInventoryListFiltered.get(position);
        holder.barcodeNumberText.setText(user);
    }

    @Override
    public int getItemCount() {
        return barcodeInventoryListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    barcodeInventoryListFiltered = barcodeInventoryList;
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (String row : barcodeInventoryList) {
                        if (row.toLowerCase().contains(charString.toLowerCase()) || row.contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    barcodeInventoryListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = barcodeInventoryListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                barcodeInventoryListFiltered = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface SpeakersAdapterListener{
        void onSpeakerSelected(String str);
    }
}
