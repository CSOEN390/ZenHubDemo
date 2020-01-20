package ca.concordia.encs.conquerdia.model;

import ca.concordia.encs.conquerdia.util.Observable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Result for the command
 */
public class CommandResultModel extends Observable {
	/**
	 * Singleton holder for the command
	 */
	private static CommandResultModel instance;
	/**
	 * Result of the command
	 */
	private final List<String> resultList = new ArrayList<>();

	/**
	 * Constructor set private for singleton design pattern for Command Result Model
	 */
	private CommandResultModel() {
	}

	/**
	 * @return The instance of the result model Singleton class.
	 */
	public static CommandResultModel getInstance() {
		if (instance == null) {
			synchronized (CommandResultModel.class) {
				if (instance == null) {
					instance = new CommandResultModel();
				}
			}
		}
		return instance;
	}

	/**
	 * Clear this model
	 */
	public static void clearModel() {
		instance = null;
	}

	/**
	 * Clear this model
	 */
	public void clear() {
		resultList.clear();
	}

	/**
	 * @param resultList The list of result to be added to this model
	 */
	public void addResultList(List<String> resultList) {
		if (resultList != null && !resultList.isEmpty()) {
			for (String result : resultList) {
				if (StringUtils.isNotBlank(result)) {
					this.resultList.add(result);
				}
			}
			setChanged();
			notifyObservers(this);
		}
	}

	/**
	 * @param result The result to be added to this model
	 */
	public void addResult(String result) {
		if (!StringUtils.isBlank(result)) {
			this.resultList.add(result);
			setChanged();
			notifyObservers(this);
		}
	}

	/**
	 * @return result list
	 */
	public List<String> getResultList() {
		return resultList;
	}
}
