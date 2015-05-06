package me.geso.iarea;

import java.util.List;
import java.util.Optional;

public interface IAreaRepository {
	/**
	 * Get list of regions
	 */
	List<String> getRegions();

	/**
	 * Get list of prefectures
	 */
	List<String> getPrefectures();

	/**
	 * Find IArea object by area code.
	 *
	 * @param areaCode area code
	 * @return iArea object.
	 */
	Optional<IArea> findByAreaCode(String areaCode);

	/**
	 * Get all iArea objects.
	 *
	 * @return get all iArea objects
	 */
	List<IArea> getAll();
}
