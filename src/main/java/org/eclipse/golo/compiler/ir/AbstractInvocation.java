/*
 * Copyright (c) 2012-2018 Institut National des Sciences Appliquées de Lyon (INSA Lyon) and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.golo.compiler.ir;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractInvocation<T extends AbstractInvocation<T>> extends ExpressionStatement<T> {

  private final String name;
  private final List<ExpressionStatement<?>> arguments = new LinkedList<>();
  protected boolean usesNamedArguments = false;

  AbstractInvocation(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private void addArgument(ExpressionStatement<?> argument) {
    arguments.add(argument);
    makeParentOf(argument);
  }

  public T withArgs(Object... arguments) {
    for (Object argument : arguments) {
      addArgument(ExpressionStatement.of(argument));
    }
    return self();
  }

  public List<ExpressionStatement<?>> getArguments() {
    return Collections.unmodifiableList(arguments);
  }

  public int getArity() {
    return arguments.size();
  }

  public boolean usesNamedArguments() {
    return usesNamedArguments;
  }

  public boolean namedArgumentsComplete() {
    return this.arguments.isEmpty() || this.usesNamedArguments;
  }

  public T withNamedArguments() {
    setUsesNamedArguments(true);
    return self();
  }

  private void setUsesNamedArguments(boolean usesNamedArguments) {
    this.usesNamedArguments = usesNamedArguments;
  }

  @Override
  public void walk(GoloIrVisitor visitor) {
    for (ExpressionStatement<?> arg : arguments) {
      arg.accept(visitor);
    }
  }

  @Override
  protected void replaceElement(GoloElement<?> original, GoloElement<?> newElement) {
    if (newElement instanceof ExpressionStatement && arguments.contains(original)) {
      this.arguments.set(arguments.indexOf(ExpressionStatement.of(original)),
          ExpressionStatement.of(newElement));
    } else {
      throw cantReplace(original, newElement);
    }
  }
}
