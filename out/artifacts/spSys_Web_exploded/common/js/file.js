	var fileCount=0;
	function createUploadFile(allowFileCount){
		if(allowFileCount==-1||allowFileCount-fileCount!=0)
		{
			var fileTable = document.getElementById("fileTable"); 
			//����������һ�� 
			var myNewRow = fileTable.insertRow(fileTable.rows.length); 
			//ȡ�ñ���������
			var aRows=fileTable.rows;
			//ȡ�ñ�����������
			var aCells=myNewRow.cells;
			//���������������ĸ�����
			var oCell1_1=aRows[myNewRow.rowIndex].insertCell(aCells.length);
			var oCell1_2=aRows[myNewRow.rowIndex].insertCell(aCells.length);
			//�������������html�ı�
			oCell1_1.innerHTML='<input type="file" name="upload">';
			oCell1_2.innerHTML='<input type="button" value="ɾ��" onclick="javascript:del_fileRow(this.parentNode.parentNode)">';
			fileCount=fileCount+1;
		}
		else
		{
			alert("��ǰ�������������ϴ�"+allowFileCount+"��������");
		}
	}
	function del_fileRow(obj){
		var tab = document.getElementById("fileTable");
		for(var i=0;i<tab.rows.length;i++){
			if(tab.rows[i]==obj){
				tab.deleteRow(i);
				fileCount=fileCount-1;
				break;
			}
		} 
	}