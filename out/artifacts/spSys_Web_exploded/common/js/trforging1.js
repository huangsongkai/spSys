var bgColor; // ±£´æ±»¾°É«
var idx = "f";
function MouseMoveIn(res)
{
	if (res.id == idx)
	{
		// document.getElementById(idx1).style.backgroundColor="";
	}
	else
	{
		if (!res.contains(event.fromElement))
		{
			bgColor = res.style.backgroundColor;
			res.style.backgroundColor = "#BDDFFF";
		}
	}
}
function MouseMoveOut(res)
{
	if (res.id == idx)
	{
		// document.getElementById(idx1).style.backgroundColor="";
	}
	else
	{
		if (!res.contains(event.toElement))
		{
			res.style.backgroundColor = bgColor;
		}
	}
}
function setRowFocus(res, idx1)
{
	if (idx != "f")
	{
		document.getElementById(idx).style.backgroundColor = "";
	}
	if (!res.contains(event.fromElement))
	{
		res.style.backgroundColor = "#6699FF";
	}
	idx = idx1;
}