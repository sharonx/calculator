public class Token {
  private Operator type;
  private String text;
  private int lineNumber;
  private int charPosition;

  public Token(Operator type, String text, int lineNumber, int charPosition) {
    this.type = type;
    this.text = text;
    this.lineNumber = lineNumber;
    this.charPosition = charPosition;
  }

  public Operator getType() {
    return type;
  }

  public void setType(Operator type) {
    this.type = type;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public int getCharPosition() {
    return charPosition;
  }

  public void setCharPosition(int charPosition) {
    this.charPosition = charPosition;
  }

  @Override
  public String toString() {
    return "Token{" +
      "type='" + type + '\'' +
      ", text='" + text + '\'' +
      ", lineNumber=" + lineNumber +
      ", charPosition=" + charPosition +
      '}';
  }
}
