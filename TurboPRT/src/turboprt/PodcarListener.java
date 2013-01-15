/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turboprt;

/**
 * This enforces the use of a update method so we can inform them when something changes
 * @author Marcel
 */
public interface PodcarListener {

	/**
	 * This method is called when an attribute of podcar changes.
	 * @param device
	 */
	public void update(Podcar device);
}
