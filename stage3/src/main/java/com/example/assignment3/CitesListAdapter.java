package com.example.assignment3;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.widget.Filter;


public class CitesListAdapter extends ArrayAdapter<Cites> implements Filterable {
    private ArrayList<Cites> cite;
    public ArrayList<Cites> selectedCites;
    private ArrayList<Cites> filteredNotes;
    private Filter filter;

    public CitesListAdapter(Context context, ArrayList<Cites> cites) {
        super(context,0,cites);
        this.cite = cites;
        this.selectedCites = new ArrayList<Cites>();
        this.filteredNotes = cites;
    }

    public Cites getItem(int position){
        return filteredNotes.get(position);
    }

    public int getCount() {
        return filteredNotes.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Cites cites = getItem(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listcites,parent,false);
        }
        CheckBox box = (CheckBox) convertView.findViewById(R.id.checkBox);
        box.setTag(position);
        TextView text = (TextView) convertView.findViewById(R.id.textView2);
        text.setText(cites.getName());
        TextView text_time = (TextView) convertView.findViewById(R.id.textView3);
        text_time.setText(cites.getTime().toString());
        box.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                if (box.isChecked()) {
                    selectedCites.add(filteredNotes.get(index.intValue()));
                }
                else{
                    selectedCites.remove(filteredNotes.get(index.intValue()));
                }
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new ListFilter();
        }
        return filter;
    }

    private class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0){
                ArrayList<Cites> filteredList = new ArrayList<Cites>();
                for(int i=0; i < cite.size(); i++){
                    if(cite.get(i).getName().contains(constraint)){
                        filteredList.add(cite.get(i));
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;

            }
            else{
                results.count = cite.size();
                results.values = cite;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredNotes = (ArrayList<Cites>) results.values;
            notifyDataSetChanged();
        }

    }
}
