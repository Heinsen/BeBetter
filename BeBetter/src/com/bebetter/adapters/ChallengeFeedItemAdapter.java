package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bebetter.R;
import com.bebetter.domainmodel.Challenge; 

public class ChallengeFeedItemAdapter extends ArrayAdapter<Challenge>{

	List<Challenge> mChallengeFeedItems;
	Context mContext;
	
	public ChallengeFeedItemAdapter(Context context, int resource, List<Challenge> ChallengeFeedItems) {
		super(context, resource, ChallengeFeedItems);
		mChallengeFeedItems = new ArrayList<Challenge>();
		mChallengeFeedItems.addAll(ChallengeFeedItems);
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		ChallengeFeedItemViewHolder viewHolder;
		if (convertView == null) {
			//LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//convertView = inflater.inflate(R.layout.item_adapter_challenge_feed, null);
			
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_challenge_feed, null);
			viewHolder = new ChallengeFeedItemViewHolder();
			viewHolder.headLineTextView = (TextView) convertView.findViewById(R.id.item_adapter_challenge_feed_text_view_head_line);
			viewHolder.firstImageView = (ImageView) convertView.findViewById(R.id.item_adapter_challenge_feed_image_view_1);
			viewHolder.secondImageView = (ImageView) convertView.findViewById(R.id.item_adapter_challenge_feed_image_view_2);
		}
		else{
			viewHolder = (ChallengeFeedItemViewHolder) convertView.getTag();
		}
		
		Challenge challenge = getItem(position);
		
		viewHolder.headLineTextView.setText(challenge.getHeadlineText());
		viewHolder.firstImageView.setImageResource(challenge.getPictures().get(0));
		viewHolder.secondImageView.setImageResource(challenge.getPictures().get(1));

		
		return convertView;
	}

}
