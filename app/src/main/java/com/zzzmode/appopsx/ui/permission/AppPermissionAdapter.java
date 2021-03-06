package com.zzzmode.appopsx.ui.permission;

import android.app.AppOpsManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zzzmode.appopsx.R;
import com.zzzmode.appopsx.ui.model.OpEntryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2016/11/18.
 */

public class AppPermissionAdapter extends RecyclerView.Adapter<AppPermissionAdapter.ViewHolder> implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    private List<OpEntryInfo> datas=new ArrayList<>();

    private OnSwitchItemClickListener listener;

    public void setDatas(List<OpEntryInfo> datas) {
        this.datas = datas;
    }

    public void updateItem(OpEntryInfo info){
        if(datas != null && info != null){
            int i = datas.indexOf(info);
            if(i != -1 && i < datas.size()){
                notifyItemChanged(i);
            }
        }
    }

    public void setListener(OnSwitchItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_permission_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OpEntryInfo opEntryInfo = datas.get(position);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(holder);


        holder.switchCompat.setTag(opEntryInfo);
        if(opEntryInfo != null){
            if(opEntryInfo.opPermsLab != null){
                holder.title.setText(opEntryInfo.opPermsLab);
            }else {
                holder.title.setText(opEntryInfo.opName);
            }
            if(opEntryInfo.opPermsDesc != null){
                holder.summary.setVisibility(View.VISIBLE);
                holder.summary.setText(opEntryInfo.opPermsDesc);
            }else {
                holder.summary.setVisibility(View.GONE);
            }
            if(opEntryInfo.opEntry.getTime() != 0) {
                StringBuilder sb = new StringBuilder("time:");
                TimeUtils.formatDuration(System.currentTimeMillis() - opEntryInfo.opEntry.getTime(),sb);
                sb.append(" ago");
                holder.lastTime.setVisibility(View.VISIBLE);
                holder.lastTime.setText(sb.toString());
            }else {
                holder.lastTime.setVisibility(View.GONE);
            }
            int mode=opEntryInfo.opEntry.getMode();

            holder.switchCompat.setOnCheckedChangeListener(null);
            switch (mode){
                case AppOpsManager.MODE_ALLOWED:
                    holder.switchCompat.setChecked(true);
                    break;
                default:
                    holder.switchCompat.setChecked(false);
            }

            holder.switchCompat.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        if(v.getTag() instanceof ViewHolder){
            ((ViewHolder) v.getTag()).switchCompat.toggle();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getTag() instanceof OpEntryInfo && listener != null){
            listener.onSwitch(((OpEntryInfo) buttonView.getTag()),isChecked);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView summary;
        TextView lastTime;
        SwitchCompat switchCompat;

        public ViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(android.R.id.title);
            summary= (TextView) itemView.findViewById(android.R.id.summary);
            lastTime= (TextView) itemView.findViewById(R.id.last_time);
            switchCompat= (SwitchCompat) itemView.findViewById(R.id.switch_compat);

        }
    }

    public interface OnSwitchItemClickListener{
        void onSwitch(OpEntryInfo info,boolean v);
    }

}
