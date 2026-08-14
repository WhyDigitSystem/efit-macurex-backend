package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

@Service
public class AmountInWordsConverterServiceImpl implements AmountInWordsConverterService {

	private static final String[] units = {
			"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
			"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
			"Seventeen", "Eighteen", "Nineteen"
	};

	private static final String[] tens = {
			"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy",
			"Eighty", "Ninety"
	};

	private static String convertBelowThousand(int number) {

		if (number < 20) {
			return units[number];

		} else if (number < 100) {
			return tens[number / 10]
					+ (number % 10 != 0 ? " " + units[number % 10] : "");

		} else {
			return units[number / 100] + " Hundred"
					+ (number % 100 != 0
							? " " + convertBelowThousand(number % 100)
							: "");
		}
	}

	@Override
	public String convert(BigDecimal amount) {

		if (amount == null) {
			return "Zero Only";
		}

		// Round amount to 2 decimal places
		amount = amount.setScale(2, RoundingMode.HALF_UP);

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return "Zero Only";
		}

		StringBuilder result = new StringBuilder();

		// Integer/Rupees part
		long number = amount.longValue();

		int crore = (int) (number / 1_00_00_000);
		number %= 1_00_00_000;

		int lakh = (int) (number / 1_00_000);
		number %= 1_00_000;

		int thousand = (int) (number / 1_000);
		number %= 1_000;

		int hundred = (int) number;

		// Crore
		if (crore > 0) {
			result.append(convertBelowThousand(crore))
					.append(" Crore ");
		}

		// Lakh
		if (lakh > 0) {
			result.append(convertBelowThousand(lakh))
					.append(" Lakh ");
		}

		// Thousand
		if (thousand > 0) {
			result.append(convertBelowThousand(thousand))
					.append(" Thousand ");
		}

		// Hundred / remaining amount
		if (hundred > 0) {
			result.append(convertBelowThousand(hundred));
		}

		String amountInWords = result.toString().trim();

		// Paise calculation
		BigDecimal paise = amount
				.remainder(BigDecimal.ONE)
				.movePointRight(2);

		int paiseInt = paise.intValue();

		if (paiseInt > 0) {

			String paiseInWords = convertBelowThousand(paiseInt);

			if (!amountInWords.isEmpty()) {
				amountInWords += " and ";
			}

			amountInWords += paiseInWords + " Paise";
		}

		return amountInWords + " Only";
	}
}