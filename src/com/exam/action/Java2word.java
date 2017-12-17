package com.exam.action;


	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Iterator;

	import com.jacob.activeX.ActiveXComponent;
	import com.jacob.com.Dispatch;
	import com.jacob.com.Variant;

 public class Java2word{
    
	     private boolean saveOnExit;
	     /**
	      * word�ĵ�
	      */
	     Dispatch doc = null;
	    
	     /**
	      * word���г������s
	      */
	     private   ActiveXComponent word;
	     /**
	      * ����word�ĵ�
	      */
	     private   Dispatch documents;
	     
	    
	     /**    
	     37.     *  Selection ���� �����ڻ򴰸��еĵ�ǰ��ѡ���ݡ� ��ѡ���ݴ����ĵ���ѡ������ͻ����ʾ������������ĵ���û��ѡ���κ����ݣ���������㡣    
	     38.     *  ÿ���ĵ�����ֻ����һ��Selection ���󣬲���������Ӧ�ó�����ֻ����һ����� Selection ����    
	     39.     */     
	     private Dispatch selection = null; 
	     /** word�ĵ�    
	       * �ڱ����������ַ�ʽ���Խ����ĵ��Ĵ���,<br>    
	       * ��һ�ֵ��� createNewDocument    
	       * �ڶ��ֵ��� openDocument     
	       */       
         private Dispatch document = null;      

	     /**
	      * ���캯��
	      */
	     public Java2word() {
	         if(word==null){
	         word = new ActiveXComponent("Word.Application");
	         word.setProperty("Visible",new Variant(false));
	         }
	         if(documents==null)
	         documents = word.getProperty("Documents").toDispatch();
	         saveOnExit = false;
	     }
	    
	     /**
	      * ���ò������˳�ʱ�Ƿ񱣴�
	      * @param saveOnExit boolean true-�˳�ʱ�����ļ���false-�˳�ʱ�������ļ�
	      */
	     public void setSaveOnExit(boolean saveOnExit) {
	         this.saveOnExit = saveOnExit;
	     }
	     /**
	      * �õ��������˳�ʱ�Ƿ񱣴�
	      * @return boolean true-�˳�ʱ�����ļ���false-�˳�ʱ�������ļ�
	      */
	     public boolean getSaveOnExit() {
	         return saveOnExit;
	     }
	    
	     /**
	      * ���ļ�
	      * @param inputDoc String Ҫ�򿪵��ļ���ȫ·��
	      * @return Dispatch �򿪵��ļ�
	      */
	     public Dispatch open(String inputDoc) {
	         return Dispatch.call(documents,"Open",inputDoc).toDispatch();
	         //return Dispatch.invoke(documents,"Open",Dispatch.Method,new Object[]{inputDoc},new int[1]).toDispatch();
	     }
	    
	     /**
	      * ѡ������
	      * @return Dispatch ѡ���ķ�Χ������
	      */
	     public Dispatch select() {
	         return word.getProperty("Selection").toDispatch();
	     }
	    
	     /**
	      * ��ѡ�����ݻ����������ƶ�
	      * @param selection Dispatch Ҫ�ƶ�������
	      * @param count int �ƶ��ľ���
	      */
	     public void moveUp(Dispatch selection,int count) {
	         for(int i = 0;i < count;i ++)
	             Dispatch.call(selection,"MoveUp");
	     }
	    
	     /**
	      * ��ѡ�����ݻ����������ƶ�
	      * @param selection Dispatch Ҫ�ƶ�������
	      * @param count int �ƶ��ľ���
	      */
	     public void moveDown(Dispatch selection,int count) {
	         for(int i = 0;i < count;i ++)
	             Dispatch.call(selection,"MoveDown");
	     }
	    
	     /**
	      * ��ѡ�����ݻ����������ƶ�
	      * @param selection Dispatch Ҫ�ƶ�������
	      * @param count int �ƶ��ľ���
	      */
	     public void moveLeft(Dispatch selection,int count) {
	         for(int i = 0;i < count;i ++) {
	             Dispatch.call(selection,"MoveLeft");
	         }
	     }
	    
	     /**
	      * ��ѡ�����ݻ����������ƶ�
	      * @param selection Dispatch Ҫ�ƶ�������
	      * @param count int �ƶ��ľ���
	      */
	     public void moveRight(Dispatch selection,int count) {
	         for(int i = 0;i < count;i ++)
	             Dispatch.call(selection,"MoveRight");
	     }
	    
	     /**
	      * �Ѳ�����ƶ����ļ���λ��
	      * @param selection Dispatch �����
	      */
	     public void moveStart(Dispatch selection) {
	         Dispatch.call(selection,"HomeKey",new Variant(6));
	     }
	    
	     /**
	      * ��ѡ�����ݻ����㿪ʼ�����ı�
	      * @param selection Dispatch ѡ������
	      * @param toFindText String Ҫ���ҵ��ı�
	      * @return boolean true-���ҵ���ѡ�и��ı���false-δ���ҵ��ı�
	      */
	     public boolean find(Dispatch selection,String toFindText) {
	         //��selection����λ�ÿ�ʼ��ѯ
	         Dispatch find = word.call(selection,"Find").toDispatch();
	         //����Ҫ���ҵ�����
	         Dispatch.put(find,"Text",toFindText);
	         //��ǰ����
	         Dispatch.put(find,"Forward","True");
	         //���ø�ʽ
	         Dispatch.put(find,"Format","True");
	         //��Сдƥ��
	         Dispatch.put(find,"MatchCase","True");
	         //ȫ��ƥ��
	         Dispatch.put(find,"MatchWholeWord","True");
	         //���Ҳ�ѡ��
	         return Dispatch.call(find,"Execute").getBoolean();
	     }
	    
	     /**
	      * ��ѡ�������滻Ϊ�趨�ı�
	      * @param selection Dispatch ѡ������
	      * @param newText String �滻Ϊ�ı�
	      */
	     public void replace(Dispatch selection,String newText) {
	         //�����滻�ı�
	         Dispatch.put(selection,"Text",newText);
	     }
	    
	     /**
	      * ȫ���滻
	      * @param selection Dispatch ѡ�����ݻ���ʼ�����
	      * @param oldText String Ҫ�滻���ı�
	      * @param newText String �滻Ϊ�ı�
	      */
	     public void replaceAll(Dispatch selection,String oldText,Object replaceObj) {
	         //�ƶ����ļ���ͷ
	         moveStart(selection);
	        
	         if(oldText.startsWith("table") || replaceObj instanceof ArrayList)
	             replaceTable(selection,oldText,(ArrayList) replaceObj);
	         else {
	             String newText = (String) replaceObj;
	             if(newText==null)
	                 newText="";
	             if(oldText.indexOf("image") != -1&!newText.trim().equals("") || newText.lastIndexOf(".bmp") != -1 || newText.lastIndexOf(".jpg") != -1 || newText.lastIndexOf(".gif") != -1){
	                 while(find(selection,oldText)) {
	                     replaceImage(selection,newText);
	                     Dispatch.call(selection,"MoveRight");
	                 }
	             }else{
	                 while(find(selection,oldText)) {
	                     replace(selection,newText);
	                     Dispatch.call(selection,"MoveRight");
	                 }
	             }
	         }
	     }
	    
	     /**
	      * �滻ͼƬ
	      * @param selection Dispatch ͼƬ�Ĳ����
	      * @param imagePath String ͼƬ�ļ���ȫ·����
	      */
	     public void replaceImage(Dispatch selection,String imagePath) {
	         Dispatch.call(Dispatch.get(selection,"InLineShapes").toDispatch(),"AddPicture",imagePath);
	     }
	    
	     /**
	      * �滻���
	      * @param selection Dispatch �����
	      * @param tableName String ������ƣ�����table$1@1��table$2@1...table$R@N��R����ӱ���еĵ�N�п�ʼ��䣬N����word�ļ��еĵ�N�ű�
	      * @param fields HashMap �����Ҫ�滻���ֶ������ݵĶ�Ӧ��
	      */
	     public void replaceTable(Dispatch selection,String tableName,ArrayList dataList) {
	         if(dataList.size() <= 1) {
	             System.out.println("Empty table!");
	             return;
	         }
	        
	         //Ҫ������
	         String[] cols = (String[]) dataList.get(0);
	        
	         //������
	         String tbIndex = tableName.substring(tableName.lastIndexOf("@") + 1);
	         //�ӵڼ��п�ʼ���
	         int fromRow = Integer.parseInt(tableName.substring(tableName.lastIndexOf("$") + 1,tableName.lastIndexOf("@")));
	         //���б��
	         Dispatch tables = Dispatch.get(doc,"Tables").toDispatch();
	         //Ҫ���ı��
	         Dispatch table = Dispatch.call(tables,"Item",new Variant(tbIndex)).toDispatch();
	         //����������
	         Dispatch rows = Dispatch.get(table,"Rows").toDispatch();
	         //�����
	         for(int i = 1;i < dataList.size();i ++) {
	             //ĳһ������
	             String[] datas = (String[]) dataList.get(i);
	            
	             //�ڱ�������һ��
	             if(Dispatch.get(rows,"Count").getInt() < fromRow + i - 1)
	                 Dispatch.call(rows,"Add");
	             //�����е������
	             for(int j = 0;j < datas.length;j ++) {
	                 //�õ���Ԫ��
	                 Dispatch cell = Dispatch.call(table,"Cell",Integer.toString(fromRow + i - 1),cols[j]).toDispatch();
	                 //ѡ�е�Ԫ��
	                 Dispatch.call(cell,"Select");
	                 //���ø�ʽ
	                 Dispatch font = Dispatch.get(selection,"Font").toDispatch();
	                 Dispatch.put(font,"Bold","0");
	                 Dispatch.put(font,"Italic","0");

	                 //��������
	                 Dispatch.put(selection,"Text",datas[j]);
	             }
	         }
	     }
	    
	     /**
	      * �����ļ�
	      * @param outputPath String ����ļ�������·����
	      */
	     public void save(String outputPath) {
	         Dispatch.call(Dispatch.call(word,"WordBasic").getDispatch(),"FileSaveAs",outputPath);
	     }
	    
	     /**
	      * �ر��ļ�
	      * @param document Dispatch Ҫ�رյ��ļ�
	      */
	     public void close(Dispatch doc) {
	         Dispatch.call(doc,"Close",new Variant(saveOnExit));
	         word.invoke("Quit",new Variant[]{});
	         word = null;
	     }
	    
	     /**
	      * ����ģ�塢��������word�ļ�
	      * @param inputPath String ģ���ļ�������·����
	      * @param outPath String ����ļ�������·����
	      * @param data HashMap ���ݰ�������Ҫ�����ֶΡ���Ӧ�����ݣ�
	      */
	     public void toWord(String inputPath,String outPath,HashMap data) {
	         String oldText;
	         Object newValue;
	         try {
	             if(doc==null)
	             doc = open(inputPath);
	            
	             Dispatch selection = select();
	            
	             Iterator keys = data.keySet().iterator();
	             while(keys.hasNext()) {
	                 oldText = (String) keys.next();
	                 newValue = data.get(oldText);
	                
	                 replaceAll(selection,oldText,newValue);
	             }
	            
	             save(outPath);
	         } catch(Exception e) {
	             System.out.println("����word�ļ�ʧ�ܣ�");
	             e.printStackTrace();
	         } finally {
	             if(doc != null)
	                 close(doc);
	         }
	     }
	////////////////////////////////////////////////////////////////////////////////////////////////////////     
	     public void toWord(String inputPath,String outPath,HashMap data,String type) {
	         String oldText;
	         Object newValue;
	         try {
	             if(doc==null)
	             doc = open(inputPath);
	            
	             Dispatch selection = select();
	            
	             Iterator keys = data.keySet().iterator();
	             while(keys.hasNext()) {
	                 oldText = (String) keys.next();
	                 newValue = data.get(oldText);
	                
	                 replaceAll(selection,oldText,newValue,type);
	             }
	            
	             save(outPath);
	         } catch(Exception e) {
	             System.out.println("����word�ļ�ʧ�ܣ�");
	             e.printStackTrace();
	         } finally {
	             if(doc != null)
	                 close(doc);
	         }
	     }
	     
	     public void replaceAll(Dispatch selection,String oldText,Object replaceObj,String type) {
	         //�ƶ����ļ���ͷ
	         moveStart(selection);
	        
	         if(oldText.startsWith("table") || replaceObj instanceof ArrayList)
	             replaceTable(selection,oldText,(ArrayList) replaceObj,type);
	         else {
	             String newText = (String) replaceObj;
	             if(newText==null)
	                 newText="";
	             if(oldText.indexOf("image") != -1&!newText.trim().equals("") || newText.lastIndexOf(".bmp") != -1 || newText.lastIndexOf(".jpg") != -1 || newText.lastIndexOf(".gif") != -1){
	                 while(find(selection,oldText)) {
	                     replaceImage(selection,newText);
	                     Dispatch.call(selection,"MoveRight");
	                 }
	             }else{
	                 while(find(selection,oldText)) {
	                     replace(selection,newText);
	                     Dispatch.call(selection,"MoveRight");
	                 }
	             }
	         }
	     }
	     
	     public void replaceTable(Dispatch selection,String tableName,ArrayList dataList,String type) {
	         if(dataList.size() <= 1) {
	             System.out.println("Empty table!");
	             return;
	         }
	        
	         //Ҫ������
	         String[] cols = (String[]) dataList.get(0);
	        
	         //������
	         String tbIndex = tableName.substring(tableName.lastIndexOf("@") + 1);
	         //�ӵڼ��п�ʼ���
	         int fromRow = Integer.parseInt(tableName.substring(tableName.lastIndexOf("$") + 1,tableName.lastIndexOf("@")));
	         //���б��
	         Dispatch tables = Dispatch.get(doc,"Tables").toDispatch();
	         //Ҫ���ı��
	         Dispatch table = Dispatch.call(tables,"Item",new Variant(tbIndex)).toDispatch();
	         //����������
	         Dispatch rows = Dispatch.get(table,"Rows").toDispatch();
	         //�����
	         for(int i = 1;i < dataList.size();i ++) {
	             //ĳһ������
	             String[] datas = (String[]) dataList.get(i);
	            
	             //�ڱ�������һ��
	             if(Dispatch.get(rows,"Count").getInt() < fromRow + i - 1)
	                 Dispatch.call(rows,"Add");
	             //�����е������
	             for(int j = 0;j < datas.length;j ++) {
	                 //�õ���Ԫ��
	                 Dispatch cell = Dispatch.call(table,"Cell",Integer.toString(fromRow + i - 1),cols[j]).toDispatch();
	                 //ѡ�е�Ԫ��
	                 Dispatch.call(cell,"Select");
	                 //���ø�ʽ
	                 Dispatch font = Dispatch.get(selection,"Font").toDispatch();
	                 Dispatch.put(font,"Bold","0");
	                 Dispatch.put(font,"Italic","0");
	                 if(tbIndex.equals("1") && fromRow ==2){
	                	 Dispatch.put(font, "Name", new Variant("��̴իë������")); 
	                 }
	                 //��������
	                 Dispatch.put(selection,"Text",datas[j]);
	             }
	         }
	     }
	    ///////////////////////////////////////////////////////////////////////////////////////
	     public synchronized static void word(String inputPath,String outPath,HashMap data){
	         Java2word j2w = new Java2word();
	         j2w.toWord(inputPath,outPath,data);
	     }
	     
	     // �ϲ�������Ԫ��
	     public void mergeCell(Dispatch cell1, Dispatch cell2) {
	         Dispatch.call(cell1, "Merge", cell2);
	     }


	     
	     public static void main(String[] args) {

	     }
}
