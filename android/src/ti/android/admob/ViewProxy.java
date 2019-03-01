/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.android.admob;

import ti.android.admob.AdmobView;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

@Kroll.proxy(creatableInModule = AdmobModule.class)
public class ViewProxy extends TiViewProxy {
	
	private AdmobView adMob;

	public ViewProxy() {
		super();
	}

	@Override
	protected KrollDict getLangConversionTable() {
		KrollDict table = new KrollDict();
		table.put("title", "titleid");
		return table;
	}

	@Override
	public TiUIView createView(Activity activity) {
		this.adMob = new AdmobView(this);
		return this.adMob;
	}

	// Handle creation options
	@Override
	public void handleCreationDict(KrollDict options) {
		super.handleCreationDict(options);

		if (options.containsKey((Object)"adUnitId")) {
            AdmobModule.AD_UNIT_ID = options.getString("adUnitId");
        }
		
		if (options.containsKey((Object)"publisherId")) {
            AdmobModule.PUBLISHER_ID = options.getString("publisherId");
        }
		
		if (options.containsKey((Object)"testDeviceId")) {
            AdmobModule.TEST_DEVICE_ID = options.getString("testDeviceId");
        }
	}
	
	@Kroll.method
	public void showAdNow() {
		this.adMob.showAdNow();
	}

	@Kroll.method
	public void destoyAdViewAndCancelRequest() {
		this.adMob.destroy();
	}
	
	@Kroll.method
	public void loadRewardedAd() {
		this.adMob.loadRewardedAdVideo();
	}

	@Override
	public void onPause(Activity activity) {
	}

	@Override
	public void onResume(Activity activity) {
	}

	@Override
	public void onStart(Activity activity) {
	}

	@Override
	public void onStop(Activity activity) {
	}

	@Override
	public void onDestroy(Activity activity) {
		this.adMob.destroy();
	}
}