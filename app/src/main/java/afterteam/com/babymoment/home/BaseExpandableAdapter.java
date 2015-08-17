package afterteam.com.babymoment.home;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.model.Action;
import afterteam.com.babymoment.utils.TimeUtils;


public class BaseExpandableAdapter extends BaseExpandableListAdapter{
	
	private ArrayList<ArrayList<String>> groupList = null;
	private ArrayList<ArrayList<Action>> childList = null;
	private LayoutInflater inflater = null;
	private ViewHolder viewHolder = null;
	
	public BaseExpandableAdapter(Context c, ArrayList<ArrayList<String>> groupList,
			ArrayList<ArrayList<Action>> childList){
		super();
		this.inflater = LayoutInflater.from(c);
		this.groupList = groupList;
		this.childList = childList;
	}
	
	// 그룹 포지션을 반환한다.
	@Override
	public String getGroup(int groupPosition) {
		return groupList.get(groupPosition).get(0);
	}

	// 그룹 사이즈를 반환한다.
	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	// 그룹 ID를 반환한다.
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// 그룹뷰 각각의 ROW 
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.layout_home_expendablelist, parent, false);
			viewHolder.tv_groupName = (TextView) v.findViewById(R.id.tv_group);
			viewHolder.tv_groupSumMedicine = (TextView) v.findViewById(R.id.tv_sum_medicine);
			viewHolder.tv_groupSumSleep = (TextView) v.findViewById(R.id.tv_sum_sleep);
			viewHolder.tv_groupSumDiaper = (TextView) v.findViewById(R.id.tv_sum_diaper);
			viewHolder.tv_groupSumFeed = (TextView) v.findViewById(R.id.tv_sum_feed);

			viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}
		
		// 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
		if (isExpanded) {
			viewHolder.iv_image.setImageResource(R.drawable.icon_accodion_arrow_down);
		} else {
			viewHolder.iv_image.setImageResource(R.drawable.icon_accodion_arrow_left_white);
		}

		String groupTitle = getGroup(groupPosition);
		viewHolder.tv_groupName.setText(groupTitle);


		ArrayList<Action> child = childList.get(groupPosition);

		// temporary count summery
		viewHolder.tv_groupSumMedicine.setText(getCount(groupPosition, 1));
		viewHolder.tv_groupSumSleep.setText(getCount(groupPosition, 2));
		viewHolder.tv_groupSumDiaper.setText(getCount(groupPosition, 3));
		viewHolder.tv_groupSumFeed.setText(getCount(groupPosition, 4));

		return v;
	}
	
	// 차일드뷰를 반환한다.
	@Override
	public Action getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}
	
	// 차일드뷰 사이즈를 반환한다.
	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	// 차일드뷰 ID를 반환한다.
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// 차일드뷰 각각의 ROW
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		if(v == null){
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.layout_home_action_detail, null);
			viewHolder.iv_action_icon = (ImageView) v.findViewById(R.id.iv_action_icon);
			viewHolder.tv_action_count = (TextView) v.findViewById(R.id.tv_action_count);
			viewHolder.tv_action_time = (TextView) v.findViewById(R.id.tv_action_time);
			viewHolder.tv_action_detail = (TextView) v.findViewById(R.id.tv_action_detail);
			viewHolder.iv_action_photo = (ImageView) v.findViewById(R.id.iv_action_photo);
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)v.getTag();
		}

		Action action = getChild(groupPosition, childPosition);
		TimeUtils timeUtils = new TimeUtils();

		viewHolder.tv_action_count.setText(Integer.toString(action.getCount()));
		viewHolder.tv_action_time.setText(timeUtils.getStringTime(action.getTime()));
		viewHolder.tv_action_detail.setText(action.getDetail());

		setActionIcon(action.getType());
		setActionPhoto(action.getPhoto());

		return v;
	}

	@Override
	public boolean hasStableIds() {	return true; }

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }
	
	class ViewHolder {
		public ImageView iv_image;
		public TextView tv_groupName;
		public TextView tv_groupSumMedicine;
		public TextView tv_groupSumSleep;
		public TextView tv_groupSumDiaper;
		public TextView tv_groupSumFeed;


		public ImageView iv_action_icon;
		public TextView tv_action_count;
		public TextView tv_action_time;
		public TextView tv_action_detail;
		public ImageView iv_action_photo;
	}

	public String getCount(int groupPosition, int type) {
		ArrayList<String> temp = groupList.get(groupPosition);
		return groupList.get(groupPosition).get(type);
	}

	private void setActionIcon(int type) {
		switch (type) {
			case 1:
				viewHolder.iv_action_icon.setImageResource(R.drawable.icon_medicine_sky);
				break;
			case 2:
				viewHolder.iv_action_icon.setImageResource(R.drawable.icon_sleep_green);
				break;
			case 3:
				viewHolder.iv_action_icon.setImageResource(R.drawable.icon_diaper_orange);
				break;
			case 4:
				viewHolder.iv_action_icon.setImageResource(R.drawable.icon_feed_scarlet);
				break;
			default:
				break;
		}
	}

	// temporary photo resource from res/drawable
	private void setActionPhoto(String photoType) {

		switch (Integer.parseInt(photoType)) {
			case 1:
				viewHolder.iv_action_photo.setImageResource(R.drawable.icon_writer1);
				break;
			case 2:
				viewHolder.iv_action_photo.setImageResource(R.drawable.icon_writer2);
				break;
			case 3:
				viewHolder.iv_action_photo.setImageResource(R.drawable.icon_writer3);
				break;
			default:
				break;
		}
	}

}




