/*
 * Copyright (c) 2012-2017 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.golo.compiler.testing.support;

public class GoloTestHelperFunctions {

  public static String concatenate(String first, String second) {
    return first + second;
  }

  public static boolean is(Object a, Object b) {
    return a == b;
  }
}
