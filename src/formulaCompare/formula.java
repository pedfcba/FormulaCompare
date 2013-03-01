// $ANTLR 3.4 C:\\6140\\antlrTest\\src\\formula.g 2012-04-17 14:31:01

package formulaCompare;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class formula extends Lexer {
    public static final int EOF=-1;
    public static final int DIGIT=4;
    public static final int DIVID=5;
    public static final int DOWNMARK=6;
    public static final int EQUAL=7;
    public static final int LIMITS=8;
    public static final int MINUS=9;
    public static final int NUMBERS=10;
    public static final int PLUS=11;
    public static final int SIGMA=12;
    public static final int TIMES=13;
    public static final int UPMARK=14;
    public static final int VAR=15;
    public static final int WS=16;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public formula() {} 
    public formula(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public formula(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "C:\\6140\\antlrTest\\src\\formula.g"; }

    // $ANTLR start "VAR"
    public final void mVAR() throws RecognitionException {
        try {
            int _type = VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:9:4: ( 'a' .. 'z' | 'A' .. 'Z' )
            // C:\\6140\\antlrTest\\src\\formula.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VAR"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            int _type = DIGIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:13:6: ( ( '1' .. '9' ) ( '0' .. '9' )* )
            // C:\\6140\\antlrTest\\src\\formula.g:13:8: ( '1' .. '9' ) ( '0' .. '9' )*
            {
            if ( (input.LA(1) >= '1' && input.LA(1) <= '9') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // C:\\6140\\antlrTest\\src\\formula.g:13:19: ( '0' .. '9' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\6140\\antlrTest\\src\\formula.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "NUMBERS"
    public final void mNUMBERS() throws RecognitionException {
        try {
            int _type = NUMBERS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:14:8: ( ( DIGIT | '0' ) ( '.' ( '0' .. '9' )+ )? )
            // C:\\6140\\antlrTest\\src\\formula.g:14:10: ( DIGIT | '0' ) ( '.' ( '0' .. '9' )+ )?
            {
            // C:\\6140\\antlrTest\\src\\formula.g:14:10: ( DIGIT | '0' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0 >= '1' && LA2_0 <= '9')) ) {
                alt2=1;
            }
            else if ( (LA2_0=='0') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // C:\\6140\\antlrTest\\src\\formula.g:14:11: DIGIT
                    {
                    mDIGIT(); 


                    }
                    break;
                case 2 :
                    // C:\\6140\\antlrTest\\src\\formula.g:14:18: '0'
                    {
                    match('0'); 

                    }
                    break;

            }


            // C:\\6140\\antlrTest\\src\\formula.g:14:23: ( '.' ( '0' .. '9' )+ )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\6140\\antlrTest\\src\\formula.g:14:24: '.' ( '0' .. '9' )+
                    {
                    match('.'); 

                    // C:\\6140\\antlrTest\\src\\formula.g:14:28: ( '0' .. '9' )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // C:\\6140\\antlrTest\\src\\formula.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NUMBERS"

    // $ANTLR start "TIMES"
    public final void mTIMES() throws RecognitionException {
        try {
            int _type = TIMES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:17:6: ( '\\\\times' | '*' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\\') ) {
                alt5=1;
            }
            else if ( (LA5_0=='*') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // C:\\6140\\antlrTest\\src\\formula.g:17:8: '\\\\times'
                    {
                    match("\\times"); 



                    }
                    break;
                case 2 :
                    // C:\\6140\\antlrTest\\src\\formula.g:17:20: '*'
                    {
                    match('*'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TIMES"

    // $ANTLR start "DIVID"
    public final void mDIVID() throws RecognitionException {
        try {
            int _type = DIVID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:18:6: ( '\\\\' )
            // C:\\6140\\antlrTest\\src\\formula.g:18:8: '\\\\'
            {
            match('\\'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIVID"

    // $ANTLR start "LIMITS"
    public final void mLIMITS() throws RecognitionException {
        try {
            int _type = LIMITS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:19:7: ( '\\\\limits' )
            // C:\\6140\\antlrTest\\src\\formula.g:19:9: '\\\\limits'
            {
            match("\\limits"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LIMITS"

    // $ANTLR start "SIGMA"
    public final void mSIGMA() throws RecognitionException {
        try {
            int _type = SIGMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:20:6: ( '\\\\sum' )
            // C:\\6140\\antlrTest\\src\\formula.g:20:8: '\\\\sum'
            {
            match("\\sum"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SIGMA"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:21:5: ( '+' )
            // C:\\6140\\antlrTest\\src\\formula.g:21:7: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:22:6: ( '-' )
            // C:\\6140\\antlrTest\\src\\formula.g:22:8: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:23:6: ( '=' )
            // C:\\6140\\antlrTest\\src\\formula.g:23:8: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "UPMARK"
    public final void mUPMARK() throws RecognitionException {
        try {
            int _type = UPMARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:24:7: ( '^' )
            // C:\\6140\\antlrTest\\src\\formula.g:24:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UPMARK"

    // $ANTLR start "DOWNMARK"
    public final void mDOWNMARK() throws RecognitionException {
        try {
            int _type = DOWNMARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:25:9: ( '_' )
            // C:\\6140\\antlrTest\\src\\formula.g:25:11: '_'
            {
            match('_'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOWNMARK"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\6140\\antlrTest\\src\\formula.g:27:3: ( ( ' ' | '\\n' | '\\r' )+ )
            // C:\\6140\\antlrTest\\src\\formula.g:27:5: ( ' ' | '\\n' | '\\r' )+
            {
            // C:\\6140\\antlrTest\\src\\formula.g:27:5: ( ' ' | '\\n' | '\\r' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\n'||LA6_0=='\r'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\6140\\antlrTest\\src\\formula.g:
            	    {
            	    if ( input.LA(1)=='\n'||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // C:\\6140\\antlrTest\\src\\formula.g:1:8: ( VAR | DIGIT | NUMBERS | TIMES | DIVID | LIMITS | SIGMA | PLUS | MINUS | EQUAL | UPMARK | DOWNMARK | WS )
        int alt7=13;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:10: VAR
                {
                mVAR(); 


                }
                break;
            case 2 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:14: DIGIT
                {
                mDIGIT(); 


                }
                break;
            case 3 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:20: NUMBERS
                {
                mNUMBERS(); 


                }
                break;
            case 4 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:28: TIMES
                {
                mTIMES(); 


                }
                break;
            case 5 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:34: DIVID
                {
                mDIVID(); 


                }
                break;
            case 6 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:40: LIMITS
                {
                mLIMITS(); 


                }
                break;
            case 7 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:47: SIGMA
                {
                mSIGMA(); 


                }
                break;
            case 8 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:53: PLUS
                {
                mPLUS(); 


                }
                break;
            case 9 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:58: MINUS
                {
                mMINUS(); 


                }
                break;
            case 10 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:64: EQUAL
                {
                mEQUAL(); 


                }
                break;
            case 11 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:70: UPMARK
                {
                mUPMARK(); 


                }
                break;
            case 12 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:77: DOWNMARK
                {
                mDOWNMARK(); 


                }
                break;
            case 13 :
                // C:\\6140\\antlrTest\\src\\formula.g:1:86: WS
                {
                mWS(); 


                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\2\uffff\1\15\1\uffff\1\20\7\uffff\1\15\4\uffff";
    static final String DFA7_eofS =
        "\21\uffff";
    static final String DFA7_minS =
        "\1\12\1\uffff\1\56\1\uffff\1\154\7\uffff\1\56\4\uffff";
    static final String DFA7_maxS =
        "\1\172\1\uffff\1\71\1\uffff\1\164\7\uffff\1\71\4\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\1\uffff\1\4\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\uffff\1\2\1\6\1\7\1\5";
    static final String DFA7_specialS =
        "\21\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\13\2\uffff\1\13\22\uffff\1\13\11\uffff\1\5\1\6\1\uffff\1"+
            "\7\2\uffff\1\3\11\2\3\uffff\1\10\3\uffff\32\1\1\uffff\1\4\1"+
            "\uffff\1\11\1\12\1\uffff\32\1",
            "",
            "\1\3\1\uffff\12\14",
            "",
            "\1\16\6\uffff\1\17\1\5",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\3\1\uffff\12\14",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( VAR | DIGIT | NUMBERS | TIMES | DIVID | LIMITS | SIGMA | PLUS | MINUS | EQUAL | UPMARK | DOWNMARK | WS );";
        }
    }
 

}