public class NumberNode extends Node {
  private long value;

  public NumberNode(long value) {
    this.value = value;
  }

  @Override
  public long getValue() {
    return this.value;
  }
}
