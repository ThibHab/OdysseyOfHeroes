/* Generated By:JavaCC: Do not edit this line. AutomataParserTokenManager.java */
package info3.game.automata.parser;

/** Token Manager. */
public class AutomataParserTokenManager implements AutomataParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 18;
            return 65;
         }
         return -1;
      case 1:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 18;
            jjmatchedPos = 1;
            return 64;
         }
         return -1;
      case 2:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 5;
            jjmatchedPos = 2;
            return 12;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjStopAtPos(0, 16);
      case 37:
         return jjStopAtPos(0, 30);
      case 40:
         return jjStopAtPos(0, 21);
      case 41:
         return jjStopAtPos(0, 22);
      case 42:
         return jjStopAtPos(0, 25);
      case 44:
         return jjStopAtPos(0, 31);
      case 58:
         return jjStopAtPos(0, 26);
      case 63:
         return jjStopAtPos(0, 27);
      case 75:
         return jjMoveStringLiteralDfa1_0(0x20000000L);
      case 95:
         return jjStopAtPos(0, 20);
      case 100:
         return jjStopAtPos(0, 8);
      case 101:
         return jjStopAtPos(0, 9);
      case 123:
         return jjStopAtPos(0, 23);
      case 124:
         return jjStopAtPos(0, 28);
      case 125:
         return jjStopAtPos(0, 24);
      default :
         return jjMoveNfa_0(2, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x20000000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 121:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 40:
         if ((active0 & 0x20000000L) != 0L)
            return jjStopAtPos(3, 29);
         break;
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 114;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 19)
                        kind = 19;
                     jjCheckNAdd(13);
                  }
                  else if ((0x804000000000L & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                  }
                  break;
               case 65:
               case 12:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(12);
                  break;
               case 64:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(12);
                  break;
               case 10:
                  if ((0x804000000000L & l) != 0L)
                     kind = 17;
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 19)
                     kind = 19;
                  jjCheckNAdd(13);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((0x7fffffeL & l) != 0L)
                  {
                     if (kind > 18)
                        kind = 18;
                     jjCheckNAdd(12);
                  }
                  else if ((0x7fffffe00000000L & l) != 0L)
                  {
                     if (kind > 13)
                        kind = 13;
                  }
                  if ((0x51a49bL & l) != 0L)
                  {
                     if (kind > 11)
                        kind = 11;
                  }
                  else if ((0x8c5164L & l) != 0L)
                  {
                     if (kind > 10)
                        kind = 10;
                  }
                  else if (curChar == 75)
                     jjAddStates(0, 1);
                  if (curChar == 69)
                     jjAddStates(2, 4);
                  else if (curChar == 83)
                     jjCheckNAddStates(5, 8);
                  else if (curChar == 71)
                     jjAddStates(9, 11);
                  else if (curChar == 77)
                     jjAddStates(12, 13);
                  else if (curChar == 84)
                     jjAddStates(14, 16);
                  else if (curChar == 67)
                     jjAddStates(17, 18);
                  else if (curChar == 87)
                     jjAddStates(19, 20);
                  else if (curChar == 80)
                     jjAddStates(21, 24);
                  else if (curChar == 70)
                     jjCheckNAddStates(25, 28);
                  else if (curChar == 78)
                     jjCheckNAddTwoStates(15, 16);
                  else if (curChar == 74)
                     jjstateSet[jjnewStateCnt++] = 5;
                  else if (curChar == 72)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 65:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 18)
                        kind = 18;
                     jjCheckNAdd(12);
                  }
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 71;
                  else if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 64;
                  break;
               case 64:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 18)
                        kind = 18;
                     jjCheckNAdd(12);
                  }
                  if (curChar == 121)
                  {
                     if (kind > 5)
                        kind = 5;
                  }
                  break;
               case 0:
                  if (curChar == 116 && kind > 6)
                     kind = 6;
                  break;
               case 1:
               case 40:
                  if (curChar == 105)
                     jjCheckNAdd(0);
                  break;
               case 3:
                  if (curChar == 112 && kind > 6)
                     kind = 6;
                  break;
               case 4:
                  if (curChar == 109)
                     jjCheckNAdd(3);
                  break;
               case 5:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if (curChar == 74)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
                  if ((0x8c5164L & l) != 0L && kind > 10)
                     kind = 10;
                  break;
               case 8:
                  if ((0x51a49bL & l) != 0L && kind > 11)
                     kind = 11;
                  break;
               case 9:
                  if ((0x7fffffe00000000L & l) != 0L && kind > 13)
                     kind = 13;
                  break;
               case 11:
                  if ((0x7fffffeL & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(12);
                  break;
               case 12:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(12);
                  break;
               case 14:
                  if (curChar == 78)
                     jjCheckNAddTwoStates(15, 16);
                  break;
               case 15:
                  if (curChar == 69 && kind > 10)
                     kind = 10;
                  break;
               case 16:
                  if (curChar == 87 && kind > 10)
                     kind = 10;
                  break;
               case 17:
                  if (curChar == 70)
                     jjCheckNAddStates(25, 28);
                  break;
               case 18:
                  if (curChar == 85 && kind > 7)
                     kind = 7;
                  break;
               case 19:
                  if (curChar == 68 && kind > 7)
                     kind = 7;
                  break;
               case 20:
                  if (curChar == 76 && kind > 7)
                     kind = 7;
                  break;
               case 21:
                  if (curChar == 82 && kind > 7)
                     kind = 7;
                  break;
               case 22:
                  if (curChar == 80)
                     jjAddStates(21, 24);
                  break;
               case 23:
                  if (curChar == 107 && kind > 6)
                     kind = 6;
                  break;
               case 24:
                  if (curChar == 99)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 25:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 26:
                  if (curChar == 111)
                     jjCheckNAdd(3);
                  break;
               case 27:
                  if (curChar == 114 && kind > 6)
                     kind = 6;
                  break;
               case 28:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 29:
                  if (curChar == 119)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 30:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 31:
                  if (curChar == 99)
                     jjCheckNAdd(0);
                  break;
               case 32:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 33:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 34:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 35:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 36:
                  if (curChar == 87)
                     jjAddStates(19, 20);
                  break;
               case 37:
                  if (curChar == 122 && kind > 6)
                     kind = 6;
                  break;
               case 38:
                  if (curChar == 122)
                     jjstateSet[jjnewStateCnt++] = 37;
                  break;
               case 39:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 38;
                  break;
               case 41:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 40;
                  break;
               case 42:
                  if (curChar == 67)
                     jjAddStates(17, 18);
                  break;
               case 43:
                  if (curChar == 108 && kind > 5)
                     kind = 5;
                  break;
               case 44:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 43;
                  break;
               case 45:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 44;
                  break;
               case 46:
                  if (curChar == 116 && kind > 5)
                     kind = 5;
                  break;
               case 47:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 48:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 49:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 48;
                  break;
               case 50:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 49;
                  break;
               case 51:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 50;
                  break;
               case 52:
                  if (curChar == 84)
                     jjAddStates(14, 16);
                  break;
               case 53:
                  if (curChar == 101 && kind > 5)
                     kind = 5;
                  break;
               case 54:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 53;
                  break;
               case 55:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 54;
                  break;
               case 56:
                  if (curChar == 119 && kind > 6)
                     kind = 6;
                  break;
               case 57:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 56;
                  break;
               case 58:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 57;
                  break;
               case 59:
                  if (curChar == 104)
                     jjstateSet[jjnewStateCnt++] = 58;
                  break;
               case 60:
                  if (curChar == 110 && kind > 6)
                     kind = 6;
                  break;
               case 61:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 60;
                  break;
               case 62:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 61;
                  break;
               case 63:
                  if (curChar == 75)
                     jjAddStates(0, 1);
                  break;
               case 66:
                  if (curChar == 101 && kind > 6)
                     kind = 6;
                  break;
               case 67:
                  if (curChar == 122)
                     jjCheckNAdd(66);
                  break;
               case 68:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 67;
                  break;
               case 69:
                  if (curChar == 107)
                     jjstateSet[jjnewStateCnt++] = 68;
                  break;
               case 70:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 69;
                  break;
               case 71:
                  if (curChar == 109)
                     jjstateSet[jjnewStateCnt++] = 70;
                  break;
               case 72:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 71;
                  break;
               case 73:
                  if (curChar == 77)
                     jjAddStates(12, 13);
                  break;
               case 74:
                  if (curChar == 114 && kind > 5)
                     kind = 5;
                  break;
               case 75:
                  if (curChar == 105)
                     jjCheckNAdd(74);
                  break;
               case 76:
                  if (curChar == 68)
                     jjstateSet[jjnewStateCnt++] = 75;
                  break;
               case 77:
                  if (curChar == 121)
                     jjstateSet[jjnewStateCnt++] = 76;
                  break;
               case 78:
                  if (curChar == 118)
                     jjCheckNAdd(66);
                  break;
               case 79:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 78;
                  break;
               case 80:
                  if (curChar == 71)
                     jjAddStates(9, 11);
                  break;
               case 81:
                  if (curChar == 101)
                     jjCheckNAdd(74);
                  break;
               case 82:
                  if (curChar == 119)
                     jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 83:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 82;
                  break;
               case 84:
                  if (curChar == 80)
                     jjstateSet[jjnewStateCnt++] = 83;
                  break;
               case 85:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 84;
                  break;
               case 86:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 85;
                  break;
               case 87:
                  if (curChar == 102 && kind > 5)
                     kind = 5;
                  break;
               case 88:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 87;
                  break;
               case 89:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 88;
                  break;
               case 90:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 89;
                  break;
               case 91:
                  if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 90;
                  break;
               case 92:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 91;
                  break;
               case 93:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 92;
                  break;
               case 94:
                  if (curChar == 101)
                     jjCheckNAdd(0);
                  break;
               case 95:
                  if (curChar == 83)
                     jjCheckNAddStates(5, 8);
                  break;
               case 96:
                  if (curChar == 114)
                     jjCheckNAdd(66);
                  break;
               case 97:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 96;
                  break;
               case 98:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 97;
                  break;
               case 99:
                  if (curChar == 69 && kind > 7)
                     kind = 7;
                  break;
               case 100:
                  if (curChar == 67)
                     jjstateSet[jjnewStateCnt++] = 99;
                  break;
               case 101:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 100;
                  break;
               case 102:
                  if (curChar == 80)
                     jjstateSet[jjnewStateCnt++] = 101;
                  break;
               case 103:
                  if (curChar == 69)
                     jjAddStates(2, 4);
                  break;
               case 104:
                  if (curChar == 103 && kind > 6)
                     kind = 6;
                  break;
               case 105:
                  if (curChar == 103)
                     jjstateSet[jjnewStateCnt++] = 104;
                  break;
               case 106:
                  if (curChar == 100)
                     jjCheckNAdd(66);
                  break;
               case 107:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 106;
                  break;
               case 108:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 107;
                  break;
               case 109:
                  if (curChar == 112)
                     jjstateSet[jjnewStateCnt++] = 108;
                  break;
               case 110:
                  if (curChar == 120)
                     jjstateSet[jjnewStateCnt++] = 109;
                  break;
               case 111:
                  if (curChar == 69)
                     jjCheckNAdd(21);
                  break;
               case 112:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 111;
                  break;
               case 113:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 112;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 114 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   65, 72, 105, 110, 113, 98, 102, 15, 16, 86, 93, 94, 77, 79, 55, 59, 
   62, 45, 51, 39, 41, 25, 26, 30, 35, 18, 19, 20, 21, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, "\144", "\145", null, null, null, 
null, null, null, "\41", null, null, null, "\137", "\50", "\51", "\173", "\175", 
"\52", "\72", "\77", "\174", "\113\145\171\50", "\45", "\54", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0xffff2fe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[114];
private final int[] jjstateSet = new int[228];
protected char curChar;
/** Constructor. */
public AutomataParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public AutomataParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 114; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
