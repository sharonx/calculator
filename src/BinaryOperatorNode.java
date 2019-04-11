public class BinaryOperatorNode extends Node {
  private Node left;
  private Node right;
  private Operator op;

  public BinaryOperatorNode(Node left, Node right, Operator op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  public long getValue() throws Exception {
    switch (op) {
    case PLUS:
      return left.getValue() + right.getValue();
    case MULTIPLY:
      return left.getValue() * right.getValue();
    case DIVIDE:
      if (right.getValue() == 0) {
        throw new Exception("Dividend cannot be 0");
      }
      return left.getValue() / right.getValue();
    case MINUS:
      return left.getValue() - right.getValue();
    default:
      throw new Exception("Operator can't be recognized");
    }
  }
}
