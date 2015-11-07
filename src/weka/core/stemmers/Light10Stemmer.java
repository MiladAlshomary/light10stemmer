package weka.core.stemmers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Light10Stemmer implements Stemmer, Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Light10Normalization normalization = new Light10Normalization();
	  
	  public static final char ALEF = '\u0627';
	  public static final char BEH = '\u0628';
	  public static final char TEH_MARBUTA = '\u0629';
	  public static final char TEH = '\u062A';
	  public static final char FEH = '\u0641';
	  public static final char KAF = '\u0643';
	  public static final char LAM = '\u0644';
	  public static final char NOON = '\u0646';
	  public static final char HEH = '\u0647';
	  public static final char WAW = '\u0648';
	  public static final char YEH = '\u064A';
	  
	  public static final char prefixes[][] = {
	      ("" + ALEF + LAM).toCharArray(), 
	      ("" + WAW + ALEF + LAM).toCharArray(), 
	      ("" + BEH + ALEF + LAM).toCharArray(),
	      ("" + KAF + ALEF + LAM).toCharArray(),
	      ("" + FEH + ALEF + LAM).toCharArray(),
	      ("" + LAM + LAM).toCharArray(),
	      ("" + WAW).toCharArray(),
	  };
	  
	  public static final char suffixes[][] = {
	    ("" + HEH + ALEF).toCharArray(), 
	    ("" + ALEF + NOON).toCharArray(), 
	    ("" + ALEF + TEH).toCharArray(), 
	    ("" + WAW + NOON).toCharArray(), 
	    ("" + YEH + NOON).toCharArray(), 
	    ("" + YEH + HEH).toCharArray(),
	    ("" + YEH + TEH_MARBUTA).toCharArray(),
	    ("" + HEH).toCharArray(),
	    ("" + TEH_MARBUTA).toCharArray(),
	    ("" + YEH).toCharArray(),
	};

	public String getRevision() {
		// TODO Auto-generated method stub
		return null;
	}

	public String stem(String token) {
		char[] s = token.toCharArray();
		int length = s.length;

		//normalization step
		length = this.normalization.normalize(s, length);
		length = stemPrefix(s, length);
	    length = stemSuffix(s, length);
	    
	    String stem = new String(s, 0, length);
		return stem;
	}

	public int stemPrefix(char s[], int len) {
	    for (int i = 0; i < prefixes.length; i++) 
	      if (startsWithCheckLength(s, len, prefixes[i]))
	        len = deleteN(s, 0, len, prefixes[i].length);
	    return len;
	}
	
	public int stemSuffix(char s[], int len) {
	    for (int i = 0; i < suffixes.length; i++) 
	      if (endsWithCheckLength(s, len, suffixes[i]))
	        len = deleteN(s, len - suffixes[i].length, len, suffixes[i].length);
	    return len;
	}
	
	private boolean endsWithCheckLength(char s[], int len, char suffix[]) {
	    if (len < suffix.length + 2) { // all suffixes require at least 2 characters after stemming
	      return false;
	    } else {
	      for (int i = 0; i < suffix.length; i++)
	        if (s[len - suffix.length + i] != suffix[i])
	          return false;
	        
	      return true;
	    }
	}
	
	 private boolean startsWithCheckLength(char s[], int len, char prefix[]) {
	    if (prefix.length == 1 && len < 4) { // wa- prefix requires at least 3 characters
	      return false;
	    } else if (len < prefix.length + 2) { // other prefixes require only 2.
	      return false;
	    } else {
	      for (int i = 0; i < prefix.length; i++)
	        if (s[i] != prefix[i])
	          return false;
	        
	      return true;
	    }
	  }
	 
	 
	 public int deleteN(char s[], int pos, int len, int nChars) {
	    // TODO: speed up, this is silly
	    for (int i = 0; i < nChars; i++)
	      len = delete(s, pos, len);
	    return len;
	 }
	 
	 private int delete(char[] s, int pos, int len) {
		  if (pos < len) 
		    System.arraycopy(s, pos + 1, s, pos, len - pos - 1);
		  return len - 1;
	  }
	 
	 
	 public static void main(String[] args) {
		 try {
			 Stemmer stemmer = new Light10Stemmer();
			 System.out.print(stemmer.stem("سياره"));
			 
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
	 }
	
	
}
