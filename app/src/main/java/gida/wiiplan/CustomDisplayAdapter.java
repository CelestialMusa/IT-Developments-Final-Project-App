package gida.wiiplan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Zacharia Manyoni on 2016/11/07.
 */

public class CustomDisplayAdapter extends ArrayAdapter{
    private List list = new ArrayList();

    public CustomDisplayAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Object object) {
        list.add(object);
        super.add(object);
    }

    static class ImgHolder{
        ImageView IMG;
        TextView NAME;
        TextView DESCR;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        ImgHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout,parent,false);
            holder = new ImgHolder();
            holder.IMG = (ImageView) row.findViewById(R.id.item_img);
            holder.NAME = (TextView) row.findViewById(R.id.item_name);
            holder.DESCR= (TextView) row.findViewById(R.id.item_descr);
            row.setTag(holder);
        }
        else{
            row = convertView;
            holder = (ImgHolder) row.getTag();
        }

        Object obj = getItem(position);

        if(obj instanceof VenueClass){
            VenueClass vc = (VenueClass) obj;
            holder.IMG.setImageResource(vc.getVenue_resource());
            holder.NAME.setText(vc.getVenue_name());
            holder.DESCR.setText(vc.getBuilding()+"-"+vc.getRoom());
        }
        else if(obj instanceof ModuleClass){
            ModuleClass mc = (ModuleClass) obj;
            holder.IMG.setImageResource(mc.getModuleResource());
            holder.NAME.setText(mc.getModuleName());
            holder.DESCR.setText(mc.getModuleCode());
        }
        else if(obj instanceof User){
            User user = (User) obj;
            holder.NAME.setText(user.getFname()+" "+user.getLname());
            holder.DESCR.setText(user.getVarsity_num());
        }

        return row;
    }
}
