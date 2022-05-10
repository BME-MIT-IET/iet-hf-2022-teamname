package InputTranslator;

import java.text.MessageFormat;

public class Token {
    private TokenTypes type;
    private String value;

    public Token(TokenTypes type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return MessageFormat.format("({0} {1})", type, value);
    }

    public TokenTypes getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
    	Token t=(Token)o;
    	return type==t.getType()&& value.equals(t.getValue());
    }
}
