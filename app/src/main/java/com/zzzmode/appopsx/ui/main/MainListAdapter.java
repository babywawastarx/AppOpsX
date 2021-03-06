package com.zzzmode.appopsx.ui.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzzmode.appopsx.R;
import com.zzzmode.appopsx.ui.model.AppInfo;
import com.zzzmode.appopsx.ui.permission.AppPermissionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2016/11/18.
 */

class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> implements View.OnClickListener{

    List<AppInfo> appInfos=new ArrayList<>();

    void addItem(AppInfo info){
        appInfos.add(info);
        notifyItemInserted(appInfos.size()-1);
    }

    void showItems(List<AppInfo> infos){
        appInfos.clear();
        if(infos != null){
            appInfos.addAll(infos);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_app,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppInfo appInfo = appInfos.get(position);
        holder.tvName.setText(appInfo.appName);
        if(appInfo.icon != null){
            holder.imgIcon.setImageDrawable(appInfo.icon);
        }else {
            holder.imgIcon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.itemView.setTag(appInfo);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    @Override
    public void onClick(View v) {
        if(v.getTag() instanceof AppInfo){

            Intent intent = new Intent(v.getContext(), AppPermissionActivity.class);
            intent.putExtra(AppPermissionActivity.EXTRA_APP, ((AppInfo) v.getTag()));
            v.getContext().startActivity(intent);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgIcon;
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgIcon= (ImageView) itemView.findViewById(R.id.app_icon);
            tvName= (TextView) itemView.findViewById(R.id.app_name);
        }
    }
}
