package com.kentec.milkbox.tv.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class YoutubeURLUtil {
	static final String YOUTUBE_VIDEO_INFORMATION_URL = "http://www.youtube.com/get_video_info?&video_id=";
	static final String YOUTUBE_PLAYLIST_ATOM_FEED_URL = "http://gdata.youtube.com/feeds/api/playlists/";

	public static class Format {

		protected int mId;

		/**
		 * Construct this object from one of the strings in the "fmt_list" parameter
		 * 
		 * @param pFormatString
		 *            one of the comma separated strings in the "fmt_list" parameter
		 */
		public Format(String pFormatString) {
			String lFormatVars[] = pFormatString.split("/");
			mId = Integer.parseInt(lFormatVars[0]);
		}

		/**
		 * Construct this object using a format id
		 * 
		 * @param pId
		 *            id of this format
		 */
		public Format(int pId) {
			this.mId = pId;
		}

		/**
		 * Retrieve the id of this format
		 * 
		 * @return the id
		 */
		public int getId() {
			return mId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object pObject) {
			if (!(pObject instanceof Format)) {
				return false;
			}
			return ((Format) pObject).mId == mId;
		}
	}	
	
	public static class VideoStream {

		protected String mUrl;

		/**
		 * Construct a video stream from one of the strings obtained from the
		 * "url_encoded_fmt_stream_map" parameter if the video_info
		 * 
		 * @param pStreamStr
		 *            - one of the strings from "url_encoded_fmt_stream_map"
		 */
		public VideoStream(String pStreamStr) {
			String[] lArgs = pStreamStr.split("&");
			Map<String, String> lArgMap = new HashMap<String, String>();
			for (int i = 0; i < lArgs.length; i++) {
				String[] lArgValStrArr = lArgs[i].split("=");
				if (lArgValStrArr != null) {
					if (lArgValStrArr.length >= 2) {
						lArgMap.put(lArgValStrArr[0], lArgValStrArr[1]);
					}
				}
			}
			mUrl = lArgMap.get("url");
		}

		public String getUrl() {
			return mUrl;
		}
	}
	
	public static String calculateYouTubeUrl(String pYouTubeFmtQuality,
			boolean pFallback, String pYouTubeVideoId) throws IOException,
			ClientProtocolException, UnsupportedEncodingException {

		String lUriStr = null;
		HttpClient lClient = new DefaultHttpClient();

		HttpGet lGetMethod = new HttpGet(YOUTUBE_VIDEO_INFORMATION_URL
				+ pYouTubeVideoId);

		HttpResponse lResp = null;

		lResp = lClient.execute(lGetMethod);

		ByteArrayOutputStream lBOS = new ByteArrayOutputStream();
		String lInfoStr = null;

		lResp.getEntity().writeTo(lBOS);
		lInfoStr = new String(lBOS.toString("UTF-8"));

		String[] lArgs = lInfoStr.split("&");
		Map<String, String> lArgMap = new HashMap<String, String>();
		for (int i = 0; i < lArgs.length; i++) {
			String[] lArgValStrArr = lArgs[i].split("=");
			if (lArgValStrArr != null) {
				if (lArgValStrArr.length >= 2) {
					lArgMap.put(lArgValStrArr[0],
							URLDecoder.decode(lArgValStrArr[1]));
				}
			}
		}

		// Find out the URI string from the parameters

		// Populate the list of formats for the video
		String lFmtList = URLDecoder.decode(lArgMap.get("fmt_list"));
		ArrayList<Format> lFormats = new ArrayList<Format>();
		if (null != lFmtList) {
			String lFormatStrs[] = lFmtList.split(",");

			for (String lFormatStr : lFormatStrs) {
				Format lFormat = new Format(lFormatStr);
				lFormats.add(lFormat);
			}
		}

		// Populate the list of streams for the video
		String lStreamList = lArgMap.get("url_encoded_fmt_stream_map");
		if (null != lStreamList) {
			String lStreamStrs[] = lStreamList.split(",");
			ArrayList<VideoStream> lStreams = new ArrayList<VideoStream>();
			for (String lStreamStr : lStreamStrs) {
				VideoStream lStream = new VideoStream(lStreamStr);
				lStreams.add(lStream);
			}

			// Search for the given format in the list of video formats
			// if it is there, select the corresponding stream
			// otherwise if fallback is requested, check for next lower format
			int lFormatId = Integer.parseInt(pYouTubeFmtQuality);

			Format lSearchFormat = new Format(lFormatId);
			while (!lFormats.contains(lSearchFormat) && pFallback) {
				int lOldId = lSearchFormat.getId();
				int lNewId = getSupportedFallbackId(lOldId);

				if (lOldId == lNewId) {
					break;
				}
				lSearchFormat = new Format(lNewId);
			}

			int lIndex = lFormats.indexOf(lSearchFormat);
			if (lIndex >= 0) {
				VideoStream lSearchStream = lStreams.get(lIndex);
				lUriStr = lSearchStream.getUrl()
							.replace("%252C", ",").replace("%3F", "?").replace("%2F", "/").replace("%3A", ":").replace("%26", "&").replace("%3D", "=");
			}
		}
		
		// Return the URI string. It may be null if the format (or a fallback
		// format if enabled)
		// is not found in the list of formats for the video
		return lUriStr;
	}

	public static int getSupportedFallbackId(int pOldId) {
		final int lSupportedFormatIds[] = { 
				10,
				11,
				12,
				13, // 3GPP (MPEG-4 encoded) Low quality
				14,
				15,
				16,
				17, // 3GPP (MPEG-4 encoded) Medium quality
				18, // MP4 (H.264 encoded) Normal quality
				19,
				20,
				21,
				22, // MP4 (H.264 encoded) High quality
				23,
				24,
				25,
				26,
				27,
				28,
				29,
				30,
				31,
				32,
				33,
				34,
				35,
				36,
				37, // MP4 (H.264 encoded) High quality
				38,
				39,
				40,
				41,
				42,
				43,
				44,
				45,
				46,
				47,
				48
		};
		int lFallbackId = pOldId;
		for (int i = lSupportedFormatIds.length - 1; i >= 0; i--) {
			if (pOldId == lSupportedFormatIds[i] && i > 0) {
				lFallbackId = lSupportedFormatIds[i - 1];
			}
		}
		return lFallbackId;
	}
	
	public static String convert1(String link) {
		try {
			if (link==null || !link.contains("www.youtube.com"))
				return link;
			
			String pattern = "(?:videos\\/|v=)([\\w-]+)";
			Pattern compiledPattern = Pattern.compile(pattern);
			Matcher matcher = compiledPattern.matcher(link);
			String videoID = "";
			
			if (matcher.find()) {
				System.out.println(matcher.group().substring(2));
				videoID = matcher.group().substring(2);
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc1 = db.parse("http://gdata.youtube.com/feeds/mobile/videos/" + videoID);
			Element rsp = (Element) doc1.getElementsByTagName("media:content").item(1);

			String anotherurl = rsp.getAttribute("url");
			// Element rsp =
			// (Element)doc1.getElementsByTagName("media:content").item(1);
			
			return anotherurl;
		} catch (Exception e) {
			System.out.println("Pasing Excpetion = " + e);
		}
		
		return link;
	}	
	
	public static String convert(String link) {
		try {
			int pos = -1;
			final String lead = "www.youtube.com/watch?v=";
			if (link == null || ((pos = link.indexOf(lead)) == -1))
				return link;
			else {
				String youTubeVideoId = link.substring(pos + lead.length());
				return calculateYouTubeUrl("22", true, youTubeVideoId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return link;
		}
	}
	
	public static void main(String []args){
        try {
			System.out.println(calculateYouTubeUrl("37", true, "0LHKpaf8kAE"));
		} catch (Exception e) {
			e.printStackTrace();
		}
     }

}