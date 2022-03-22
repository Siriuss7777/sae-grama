package org.jungrapht.visualization.spatial.rtree;

import java.awt.geom.Rectangle2D;

/**
 * Interface for items that present a bounding box rectangle
 *
 * @author Tom Nelson
 */
public interface Bounded {

  /**
   * return the Rectangle of the bounding box
   *
   * @return the rectangular bounds of the implementation
   */
  Rectangle2D getBounds();
}
