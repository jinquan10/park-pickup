// This software is released into the Public Domain.  See copying.txt for details.

package net.morbz.osmonaut.binary.pbf;

import net.morbz.osmonaut.osm.Entity;

import java.util.List;

/**
 * Stores the results for a decoded Blob.
 * 
 * @author Brett Henderson
 */
public class PbfBlobResult {
	private List<Entity> entities;
	private boolean complete;
	private boolean success;

	/**
	 * Creates a new instance.
	 */
	public PbfBlobResult() {
		complete = false;
		success = false;
	}

	/**
	 * Stores the results of a successful blob decoding operation.
	 * 
	 * @param decodedEntities
	 *            The entities from the blob.
	 */
	public void storeSuccessResult(List<Entity> decodedEntities) {
		entities = decodedEntities;
		complete = true;
		success = true;
	}

	/**
	 * Stores a failure result for a blob decoding operation.
	 */
	public void storeFailureResult() {
		complete = true;
		success = false;
	}

	/**
	 * Gets the complete flag.
	 * 
	 * @return True if complete.
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * Gets the success flag. This is only valid after complete becomes true.
	 * 
	 * @return True if successful.
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Gets the entities decoded from the blob. This is only valid after
	 * complete becomes true, and if success is true.
	 * 
	 * @return The list of decoded entities.
	 */
	public List<Entity> getEntities() {
		return entities;
	}
}
