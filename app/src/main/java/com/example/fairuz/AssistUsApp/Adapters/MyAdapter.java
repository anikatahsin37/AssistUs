package com.example.fairuz.AssistUsApp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fairuz.AssistUsApp.Activities.TaskMainActivity;
import com.example.fairuz.AssistUsApp.R;
import com.example.fairuz.AssistUsApp.Utility.RecyclerItem;
import com.example.fairuz.AssistUsApp.Utility.TaskManagementEdit;

import java.util.List;

import static com.example.fairuz.AssistUsApp.Activities.MainOption.theme;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.IconDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskAlarmBeforeDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskDescriptionDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskNewArlarmTimeDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskTimeDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskTitleDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.TaskTuneDir;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.dltFlag;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedIcon;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskAlarmBefore;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskDescription;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskTime;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskTitle;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedTaskTune;
import static com.example.fairuz.AssistUsApp.Activities.TaskMainActivity.savedtaskNewAlarmTime;
import static com.example.fairuz.AssistUsApp.Utility.SaveAndLoad.Save;

/**
 * Created by anika on 9/1/17.
 * copyright stackoverflow.com, youtube.com
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<RecyclerItem> listItems;
    private Context mcontext;
    public static String CurrentyDeletedTaskTime = "";


    public MyAdapter(List<RecyclerItem> listItems, TaskMainActivity mcontext) {
        this.listItems = listItems;
        this.mcontext = mcontext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
       holder.txtDescription.setText(itemList.getDescription());


        if(savedIcon.get(position).toString().equals("call"))
        {
           holder.image.setImageResource(R.drawable.call);
        }


        if(savedIcon.get(position).toString().equals("calender"))
        {
            holder.image.setImageResource(R.drawable.calender);
        }

        if(savedIcon.get(position).toString().equals("cake"))
        {
            holder.image.setImageResource(R.drawable.cake);
        }
        if(savedIcon.get(position).toString().equals("medical"))
        {
            holder.image.setImageResource(R.drawable.medical);
        }

        if(savedIcon.get(position).toString().equals("gift"))
        {
            holder.image.setImageResource(R.drawable.gift);
        }

        if(savedIcon.get(position).toString().equals("travel"))
        {
            holder.image.setImageResource(R.drawable.travel);
        }
        if(savedIcon.get(position).toString().equals("shopping"))
        {
            holder.image.setImageResource(R.drawable.shopping);
        }

        if(theme==1) {
            holder.Rlayout.setBackgroundResource(R.color.white);
            holder.txtTitle.setTextColor(Color.parseColor("#384248"));
            holder.txtDescription.setTextColor(Color.parseColor("#3d5655"));
            }
        holder.Rlayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View view) {

                //Display option menu
                PopupMenu popupMenu = new PopupMenu(mcontext, holder.Rlayout);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnu_item_edit:

                                Intent intent = new Intent(view.getContext(), TaskManagementEdit.class);
                                intent.putExtra("position", "" + position);
                                view.getContext().startActivity(intent);


                                notifyDataSetChanged();
                                break;


                            case R.id.mnu_item_delete:
                                listItems.remove(position);
                                CurrentyDeletedTaskTime = savedTaskTime.get(position);
                                savedTaskTitle.remove(position);
                                savedTaskTime.remove(position);
                                savedTaskDescription.remove(position);
                                savedTaskTune.remove(position);
                                savedtaskNewAlarmTime.remove(position);
                                savedTaskAlarmBefore.remove(position);
                                savedIcon.remove(position);
                                Save(TaskTitleDir, savedTaskTitle);
                                Save(TaskTimeDir, savedTaskTime);
                                Save(TaskDescriptionDir, savedTaskDescription);
                                Save(TaskTuneDir, savedTaskTune);
                                Save(TaskNewArlarmTimeDir, savedtaskNewAlarmTime);
                                Save(TaskAlarmBeforeDir, savedTaskAlarmBefore);
                                Save(IconDir,savedIcon);
                                notifyDataSetChanged();
                                intent = new Intent(view.getContext(), TaskMainActivity.class);
                                intent.putExtra("deletedTime", CurrentyDeletedTaskTime);
                                dltFlag = 1;
                                view.getContext().startActivity(intent);
                                Toast.makeText(mcontext, "Task Deleted Successfully", Toast.LENGTH_LONG).show();


                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtDescription;
        public RelativeLayout Rlayout;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            Rlayout = (RelativeLayout) itemView.findViewById(R.id.Rlayout);
            image = (ImageView) itemView.findViewById(R.id.Image);


        }
    }


}
