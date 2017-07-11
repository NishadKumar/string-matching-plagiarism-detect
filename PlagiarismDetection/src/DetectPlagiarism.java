
//import static java.awt.Color.RED;
import java.awt.Component;
import static java.awt.SystemColor.menu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import static jdk.nashorn.internal.objects.NativeMath.max;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aarohi, nishad
 */
public class DetectPlagiarism extends javax.swing.JFrame {
static String[] plagiarizedSentences;
    /**
     * Creates new form UI
     */
    public static String read(String fileName)
    {
        // line by line 
        String line = null;
        String text = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                
                text=text+line;
                
                
            } //  display.appendln(text); 
            
            // Always close files.
            bufferedReader.close(); 
            
        }
        catch(FileNotFoundException ex) {
                           
        }
        catch(IOException ex) {
            
        }   
     return text; 
    }
    public static File[] extract(String path, String file)
    { //display.appendln("name"+file);
    File folder = new File(path);
            //new File("/Users/aarohi/Desktop/docs");
File[] Files1 = folder.listFiles();
int a=0;
for(int i =0; i<Files1.length;i++)
{//display.appendln("in for");
//display.appendln(Files1[i]);
if(Files1[i].getName().equals(file))
{
Files1[i]=null;
a=i;
}
//display.appendln(a);
}
int nSize = Files1.length - 1;
    File[] newFiles = new File[nSize];
    if (nSize > 0) {
       // display.append("in for"); 
        if(a>0){
           //display.appendln("in if"+a); 
        System.arraycopy(Files1, 0, newFiles, 0, a);
        System.arraycopy(Files1, a+1, newFiles, a,Files1.length-a-1);
        }
        else if(a==0)
        {
            //display.appendln("in else"); 
        System.arraycopy(Files1, 1, newFiles, 0,Files1.length - 1);
        }
    }
    //display.appendln(Arrays.toString(newFiles));
    return newFiles;
  
    } 
    public static String[] split(String s2){ 
String[] Sentence = s2.split("(?<=[a-z])\\.\\s+");

return Sentence;
    }
    public void print(String[] s3){
int length = s3.length;
//display.appendln("length:"+length);
for(int i=0;i<length;i++)
{
    String sentence1 = s3[i];
    display.append("\nSentence:"+i+":"+ sentence1);
}
    }
   public void listFilesForFolder(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            display.append("\n"+fileEntry.getName());
        }
    }
}  
   public static boolean KMP(String pat1, String txt1)
    {
        boolean a = false;
          String string = txt1;
           String string1 = pat1;
String[] txt = string.replace(".", "").replace(",", "").replace("?", "").replace("!","").split(" ");
String[] pat = string1.replace(".", "").replace(",", "").replace("?", "").replace("!","").split(" ");
        int M = pat.length;
        int N = txt.length;
 
       
        int pref[] = new int[M];
        int j = 0;  // index for pat[]
 
        // Preprocess the pattern 
        LPSArray(pat,M,pref);
 
        int i = 0;  // index for txt[]
        while (i < N)
        {//display.appendln(pat[j]+" "+txt[i]);
            if (pat[j].equals(txt[i]))
            {
                //display.appendln("OK");
                j++;
                i++;
            }
            if (j == M)
            {
                a=true;
                //display.appendln("Found pattern "+
                              //"at index " + (i-j));
                j = pref[j-1];
            }
 
            // mismatch after j matches
            else if (i < N && pat[j].equals(txt[i])==false)
            {
                // Do not match pref[0..pref[j-1]] characters,
                // they will match anyway
                if (j != 0){
                    j = pref[j-1];
               
                }
                else{
                    i = i+1;
                     
                }
            }
        }
        return a;
    }
 
    public static void LPSArray(String[] pat, int M, int pref[])
    {
        // length of the previous longest prefix suffix
        int l = 0;
        int i = 1;
        pref[0] = 0;  // pref[0] is always 0
 
        // the loop calculates pref[i] for i = 1 to M-1
        while (i < M)
        {
            if (pat[i].equals(pat[l]))
            {
                
                l++;
                pref[i] = l;
                i++;
            }
            else  // (pat[i] != pat[l])
            {
                
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar 
                // to search step.
                if (l != 0)
                {
                    l = pref[l-1];
 
                    // Also, note that we do not increment
                    // i here
                }
                else  // if (l == 0)
                {
                    pref[i] = l;
                    
                    i++;
                }
            }
        }
    }
    public static boolean NaiveSearch(String pat, String txt){
        boolean a = false;
           String string = txt;
           String string1 = pat;
String[] parts = string.replace(".", "").replace(",", "").replace("?", "").replace("!","").split(" ");
String[] parts1 = string1.replace(".", "").replace(",", "").replace("?", "").replace("!","").split(" ");
 // 004-
int m;
        m = parts1.length;
    int n = parts.length;
    for(int i = 0;i<=n-m;i++)
    {
        int j;
        for(j=0;j<m;j++)
        {
            String partsa = parts[i+j];
            String partsb = parts1[j];
            if(partsa.equals(partsb)==false) 
                break;
        }
        if(j==m)
        {  
        a= true;      
        }
    }
    return a;
    }
     public boolean boyer_moore(String t, String p)
    {
        //display.appendln("t:"+t);
       //display.appendln("p:"+p);
        boolean a = false;
        char[] text = t.toCharArray();
        char[] pattern = p.toCharArray();
        int position = index(text, pattern);
        if (position != -1){
            display.append("Pattern found at position : "+ position);
        a=true;
        }
        
        return a;
    }
     public static int[] offsetTable(char[] pattern) 
      {
        int[] table = new int[pattern.length];
        int lastPrefixPosition = pattern.length;
        for (int i = pattern.length - 1; i >= 0; --i) 
        {
            if (pref(pattern, i + 1)) 
                   lastPrefixPosition = i + 1;
              table[pattern.length - 1 - i] = lastPrefixPosition - i + pattern.length - 1;
        }
        for (int i = 0; i < pattern.length - 1; ++i) 
        {
              int sl = suff_l(pattern, i);
              table[sl] = pattern.length - 1 - i + sl;
        }
        return table;
    }
     public static boolean pref(char[] pattern, int p) 
    {
        for (int i = p, j = 0; i < pattern.length; ++i, ++j) 
            if (pattern[i] != pattern[j]) 
                  return false;
        return true;
    }
    /** function to returns the maximum length of the substring ends at p and is a suffix **/
    private static int suff_l(char[] pattern, int p) 
    {
        int l = 0;
        for (int i = p, j = pattern.length - 1; i >= 0 && pattern[i] == pattern[j]; --i, --j) 
               l += 1;
        return l;
    }
    /** Function to calculate index of pattern substring **/
    public int index(char[] text, char[] pattern) 
    {
        if (pattern.length == 0) 
            return 0;
        int charTable[] = charTable(pattern);
        int offsetTable[] = offsetTable(pattern);
        for (int i = pattern.length - 1, j; i < text.length;) 
        {
            for (j = pattern.length - 1; pattern[j] == text[i]; --i, --j) 
                     if (j == 0) 
                    return i;
 
              // i += pattern.length - j; // For naive method
              i += Math.max(offsetTable[pattern.length - 1 - j], charTable[text[i]]);
        }
        return -1;
      }
      /** Makes the jump table based on the mismatched character information **/
      public int[] charTable(char[] pattern) 
      {
        final int ALPHABET_SIZE = 256;
        int[] table = new int[ALPHABET_SIZE];
        for (int i = 0; i < table.length; ++i) 
               table[i] = pattern.length;
        for (int i = 0; i < pattern.length - 1; ++i) 
               table[pattern[i]] = pattern.length - 1 - i;
        return table;
      }
      /** Makes the jump table based on the scan offset which mismatch occurs. **/
      
    /** function to check if needle[p:end] a prefix of pattern **/
    
    String str1;
    private String LCSS(String[] str1, String[] str2){
        
int l1 = str1.length;
     int l2 = str2.length;

     int[][] arr = new int[l1 + 1][l2 + 1];

     for (int i = l1 - 1; i >= 0; i--)
     {
         for (int j = l2 - 1; j >= 0; j--)
         {
             if (str1[i].equalsIgnoreCase(str2[j]))
                 arr[i][j] = arr[i + 1][j + 1] + 1;
             else 
                 arr[i][j] = Math.max(arr[i + 1][j], arr[i][j + 1]);
         }
     }

     int i = 0, j = 0;
     String sb = "";
     while (i < l1 && j < l2) 
     {
         if (str1[i].equalsIgnoreCase(str2[j])) 
         {
             sb+= str1[i]+" ";
             i++;
             j++;
             //a=true;
         }
         else if (arr[i + 1][j] >= arr[i][j + 1]) 
             i++;
         else
             j++;
     }
     return sb.toString();
   

}
    private String[] breakFileIntoSentences(File file) {
//Scanner sentenceScanner = null;
//System.out.println(file);
ArrayList<String> sentenceList = null;
String[] brokenSentencesArray=null;
String line=null;
String text="";
// TODO Auto-generated method stub
String filePathString=file.getAbsolutePath();
//System.out.println(file.getName());
//String correctedFilePath = filePathString.replace('\\', '/');
try {
FileReader fileReader = new FileReader(filePathString);
BufferedReader bufferedReader = new BufferedReader(fileReader);
while((line = bufferedReader.readLine())!=null){
text+=line;
}//System.out.println(text);
bufferedReader.close();
} catch (FileNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
brokenSentencesArray = text.split("(?<=[.!?])\\s*"); 
return brokenSentencesArray;
}

    public DetectPlagiarism() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textArea3 = new java.awt.TextArea();
        panel1 = new java.awt.Panel();
        button1 = new java.awt.Button();
        naive = new java.awt.Button();
        kmp = new java.awt.Button();
        boyermoore = new java.awt.Button();
        lcss = new java.awt.Button();
        display = new java.awt.TextArea();
        label1 = new java.awt.Label();
        textField1 = new java.awt.TextField();
        textField2 = new java.awt.TextField();
        textArea1 = new java.awt.TextArea();
        textArea2 = new java.awt.TextArea();
        percent = new java.awt.TextField();
        textField4 = new java.awt.TextField();
        label2 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));

        panel1.setBackground(new java.awt.Color(102, 102, 102));

        button1.setActionCommand("Import ");
        button1.setBackground(new java.awt.Color(0, 153, 153));
        button1.setLabel("Import File");
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button1MouseClicked(evt);
            }
        });
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        naive.setActionCommand("Naive");
        naive.setBackground(new java.awt.Color(0, 153, 153));
        naive.setLabel("Naive");
        naive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                naiveMouseClicked(evt);
            }
        });
        naive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                naiveActionPerformed(evt);
            }
        });

        kmp.setActionCommand("kmp");
        kmp.setBackground(new java.awt.Color(0, 153, 153));
        kmp.setLabel("kmp");
        kmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmpActionPerformed(evt);
            }
        });

        boyermoore.setActionCommand("boyer_moore");
        boyermoore.setBackground(new java.awt.Color(0, 153, 153));
        boyermoore.setLabel("boyer-moore");
        boyermoore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boyermooreActionPerformed(evt);
            }
        });

        lcss.setActionCommand("lcss");
        lcss.setBackground(new java.awt.Color(0, 153, 153));
        lcss.setLabel("lcss");
        lcss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lcssActionPerformed(evt);
            }
        });

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setBackground(new java.awt.Color(0, 204, 204));
        label1.setForeground(new java.awt.Color(102, 102, 102));
        label1.setSize(new java.awt.Dimension(50, 25));
        label1.setText("PLAGIARISM DETECTOR");

        textField1.setText("textField1");

        textField2.setText("textField2");

        percent.setText("textField1");
        percent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                percentActionPerformed(evt);
            }
        });

        textField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField4ActionPerformed(evt);
            }
        });

        label2.setAlignment(1);
        label2.setBackground(new java.awt.Color(0, 204, 204));
        label2.setText("Percentage plagiarized:");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(boyermoore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lcss, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(naive, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kmp, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(textField4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addComponent(display, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(display, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(naive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boyermoore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lcss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );

        naive.getAccessibleContext().setAccessibleName("naive");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void naiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_naiveActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_naiveActionPerformed
     String s;
     String d;
     String name;
     File path;
    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        //int result;
              java.lang.String file_path_expo = null;
        JFileChooser chooser = new JFileChooser();
chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      }
    else {
      System.out.println("No Selection ");
      }
      File path = chooser.getSelectedFile();
      //System.out.print(path);
       File path_d=chooser.getCurrentDirectory();
       
      s=path.getAbsolutePath();
      name=path.getName();
      d=path_d.getAbsolutePath();
       display.append("\nSelected file:"+s);
      display.append("\nSelected folder:"+d+"\n");
      
   
   
 
                                           
         
    }//GEN-LAST:event_button1ActionPerformed

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_button1MouseClicked

    private void naiveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_naiveMouseClicked
        // TODO add your handling code here 
        long startTime = System.nanoTime();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //display.appendln("Enter Folder path:");
        String folderpath = d;
        final File folder = new File(folderpath);
        display.append("\n"+"List of Files:");
listFilesForFolder(folder);
         String testdoc1 = s;
         String pat=read(testdoc1);
         String[] pat1=split(pat);
         boolean s=false;
         int count=0;
         display.append("\n"+"-------------------------------------------------------------PATTERN-------------------------------------------------------------\n");
print(pat1);
 int patl = pat1.length;
 //display.append("nn"+name);
File[] Files1=extract(folderpath,name);
int l=Files1.length;
display.append("\n"+"------------------------------------------------------PLAGIARIARISM CHECK---------------------------------------------------\n");
for(int b=0;b<patl;b++){
    display.append("\n"+"Sentence"+b+": "+pat1[b]+"\n\n"); 
    int flag=0;
for(int i=0;i<l;i++) {
    
      if (Files1[i].isFile()) {
        //display.appendln("File:" + Files1[i].getName());
        String path=Files1[i].getAbsolutePath();
        //display.appendln("FilePath:" +path);
        String text = read(path);
        String[] txt1=split(text);
        int length = txt1.length; 
for(int a=0;a<length;a++)
{   
    //display.appendln(txt1[a]); 
    s=NaiveSearch(pat1[b],txt1[a]);
    if(s==true)
    {
        display.append("\n"+"**Copied from "+ Files1[i].getName()+ " sentence:"+a+" ");
        //display.setForeground(RED);
        display.append("\n"+txt1[a]);
        flag=1;    
    }
}
 }   //display.appendln(text);
       else if (Files1[i].isDirectory()) {
        display.append("\nDirectory " + Files1[i].getName());
      }
    }
 display.append("\n--------------------------------------------------------------------------------------------------------------------------------\n");
if(flag==0)
    {
    //display.append("Not Copied");
    }
else
{
    count=count+1;
}
}
  display.append("\n--------------------------------------------------------ANALYSIS SUMMARY---------------------------------------------------------\n");
  display.append("\n"+"Total Sentences copied:"+count+"\n"); 
  float p=Math.round((count * 100)/patl);
  display.append("\n"+"Percentage copied: "+p+"%\n");
         display.append("\n*******************************************************************************************************************************************\n");
textField4.setText(Float.toString(p)+"%");
long endTime = System.nanoTime();
long duration = (endTime - startTime);
System.out.println("Naive duration"+duration);
    }//GEN-LAST:event_naiveMouseClicked

    private void kmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmpActionPerformed
        // TODO add your handling code here:
        long startTime = System.nanoTime();
    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //display.appendln("Enter Folder path:");
        String folderpath = d;
        final File folder = new File(folderpath);
        display.append("\n"+"List of Files:");
listFilesForFolder(folder);
        String testdoc1 = s;
         String pat=read(testdoc1);
         String[] pat1=split(pat);
         boolean s=false;
         int count=0;
         display.append("\n-------------------------------------------------------------PATTERN-------------------------------------------------------------\n");
print(pat1);
 int patl = pat1.length;
File[] Files1=extract(folderpath,name);
int l=Files1.length;
display.append("\n------------------------------------------------------PLAGIARIARISM CHECK---------------------------------------------------\n");
for(int b=0;b<patl;b++){
    display.append("\n"+"Sentence"+b+": "+pat1[b]+"\n\n"); 
    int flag=0;
for(int i=0;i<l;i++) {
    
      if (Files1[i].isFile()) {
        //display.appendln("File:" + Files1[i].getName());
        String path=Files1[i].getAbsolutePath();
        //display.appendln("FilePath:" +path);
        String text = read(path);
        String[] txt1=split(text);
        int length = txt1.length; 
       // display.append("------------------------------------TEXT---------------------------------------------\n");
//print(txt1);
for(int a=0;a<length;a++)
{   
    //display.appendln(txt1[a]); 
    s=KMP(pat1[b],txt1[a]);
    if(s==true)
    {
        display.append("\n"+"**Copied from "+ Files1[i].getName()+ " sentence:"+a+" ");
        display.append("\n"+txt1[a]);
        flag=1;    
    }
}
 }   //display.appendln(text);
       else if (Files1[i].isDirectory()) {
        display.append("\n"+"Directory " + Files1[i].getName());
      }
    }
 display.append("\n--------------------------------------------------------------------------------------------------------------------------------\n");
if(flag==0)
    {
    //display.append("Not Copied");
    }
else
{
    count=count+1;
}
}
  display.append("\n--------------------------------------------------------ANALYSIS SUMMARY---------------------------------------------------------\n");
  display.append("\n"+"Total Sentences copied:"+count+"\n"); 
  float p=Math.round((count * 100)/patl);
  display.append("\n"+"Percentage copied: "+p+"%\n");
                display.append("\n****************************************************************************************************************************************************************\n");
 textField4.setText(Float.toString(p)+"%");
long endTime = System.nanoTime();
long duration = (endTime - startTime);
System.out.println("KMP duration:"+duration);
    }//GEN-LAST:event_kmpActionPerformed

    private void boyermooreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boyermooreActionPerformed
        // TODO add your handling code here:
        long startTime = System.nanoTime();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //display.appendln("Enter Folder path:");
        String folderpath = d;
        final File folder = new File(folderpath);
        display.append("\n"+"List of Files:");
listFilesForFolder(folder);
        String testdoc1 = s;
        display.append("\n"+"testdoc:"+testdoc1);
         String pat=read(testdoc1);
         String[] pat1=split(pat);
         boolean s=false;
         int count=0;
         display.append("\n"+"-------------------------------------------------------------PATTERN-------------------------------------------------------------\n");
print(pat1);
 int patl = pat1.length;
File[] Files1=extract(folderpath,name);
int l=Files1.length;
display.append("\n"+"------------------------------------------------------PLAGIARIARISM CHECK---------------------------------------------------\n");
for(int b=0;b<patl;b++){
    display.append("\n"+"Sentence"+b+": "+pat1[b]+"\n\n"); 
    int flag=0;
for(int i=0;i<l;i++) {
    
      if (Files1[i].isFile()) {
        //display.appendln("File:" + Files1[i].getName());
        String path=Files1[i].getAbsolutePath();
        //display.appendln("FilePath:" +path);
        String text = read(path);
        String[] txt1=split(text);
        int length = txt1.length; 
       // display.append("------------------------------------TEXT---------------------------------------------\n");
//print(txt1);
for(int a=0;a<length;a++)
{    
   
        s=boyer_moore(txt1[a],pat1[b]);
    if(s==true)
    {
        display.append("\n"+"**Copied from "+ Files1[i].getName()+ " sentence:"+a+" ");
        display.append("\n"+txt1[a]);
        flag=1;    
    }
}
 }  
       else if (Files1[i].isDirectory()) {
        display.append("\n"+"Directory " + Files1[i].getName());
      }
    }
 display.append("\n--------------------------------------------------------------------------------------------------------------------------------\n");
if(flag==0)
    {
    //display.append("Not Copied");
    }
else
{
    count=count+1;
}
}
  display.append("\n--------------------------------------------------------ANALYSIS SUMMARY---------------------------------------------------------\n");
  display.append("\n"+"Total Sentences copied:"+count+"\n"); 
  float p=Math.round((count * 100)/patl);
  display.append("\n"+"Percentage copied: "+p+"%\n");
         display.append("\n***********************************************************************************************************************************************************************************\n");
textField4.setText(Float.toString(p)+"%");
long endTime = System.nanoTime();

long duration = (endTime - startTime);
System.out.println("Boyer-Moore duration"+duration);

    }//GEN-LAST:event_boyermooreActionPerformed

    private void percentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_percentActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_percentActionPerformed

    private void lcssActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lcssActionPerformed
        // TODO add your handling code here:
        long startTime = System.nanoTime();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String folderpath = d;
        final File folder = new File(folderpath);
        display.append("\n"+"List of Files:");
listFilesForFolder(folder);
        String testdoc1 = s;
        display.append("\n"+"testdoc:"+testdoc1);
         String pat=read(testdoc1);
         String[] pat1=split(pat);
         boolean s=false;
         int count=0;
         display.append("\n"+"-------------------------------------------------------------PATTERN-------------------------------------------------------------\n");
print(pat1);
 int patl = pat1.length;
File[] Files1=extract(folderpath,name);
int l=Files1.length;

       String[] brokenSentences;
String matched="";
float denominator;
float numerator=0;
float percentage;
String[] arr;
int flag=0;
//System.out.print(path);
File path1;
    path1 = new File(testdoc1);
   display.append("\n"+"testdoc"+testdoc1);
plagiarizedSentences = breakFileIntoSentences(path1);
    float[] max_array_final={};
    max_array_final = new float[plagiarizedSentences.length];

for(int j=0;j<plagiarizedSentences.length;j++){
    display.append("\n\nSentence "+j+":"+plagiarizedSentences[j]+"\n");
    float[] max_array={};
    float[] fArray={};
    max_array = new float[Files1.length];

//max_array = new float[] {};
for(int i=0;i<Files1.length;i++){
List<Float> fList = new ArrayList<Float>();


display.append("\n"+"File "+(i+1)+":");
brokenSentences = breakFileIntoSentences(Files1[i]);

for(int k=0;k<brokenSentences.length;k++){
    
String[] words1 = brokenSentences[k].replace(".", "").replace(",", "").replace("?", "").replace("!","").replace(";","").split(" ");
String[] words2 = plagiarizedSentences[j].replace(".", "").replace(",", "").replace("?", "").replace("!","").replace(";","").split(" ");
matched = LCSS(words1,words2);
 arr = matched.split(" ");
 

denominator = words2.length;
if(arr[0]==""){
numerator=0;
//System.out.println("Not copied");
//System.out.println("in if");
//System.out.println("numerator is "+numerator);
            percentage = (numerator/denominator)*100;
            fList.add(percentage);
 
}else{
   
             numerator = arr.length;
             //System.out.println("numerator is "+numerator);
             percentage = (numerator/denominator)*100;
             fList.add(percentage);
}
             if(percentage>35){
                  display.append("\n\nSentence "+(k+1)+" of file "+(i+1)+" is: "+brokenSentences[k]);
    //System.out.println("Sentence "+(j+1)+" from plagiarized file is: "+plagiarizedSentences[j]);
    if(matched!=""){
display.append("\n"+"Longest Common Subsequence of sentence "+(j+1)+": "+ matched);
}
                              //System.out.println("Percentage of line "+(j+1)+" plagiarized from file "+(i+1)+" of sentence "+(k+1)+" is: );

            display.append("\n"+"*****Sentence "+(j)+" has been "+percentage+"% plagiarized from sentence "+(k+1)+" of "+Files1[i].getName()+"******\n");
              }
}
//System.out.println(fList);
float total=0;
fArray = new float[fList.size()];
for(int m=0;m<fArray.length;m++){
fArray[m] = fList.get(m);
}
float max = fArray[0];

for (int o = 1;o < fArray.length; o++) {
    if (fArray[o] > max) {
      max = fArray[o];
    }
}
for(int x=0;x<fArray.length;x++){
total+=fArray[x];
}
//System.out.println("Total is: "+total);
//System.out.println("Array length is: "+fArray.length);
display.append("\n"+"Change Check"+"Sentence "+j+" plagiarized from "+Files1[i].getName()+" is "+max+"%");
display.append("\n"+"Sentence "+j+" plagiarized from "+Files1[i].getName()+" is "+(total/fArray.length)+"%");

display.append("\n"+"----------------------------------------------------------------------------------------------");
max_array[i]=max;



 
}
float max_o=max_array[0];
for (int o = 1;o < max_array.length; o++) {
    if (max_array[o] > max_o) {
      max_o = max_array[o];
    }
}
max_array_final[j]=max_o;
display.append("\n"+"Check"+"Sentence "+j+" copied percentage:"+max_o);
display.append("\n"+"----------------------------------------------------------------------------------------------");

}
float total=0;
//max_array_final = new float[fList.size()];
for(int m=0;m<max_array_final.length;m++){
max_array_final[m] = max_array_final[m];
}
for(int x=0;x<max_array_final.length;x++){
total+=max_array_final[x];
}
System.out.println("Total:"+total);

float p;
    p = Math.round(total/max_array_final.length);
    display.append("\n"+"Check"+"Total percentage copied:"+p+"%");
    
   display.append("\n"+"***********************************************************************************************************************************************************************************\n");
    textField4.setText(Float.toString(p)+"%");

   long endTime = System.nanoTime();

long duration = (endTime - startTime);
System.out.println("LCSS duration"+duration);

    }//GEN-LAST:event_lcssActionPerformed

    private void textField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DetectPlagiarism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetectPlagiarism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetectPlagiarism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetectPlagiarism.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetectPlagiarism().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button boyermoore;
    private java.awt.Button button1;
    private java.awt.TextArea display;
    private java.awt.Button kmp;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Button lcss;
    private java.awt.Button naive;
    private java.awt.Panel panel1;
    private java.awt.TextField percent;
    private java.awt.TextArea textArea1;
    private java.awt.TextArea textArea2;
    private java.awt.TextArea textArea3;
    private java.awt.TextField textField1;
    private java.awt.TextField textField2;
    private java.awt.TextField textField4;
    // End of variables declaration//GEN-END:variables
}
