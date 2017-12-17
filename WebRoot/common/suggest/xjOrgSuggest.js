	var xjOrgSuggest =
	{
		inputId:null,
		setOrgIdTo:null,
		setOrgNameTo:null,
		width:310,
		initOrgSuggest : function (){
			if(xjOrgSuggest.inputId!==null)
			{
				$("#"+xjOrgSuggest.inputId).autocomplete(orgs, {
					minChars: 0,
					width: xjOrgSuggest.width,
					matchContains: "word",
					autoFill: false,
					formatItem: function(row, i, max) {
						return i + "/" + max + ": \"" + row.orgId + "\" [" + row.orgName + "]";
					},
					formatMatch: function(row, i, max) {
						return row.orgName + " " + row.qp + " " + row.jp + " " + row.orgId;
					},
					formatResult: function(row) {
						return row.orgName;
					},
					rowSelected : function(row){
						if(xjOrgSuggest.setOrgIdTo!==null)
							$("#"+xjOrgSuggest.setOrgIdTo).val(row.orgId);
						if(xjOrgSuggest.setOrgNameTo!==null)
							$("#"+xjOrgSuggest.setOrgNameTo).val(row.orgName);
					}
				});
			}
		}
	}