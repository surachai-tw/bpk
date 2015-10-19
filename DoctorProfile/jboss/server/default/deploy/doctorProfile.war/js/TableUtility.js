function getRowSelected(row)
{
	for( ; row.tagName != "TR"; )
		row = row.parentElement;
	return row;
}

//หาสีปกติของแถว (ที่ไม่ได้เลือก หรือ mouseover)
function getNormalRowClassName(row, hasHeader)
{
	var tbl = row.parentElement;
	var modNo = 0;
	if(hasHeader == true)
		modNo = 1;
	
	while(tbl.tagName != "TABLE")
		tbl = tbl.parentElement;

	var rowStatus = row.status;
	
	if(rowStatus && tbl.rowStatus && (rowStatus in tbl.rowStatus))
		return tbl.rowStatus[rowStatus];
	else
		return row.rowIndex%2 == modNo ? "lineOdd" : "lineEven";
}

//ล้างตาราง
function clearTable(tbl)
{
	for( ; tbl.rows.length > 0; )
		tbl.deleteRow(0);

	tbl.selectedRow = -1;
}

//ล้างสีของตาราง
function clearTableStyle(tbl, hasHeader)
{
	var i = 0;
	if(hasHeader == true)
		i = 1;
	for( ; i < tbl.rows.length; i++)
	{
		tbl.rows[i].className = getNormalRowClassName(tbl.rows[i], hasHeader);
	}
}

function tableClick(event, tbl)
{
	var cell = event.srcElement;
	if(cell && cell.tagName)
	{
		while(cell && cell.tagName != "TD")
			cell = cell.parentElement;

		if(cell)
		{
			var eventTable = cell;
			while(eventTable.tagName != "TABLE")
				eventTable = eventTable.parentElement;

			if(eventTable == tbl)
			{
				if(cell.tagName == "TD")
				{
					var row = getRowSelected(cell);

					//clear สีแถวที่เคยเลือกมาก่อน
					if(tbl.selectedRow != -1)
					{
						var oldRow = tbl.rows[tbl.selectedRow];
						oldRow.className = getNormalRowClassName(oldRow);
					}

					tbl.selectedRow = row.rowIndex;
					row.className = "bgHilite";
				}
			}
		}
	}
}

function tableOver(event, tbl)
{
	var cell = event.srcElement;
	if(cell.tagName == "TD")
	{
		var row = getRowSelected(cell);

		if(row.rowIndex != tbl.selectedRow)
		{
			row.className = "bgOver";
		}
	}
}

function tableOut(event, tbl)
{
	var cell = event.srcElement;
	if(cell.tagName == "TD")
	{
		var row = getRowSelected(cell);

		if(row.rowIndex != tbl.selectedRow)
		{
			row.className = getNormalRowClassName(row);
		}
	}
}