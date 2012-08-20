package gololang.compiler.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ASTCommutativeExpression extends GoloASTNode {

  private final List<String> operators = new LinkedList<>();

  public ASTCommutativeExpression(int id) {
    super(id);
  }

  public ASTCommutativeExpression(GoloParser p, int id) {
    super(p, id);
  }

  public void addOperator(String symbol) {
    operators.add(symbol);
  }

  public List<String> getOperators() {
    return Collections.unmodifiableList(operators);
  }

  @Override
  public String toString() {
    return "ASTCommutativeExpression{" +
        "operators=" + operators +
        '}';
  }

  @Override
  public Object jjtAccept(GoloParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
