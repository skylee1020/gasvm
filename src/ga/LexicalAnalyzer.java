package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:16:05
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.LexicalAnalyzer.java


public class LexicalAnalyzer
{

    public LexicalAnalyzer(String s)
        throws ParseError
    {
        string = s;
        length = s.length();
        read = 0;
        symbolRead();
    }

    public void next()
        throws ParseError
    {
        if(type != 0)
            symbolRead();
    }

    public void throwError()
        throws ParseError
    {
        throw new ParseError(string, read);
    }

    private int charLook()
    {
        if(read == length)
            return -1;
        char c = string.charAt(read);
        if(c >= 'A' && c <= 'Z')
            return (c + 97) - 65;
        else
            return c;
    }

    private void charNext()
    {
        if(read < length)
            read++;
    }

    private boolean charIsNumber()
    {
        int i = charLook();
        return i >= 48 && i <= 57;
    }

    private void symbolRead()
        throws ParseError
    {
        do
            switch(charLook())
            {
            case -1: 
                type = 0;
                return;

            case 9: // '\t'
            case 32: // ' '
                if(read < length)
                    read++;
                break;

            case 40: // '('
                if(read < length)
                    read++;
                type = 5;
                return;

            case 41: // ')'
                if(read < length)
                    read++;
                type = 6;
                return;

            case 94: // '^'
                if(read < length)
                    read++;
                type = 7;
                return;

            case 43: // '+'
                if(read < length)
                    read++;
                type = 8;
                return;

            case 45: // '-'
                if(read < length)
                    read++;
                type = 9;
                return;

            case 42: // '*'
                if(read < length)
                    read++;
                type = 10;
                return;

            case 47: // '/'
                if(read < length)
                    read++;
                type = 11;
                return;

            case 33: // '!'
                if(read < length)
                    read++;
                type = 12;
                return;

            case 97: // 'a'
                if(read < length)
                    read++;
                switch(charLook())
                {
                case 115: // 's'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 105: // 'i'
                        if(read < length)
                            read++;
                        switch(charLook())
                        {
                        case 110: // 'n'
                            if(read < length)
                                read++;
                            switch(charLook())
                            {
                            case 104: // 'h'
                                if(read < length)
                                    read++;
                                type = 20;
                                return;
                            }
                            type = 14;
                            return;
                        }
                        throw new ParseError(string, read);
                    }
                    throw new ParseError(string, read);

                case 98: // 'b'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 115: // 's'
                        if(read < length)
                            read++;
                        type = 29;
                        return;
                    }
                    throw new ParseError(string, read);

                case 99: // 'c'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 111: // 'o'
                        if(read < length)
                            read++;
                        switch(charLook())
                        {
                        case 115: // 's'
                            if(read < length)
                                read++;
                            switch(charLook())
                            {
                            case 104: // 'h'
                                if(read < length)
                                    read++;
                                type = 22;
                                return;
                            }
                            type = 16;
                            return;
                        }
                        throw new ParseError(string, read);
                    }
                    throw new ParseError(string, read);

                case 116: // 't'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 97: // 'a'
                        if(read < length)
                            read++;
                        switch(charLook())
                        {
                        case 110: // 'n'
                            if(read < length)
                                read++;
                            switch(charLook())
                            {
                            case 104: // 'h'
                                if(read < length)
                                    read++;
                                type = 24;
                                return;
                            }
                            type = 18;
                            return;
                        }
                        throw new ParseError(string, read);
                    }
                    throw new ParseError(string, read);
                }
                throw new ParseError(string, read);

            case 99: // 'c'
                if(read < length)
                    read++;
                switch(charLook())
                {
                case 111: // 'o'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 115: // 's'
                        if(read < length)
                            read++;
                        switch(charLook())
                        {
                        case 104: // 'h'
                            if(read < length)
                                read++;
                            type = 21;
                            return;
                        }
                        type = 15;
                        return;
                    }
                    throw new ParseError(string, read);
                }
                throw new ParseError(string, read);

            case 105: // 'i'
                if(read < length)
                    read++;
                switch(charLook())
                {
                case 110: // 'n'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 116: // 't'
                        if(read < length)
                            read++;
                        type = 30;
                        return;
                    }
                    throw new ParseError(string, read);
                }
                throw new ParseError(string, read);

            case 108: // 'l'
                if(read < length)
                    read++;
                switch(charLook())
                {
                case 110: // 'n'
                    if(read < length)
                        read++;
                    type = 26;
                    return;

                case 111: // 'o'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 103: // 'g'
                        if(read < length)
                            read++;
                        type = 25;
                        return;
                    }
                    throw new ParseError(string, read);
                }
                throw new ParseError(string, read);

            case 101: // 'e'
                if(read < length)
                    read++;
                type = 3;
                return;

            case 112: // 'p'
                if(read < length)
                    read++;
                switch(charLook())
                {
                case 105: // 'i'
                    if(read < length)
                        read++;
                    type = 4;
                    return;
                }
                throw new ParseError(string, read);

            case 115: // 's'
                if(read < length)
                    read++;
                switch(charLook())
                {
                case 105: // 'i'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 110: // 'n'
                        if(read < length)
                            read++;
                        switch(charLook())
                        {
                        case 104: // 'h'
                            if(read < length)
                                read++;
                            type = 19;
                            return;
                        }
                        type = 13;
                        return;
                    }
                    throw new ParseError(string, read);

                case 113: // 'q'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 114: // 'r'
                        if(read < length)
                            read++;
                        switch(charLook())
                        {
                        case 116: // 't'
                            if(read < length)
                                read++;
                            type = 28;
                            return;
                        }
                        type = 27;
                        return;
                    }
                    throw new ParseError(string, read);
                }
                throw new ParseError(string, read);

            case 116: // 't'
                if(read < length)
                    read++;
                switch(charLook())
                {
                case 97: // 'a'
                    if(read < length)
                        read++;
                    switch(charLook())
                    {
                    case 110: // 'n'
                        if(read < length)
                            read++;
                        switch(charLook())
                        {
                        case 104: // 'h'
                            if(read < length)
                                read++;
                            type = 23;
                            return;
                        }
                        type = 17;
                        return;
                    }
                    throw new ParseError(string, read);
                }
                throw new ParseError(string, read);

            case 120: // 'x'
                if(read < length)
                    read++;
                type = 2;
                return;

            case 121: // 'y'
                if(read < length)
                    read++;
                type = 31;
                return;

            case 46: // '.'
                type = 1;
                value = 0.0D;
                if(read < length)
                    read++;
                if(!charIsNumber())
                    throw new ParseError(string, read);
                double d = 1.0D;
                while(charIsNumber()) 
                {
                    value += (d /= 10D) * (double)(charLook() - 48);
                    if(read < length)
                        read++;
                }
                return;

            default:
                if(!charIsNumber())
                    throw new ParseError(string, read);
                type = 1;
                value = 0.0D;
                while(charIsNumber()) 
                {
                    value *= 10D;
                    value += charLook() - 48;
                    if(read < length)
                        read++;
                }
                if(charLook() != 46)
                    return;
                if(read < length)
                    read++;
                if(!charIsNumber())
                    throw new ParseError(string, read);
                double d1 = 1.0D;
                while(charIsNumber()) 
                {
                    value += (d1 /= 10D) * (double)(charLook() - 48);
                    if(read < length)
                        read++;
                }
                return;
            }
        while(true);
    }

    private String string;
    private int length;
    private int read;
    public int type;
    public static final int EOIType = 0;
    public static final int numberType = 1;
    public static final int XType = 2;
    public static final int EType = 3;
    public static final int PIType = 4;
    public static final int leftType = 5;
    public static final int rightType = 6;
    public static final int powerType = 7;
    public static final int plusType = 8;
    public static final int minusType = 9;
    public static final int multiplyType = 10;
    public static final int divideType = 11;
    public static final int factorialType = 12;
    public static final int sinType = 13;
    public static final int asinType = 14;
    public static final int cosType = 15;
    public static final int acosType = 16;
    public static final int tanType = 17;
    public static final int atanType = 18;
    public static final int sinhType = 19;
    public static final int asinhType = 20;
    public static final int coshType = 21;
    public static final int acoshType = 22;
    public static final int tanhType = 23;
    public static final int atanhType = 24;
    public static final int logType = 25;
    public static final int lnType = 26;
    public static final int sqrType = 27;
    public static final int sqrtType = 28;
    public static final int absType = 29;
    public static final int intType = 30;
    public static final int YType = 31;
    public double value;
}
