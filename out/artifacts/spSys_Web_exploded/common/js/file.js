	var fileCount=0;
	function createUploadFile(allowFileCount){
		if(allowFileCount==-1||allowFileCount-fileCount!=0)
		{
			var fileTable = document.getElementById("fileTable"); 
			//向表格中增加一行 
			var myNewRow = fileTable.insertRow(fileTable.rows.length); 
			//取得表格的总行数
			var aRows=fileTable.rows;
			//取得表格的总网格数
			var aCells=myNewRow.cells;
			//向新增行中增加四个网格
			var oCell1_1=aRows[myNewRow.rowIndex].insertCell(aCells.length);
			var oCell1_2=aRows[myNewRow.rowIndex].insertCell(aCells.length);
			//设置两个网格的html文本
			oCell1_1.innerHTML='<input type="file" name="upload">';
			oCell1_2.innerHTML='<input type="button" value="删除" onclick="javascript:del_fileRow(this.parentNode.parentNode)">';
			fileCount=fileCount+1;
		}
		else
		{
			alert("当前功能至多允许上传"+allowFileCount+"个附件！");
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