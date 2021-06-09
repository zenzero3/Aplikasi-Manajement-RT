package sistem.Smarta.grandcikarangcity2.rt.isibutton.penguruspack;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
private Context context;
private List<String> LisTitle;
private Map<String,List<String>> listItem;

    public CustomExpandableListAdapter(Context context, List<String> lisTitle, Map<String, List<String>> listItem) {
        this.context = context;
        LisTitle = lisTitle;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return LisTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return LisTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(LisTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String)getGroup(groupPosition);
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group,null);
        }
        TextView txttilte= convertView.findViewById(R.id.listtitle);
        txttilte.setTypeface(null, Typeface.BOLD);
        txttilte.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String)getChild(groupPosition,childPosition);
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        TextView txtchild= convertView.findViewById(R.id.listitem);
        txtchild.setTypeface(null, Typeface.BOLD);
        txtchild.setText(title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}