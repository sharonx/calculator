import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        while(true) {
            Scanner scanner = new Scanner(System.in);

            String input = scanner.nextLine();
            LinkedList<Token> tokens = new LinkedList<>();

            String digits = "";

            for (int i = 0; i < input.length(); i++) {
                char cur = input.charAt(i);
                if (Character.isSpaceChar(cur)) {
                    if (!digits.equals("")) {
                        tokens.add(new Token(Operator.NUMBER, digits, 0, i - digits.length()));
                    }
                    digits = "";
                } else if (Character.isDigit(cur)) {
                    digits += cur;
                } else if (cur == '+') {
                    if (!digits.equals("")) {
                        tokens.add(new Token(Operator.NUMBER, digits, 0, i - digits.length()));
                    }
                    digits = "";
                    tokens.add(new Token(Operator.PLUS, Character.toString(cur), 0, i - digits.length()));
                }  else if (cur == '-') {
                    if (!digits.equals("")) {
                        tokens.add(new Token(Operator.NUMBER, digits, 0, i - digits.length()));
                    }
                    digits = "";
                    tokens.add(new Token(Operator.MINUS, Character.toString(cur), 0, i - digits.length()));
                } else if (cur == '*') {
                    if (!digits.equals("")) {
                        tokens.add(new Token(Operator.NUMBER, digits, 0, i - digits.length()));
                    }
                    digits = "";
                    tokens.add(new Token(Operator.MULTIPLY, Character.toString(cur), 0, i - digits.length()));
                } else if (cur == '/') {
                    if (!digits.equals("")) {
                        tokens.add(new Token(Operator.NUMBER, digits, 0, i - digits.length()));
                    }
                    digits = "";
                    tokens.add(new Token(Operator.DIVIDE, Character.toString(cur), 0, i - digits.length()));
                } else if (cur == '(') {
                    if (!digits.equals("")) {
                        tokens.add(new Token(Operator.NUMBER, digits, 0, i - digits.length()));
                    }
                    digits = "";
                    tokens.add(new Token(Operator.LEFT, Character.toString(cur), 0, i - digits.length()));
                } else if (cur == ')') {
                    if (!digits.equals("")) {
                        tokens.add(new Token(Operator.NUMBER, digits, 0, i - digits.length()));
                    }
                    digits = "";
                    tokens.add(new Token(Operator.RIGHT, Character.toString(cur), 0, i - digits.length()));
                } else {
                    System.out.println("Error: cannot parse");
                    break;
                }
            }

            if (!digits.equals("")) {
                tokens.add(new Token(Operator.NUMBER, digits, 0, input.length() - digits.length()));
            }

            try {
                long result =  parse2(tokens).getValue();
                System.out.println("result is: " + result);
            } catch (Exception e) {
                System.out.println("Caught Exception: " + e.getMessage());
            }

        }
    }

    private static Node parse(List<Token> tokens, int pos) {
        if (pos > tokens.size() - 1) {
            throw new IllegalArgumentException("Error: can't parse");
        }

        if (tokens.get(pos).getType() == Operator.NUMBER) {
            Node left = new NumberNode(Long.parseLong(tokens.get(pos).getText()));
            if (pos == tokens.size() -1) {
                return left;
            }

            if (tokens.get(pos + 1).getType() != Operator.PLUS) {
                throw new IllegalArgumentException("Error: can't parse");
            }

            return new BinaryOperatorNode(left, parse(tokens, pos+2), Operator.PLUS);
        }

        throw new IllegalArgumentException("Error: can't parse");
    }

    private static Node parse2(LinkedList<Token> tokens) {
        if (tokens.size() == 0) {
            throw new IllegalArgumentException("Error: can't parse");
        }

        Node n = parseSum(tokens);

        if (tokens.size() > 0) {
            throw new IllegalArgumentException("Unable to parse string in the end");
        }

        return n;
    }

    private static Node parsePrimary(LinkedList<Token> tokens) {
        Token t = tokens.pollFirst();

        if (t != null && t.getType() == Operator.NUMBER) {
            return new NumberNode(Long.parseLong(t.getText()));
        }

        if (t != null && t.getType() == Operator.LEFT) {
            Node n = parseSum(tokens);
            if (tokens.peekFirst() == null || tokens.pollFirst().getType() != Operator.RIGHT) {
                throw new IllegalArgumentException("Error: parenthesis mismatch");
            }
            return n;
        }

        return null;
    }


    private static Node parseProduct(LinkedList<Token> tokens) {
        Node left = parsePrimary(tokens);

        if (left == null) {
            throw new IllegalArgumentException("Error: can't parse");
        }

        Token t = tokens.peekFirst();
        while (t != null && (t.getType() == Operator.MULTIPLY || t.getType() == Operator.DIVIDE)) {
            tokens.pollFirst();
            Node right = parsePrimary(tokens);
            left = new BinaryOperatorNode(left, right,t.getType());
            t = tokens.peekFirst();
        }

        return left;
    }

    private static Node parseSum(LinkedList<Token> tokens) {
        Node left = parseProduct(tokens);

        if (left == null) {
            throw new IllegalArgumentException("Error: can't parse");
        }

        Token t = tokens.peekFirst();
        while (t != null && (t.getType() == Operator.PLUS || t.getType() == Operator.MINUS)) {
            t = tokens.pollFirst();
            Node right = parseProduct(tokens);
            left = new BinaryOperatorNode(left, right, t.getType());

            t = tokens.peekFirst();
        }

        return left;
    }

}
