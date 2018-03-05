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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Collections;

public class ClosureReference extends ExpressionStatement {

  private GoloFunction target;
  private final Set<String> capturedReferenceNames = new LinkedHashSet<>();

  ClosureReference(GoloFunction target) {
    super();
    setTarget(target);
  }

  public GoloFunction getTarget() {
    return target;
  }

  private void setTarget(GoloFunction target) {
    this.target = target;
    makeParentOf(target);
    this.positionInSourceCode(target.positionInSourceCode());
    this.documentation(target.documentation());
    updateCapturedReferenceNames();
  }

  public Set<String> getCapturedReferenceNames() {
    return Collections.unmodifiableSet(capturedReferenceNames);
  }

  public void updateCapturedReferenceNames() {
    for (String name : target.getSyntheticParameterNames()) {
      capturedReferenceNames.add(name);
    }
  }

  public ClosureReference block(Object... statements) {
    this.target.block(statements);
    return this;
  }

  public ClosureReference returns(Object expression) {
    this.target.returns(expression);
    return this;
  }

  @Override
  public void accept(GoloIrVisitor visitor) {
    visitor.visitClosureReference(this);
  }

  @Override
  public void walk(GoloIrVisitor visitor) {
    target.accept(visitor);
  }

  @Override
  protected void replaceElement(GoloElement original, GoloElement newElement) {
    if (newElement instanceof GoloFunction && target.equals(original)) {
      setTarget((GoloFunction) newElement);
    } else {
      throw cantReplace(original, newElement);
    }
  }

  @Override
  public String toString() {
    return String.format("ClosureReference{target=%s, captured=%s}", target, capturedReferenceNames);
  }
}
