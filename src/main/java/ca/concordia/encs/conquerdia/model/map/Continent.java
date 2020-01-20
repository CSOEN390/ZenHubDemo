package ca.concordia.encs.conquerdia.model.map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Continent in the world map of the game.
 * <p>
 * A Continent has a set of countries
 * A Continent belongs to a Map
 */
public class Continent implements Serializable {
	private final Map<String, Country> countries = new HashMap<>();
	private String name;
	private int value;

	/**
	 * private constructor to force developer to use the builder
	 */
	private Continent() {
	}

	/**
	 * @return all Countries Name
	 */
	public final Set<String> getCountriesName() {
		return countries.keySet();
	}

	/**
	 * @return all Countries
	 */
	public final Set<Country> getCountries() {
		return countries.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
	}

	/**
	 * Add a country to this continent
	 *
	 * @param country country
	 */
	public void addCountry(Country country) {
		countries.put(country.getName(), country);
	}

	/**
	 * Remove a country from Continent
	 *
	 * @param countryName countryName
	 */
	public void removeCountry(String countryName) {
		countries.remove(countryName);
	}

	/**
	 * @return Return the name of this Continent.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return return the value of the Continent
	 */
	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Continent continent = (Continent) o;

		return new EqualsBuilder()
				.append(name, continent.name)
				.isEquals();
	}

	/**
	 * This method implements the showmap result for the continent and it's
	 * including countries;
	 *
	 * @return showMap string for the continent and the countries which it include.
	 */
	@Override
	public String toString() {
		Set<Country> countries = this.getCountries();
		StringBuilder showMapConinentResult = new StringBuilder();
		showMapConinentResult.append(this.getName()).append(": includes the following countries:\n");
		for (Country country : countries) {
			showMapConinentResult.append(country.toString()).append(",");
		}
		showMapConinentResult.deleteCharAt(showMapConinentResult.length() - 1);
		showMapConinentResult.append("\n");
		return showMapConinentResult.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(name)
				.toHashCode();
	}

	/**
	 * Checks if this continent has any country associated with it
	 * 
	 * @return true if the continent does not have any countries otherwise false
	 */
	public boolean isEmpty() {
		return countries.isEmpty();
	}

	/**
	 * The Builder for {@link Continent}
	 */
	public static class Builder {
		private final Continent continent = new Continent();

		/**
		 * Continent-Builder's constructor has one parameter because the name for a
		 * continent is required.
		 *
		 * @param name The name of continent that is required for creation of a
		 *             continent
		 */
		public Builder(String name) {
			this.continent.name = name;
//            continent.worldMap = worldMap;
		}

		/**
		 * @param value continent value
		 * @return current builder object
		 */
		public Builder setValue(int value) {
			this.continent.value = value;
			return this;
		}

		/**
		 * @return return the continent that is built by this builder
		 */
		public Continent build() {
			return this.continent;
		}
	}
}
