/*
 * Created on Jul 18, 2004
 *
 * Copyright (c) 2004, The JUNG Authors
 *
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * https://github.com/tomnelson/jungrapht-visualization/blob/master/LICENSE for a description.
 */
package org.jungrapht.visualization.decorators;

import java.awt.Shape;
import java.util.function.Function;

public interface SettableShapeFunction<T> extends Function<T, Shape> {

  void setSizeFunction(Function<T, Integer> sizeFunction);

  void setAspectRatioFunction(Function<T, Float> aspectRatioFunction);
}
