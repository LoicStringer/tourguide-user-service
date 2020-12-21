package com.tourguideuserservice.mapper;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import com.tourguideuserservice.form.UserTripPreferencesForm;
import com.tourguideuserservice.model.UserTripPreferences;

@Component
public class UserTripPreferencesMapper {

	public UserTripPreferences mapUserTripPreferencesFormBeanToModel(UserTripPreferencesForm userTripPreferencesForm) {
		UserTripPreferences userTripPreferences = new UserTripPreferences();
		CurrencyUnit userTripPreferencesCurrency = Monetary.getCurrency(userTripPreferencesForm.getCurrency());
		userTripPreferences.setAttractionProximity(userTripPreferencesForm.getAttractionProximity());
		userTripPreferences.setCurrency(userTripPreferencesCurrency);
		userTripPreferences.setLowerPricePoint(Money.of(userTripPreferencesForm.getLowerPricePoint(),userTripPreferencesCurrency));
		userTripPreferences.setHighPricePoint(Money.of(userTripPreferencesForm.getHighPricePoint(), userTripPreferencesCurrency));
		userTripPreferences.setTripDuration(userTripPreferencesForm.getTripDuration());
		userTripPreferences.setNumberOfAdults(userTripPreferencesForm.getNumberOfAdults());
		userTripPreferences.setNumberOfChildren(userTripPreferencesForm.getNumberOfChildren());
		userTripPreferences.setTicketQuantity(userTripPreferencesForm.getTicketQuantity());
		return userTripPreferences;
	}
	
}
