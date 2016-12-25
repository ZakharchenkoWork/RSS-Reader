/*
 * Copyright (C) 2011 Mats Hofman <http://matshofman.nl/contact/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zakharchenko.postindustria.rest;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RssItem implements Comparable<RssItem>, Parcelable {

	private RssFeed feed;
	private String title;
	private String link;
	private String image="";
	private Date pubDate;
	private String description;
	private String content;

	public RssItem() {
		
	}
	
	public RssItem(Parcel source) {
		
		Bundle data = source.readBundle();
		title = data.getString("title");
		link = data.getString("link");
		image = data.getString("img");
		pubDate = (Date) data.getSerializable("pubDate");
		description = data.getString("description");
		content = data.getString("content");
		feed = data.getParcelable("feed");
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		Bundle data = new Bundle();
		data.putString("title", title);
		data.putString("link", link);
		data.putSerializable("pubDate", pubDate);
		data.putString("description", description);
		data.putString("content", content);
		data.putString("img", image);
		data.putParcelable("feed", feed);
		dest.writeBundle(data);
	}
	
	public static final Creator<RssItem> CREATOR = new Creator<RssItem>() {
		public RssItem createFromParcel(Parcel data) {
			return new RssItem(data);
		}
		public RssItem[] newArray(int size) {
			return new RssItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
	
	public RssFeed getFeed() {
		return feed;
	}

	public void setFeed(RssFeed feed) {
		this.feed = feed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link)
    {
		this.link = link;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public void setPubDate(String pubDate) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			this.pubDate = dateFormat.parse(pubDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {

        Log.d("description ", "" + description);
		Log.d("description parts", "" + description.split("<|>").length);
        String realDescription = "";

        String[] parts = description.split("<|>");
        for (int i = 0; i < parts.length; i++) {
            Log.d("part", "" + description.split("<|>")[i]);
            if(parts[i].contains("img src=\"")){

                String data = parts[i].split("img src=\"")[1];
                for (int j = 0; j < data.length(); j++) {
                    if (data.charAt(j) == '\"') {
                     break;
                    }
                    image += data.charAt(j);
                    }
            }
            else {
                if( !parts[i].equals("") && !parts[i].equals("p") && !parts[i].equals("/p") &&  ! parts[i].contains("div class=") && !parts[i].equals("/div") && !parts[i].equals("strong") && !parts[i].equals("/strong") && !parts[i].contains("em class=") && !parts[i].equals("/em") && !parts[i].contains("a href=\""))
                realDescription += "<"+parts[i]+">";
            }
        }
        setImage(image);

		this.description = realDescription;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int compareTo(RssItem another) {
		if(getPubDate() != null && another.getPubDate() != null) {
			return getPubDate().compareTo(another.getPubDate());
		} else { 
			return 0;
		}
	}
	
}
