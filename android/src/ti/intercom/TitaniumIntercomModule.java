/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2018 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.intercom;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;

import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.UserAttributes;
import io.intercom.android.sdk.identity.Registration;
import io.intercom.android.sdk.push.IntercomPushClient;

@Kroll.module(name="TitaniumIntercom", id="ti.intercom")
public class TitaniumIntercomModule extends KrollModule {

	private IntercomPushClient pushClient = new IntercomPushClient();

	@Kroll.method
	public void configure(KrollDict params) {
		String apiKey = params.getString("apiKey");
		String appId = params.getString("appId");

		Intercom.initialize(TiApplication.getAppRootOrCurrentActivity().getApplication(), apiKey, appId);
	}

	@Kroll.setProperty
	public void setVisible(boolean visible) {
		Intercom.client().setLauncherVisibility(visible ? Intercom.Visibility.VISIBLE : Intercom.Visibility.GONE);
	}

	@Kroll.method
	public void registerUser(KrollDict user) {
		if (user == null) {
			Intercom.client().registerUnidentifiedUser();
			return;
		}

		String userId = user.getString("identifier");
		String email = user.getString("email");
		Registration userRegistration = Registration.create().withUserId(userId);

		if (email != null) {
			userRegistration = userRegistration.withEmail(email);
		}

		Intercom.client().registerIdentifiedUser(userRegistration);
	}

	@Kroll.method
    public void updateUser(KrollDict user) {
		String email = user.getString("email");
		String name = user.getString("name");
		String locale = user.getString("locale");
		KrollDict customAttributes = user.getKrollDict("customAttributes");

		UserAttributes.Builder userAttributes = new UserAttributes.Builder();

		if (email != null) {
			userAttributes = userAttributes.withEmail(email);
		}

		if (name != null) {
			userAttributes = userAttributes.withName(name);
		}

		if (locale != null) {
			userAttributes = userAttributes.withLanguageOverride(locale);
		}

		if (customAttributes != null) {
			userAttributes = userAttributes.withCustomAttributes(customAttributes);
		}

		Intercom.client().updateUser(userAttributes.build());
	}

	@Kroll.method
	public void logout() {
		Intercom.client().logout();
	}

	@Kroll.method
	public void presentMessenger(String message) {
		Intercom.client().displayMessageComposer(message != null ? message : "");
	}

	@Kroll.method
	public void presentCarousel(String carouselId) {
		Intercom.client().displayCarousel(carouselId);
	}

	@Kroll.method
	public void presentHelpCenter() {
		Intercom.client().displayHelpCenter();
	}

	@Kroll.method
	public void updatePushToken(String pushToken) {
		if (pushToken == null) {
			pushToken = ""; // Reset token to empty string if deleted
		}

		pushClient.sendTokenToIntercom(TiApplication.getInstance(), pushToken);
	}
}

