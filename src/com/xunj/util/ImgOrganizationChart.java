package com.xunj.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;

/**
 * 生成图片组织结构
 * 
 */
public class ImgOrganizationChart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 宽度。
	private static int width = 40;
	// 高度。
	private static int height = 40;
	// 间距
	private static int widthSpacing = 20;
	private static int heigthSpacing = 60;

	// 字体高度
	private static int fontHeight = 18;
	// 字体宽度
	private static int fontWidth = 20;
	// 初始行起点
	private static int initNum = 0;

	public static List getList() throws Exception {

		/*
		 * 01 0101 0102 0103 010101 010102 010301 010302 01010101
		 * 
		 */
		List list = new ArrayList();
		Map map = new HashMap();
		map.put("pid", "0");
		map.put("id", "01");
		map.put("name", "一二三四五六七八九分号总堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "01");
		map.put("id", "0101");
		map.put("name", "分堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "01");
		map.put("id", "0102");
		map.put("name", "香堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "01");
		map.put("id", "0103");
		map.put("name", "食堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "0101");
		map.put("id", "010101");
		map.put("name", "美国分堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "0101");
		map.put("id", "010102");
		map.put("name", "日本分堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "0103");
		map.put("id", "010301");
		map.put("name", "西餐食堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "0103");
		map.put("id", "010302");
		map.put("name", "生猛海鲜食堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "010101");
		map.put("id", "01010101");
		map.put("name", "美国第五区异种生物分堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "010302");
		map.put("id", "01030201");
		map.put("name", "海底火山食堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "010302");
		map.put("id", "01030202");
		map.put("name", "海底火锅食堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "01030201");
		map.put("id", "0103020101");
		map.put("name", "火烧海带食堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "01030201");
		map.put("id", "0103020101");
		map.put("name", "火烧鲸鱼食堂");
		list.add(map);
		map = new HashMap();
		map.put("pid", "01030201");
		map.put("id", "0103020101");
		map.put("name", "火烧海底泥食堂");
		list.add(map);
		return list;
	}

	public static void createOrgPic(List dataList, int idLength, String headId,String filePath)
			throws Exception {
		TreeMap columnMap = new TreeMap();// 竖map
		int leve = 0;
		HashMap kindmap = new HashMap();// 父级map
		Iterator dateits = dataList.iterator();

		for (; dateits.hasNext();) {
			Map tempmap = (HashMap) dateits.next();
			Map datemap = new HashMap();
			String pid = (String) tempmap.get("pid");
			String id = (String) tempmap.get("id");
			String name = (String) tempmap.get("name");

			datemap.put("pid", pid);
			datemap.put("id", id);
			datemap.put("name", name);

			int dataWidth = width;// 默认宽度
			int dataHeight = height;// 默认高度

			String type = "";
			if (headId.equals(pid)) {
				// 横向
				type = "lateral";
				dataWidth = name.length() * fontWidth + widthSpacing;
			} else {
				// 纵向
				type = "longitudinal";
				dataHeight = +name.length() * fontWidth + widthSpacing;
			}
			// 计算出图像所占所需要的宽
			datemap.put("width", dataWidth);
			// 计算出图像所占所需要的高
			datemap.put("height", dataHeight);
			// 设状态是横向还是纵向
			datemap.put("type", type);
			// 取出最高的层数,层数由ID长度除以传入的层级位数来计算例010101就是三层
			int num = id.length() / idLength;
			if (leve < num) {
				leve = num;
			}

			Map codeMap = null;
			List codelist = new ArrayList();
			// 查出是否有父级，如果有，添加子级信息
			if (kindmap.containsKey(pid)) {
				codeMap = (Map) kindmap.get(pid);
				codeMap.put("codewidth", (Integer) codeMap.get("codewidth")
						+ dataWidth);
				codelist = (List) codeMap.get("codelist");
				codelist.add(datemap);
			} else {

				codeMap = new HashMap();
				codeMap.put("codewidth", dataWidth);
				codelist.add(datemap);
				codeMap.put("codelist", codelist);
				kindmap.put(pid, codeMap);
			}

			Map valueMap = new HashMap();
			// 如果此层存在的话，变更数数据，否则插入数据
			if (columnMap.containsKey(num)) {
				valueMap = (Map) columnMap.get(num);
				// 更新本层下的所属元素
				List wlist = (List) valueMap.get("list");
				wlist.add(datemap);

			} else {
				valueMap = new HashMap();
				// 更新本层下的所属元素
				List wlist = new ArrayList();
				wlist.add(datemap);
				valueMap.put("list", wlist);
				columnMap.put(num, valueMap);
			}

		}
		// 获取图像宽与高
		Iterator treemapits = columnMap.entrySet().iterator();
		// 图像宽度
		int imageWidth = 0;
		// 图像高度
		int imageHeigth = 0;
		int levenum = 0;
		Map blankMap = new HashMap();
		for (; treemapits.hasNext();) {
			// 获取层对象
			Entry e = (Entry) treemapits.next();
			Map map = (HashMap) e.getValue();
			List list = (List) map.get("list");
			int leveWidth = 0;// 层宽度
			int leveHeigth = 0;// 层高度
			int widthnum = 0;// 元素末尾占位数
			int widthnums = 0;// 元素启始占位数
			levenum++;
			Map blankNumMap = new TreeMap();
			// 存入下一层空白位置的map
			for (int i = 0; i < list.size(); i++) {

				Map po = (Map) list.get(i);

				String pid = (String) po.get("pid");
				String id = (String) po.get("id");
				String name = (String) po.get("name");
				int dataWidth = (Integer) po.get("width");

				if (headId.equals(pid)) {
					// 获得下级占位数
					int inittemp = codeNum(kindmap, id, 1, 0);
					// 获得下级占位数所需宽度
					int inittempWidth = inittemp * (widthSpacing + width);
					// 如果下级占位数所需宽度小于元素图像宽度
					if (inittempWidth < dataWidth) {
						double dataWidthD = dataWidth; // 元素图像宽度
						double widthD = widthSpacing + width; // 宽度基本单位
						double inittempD = inittemp; // 下级占位数
						double initNumD = initNum; // 空行初始值
						// 元素图像宽度/宽度基本单位=元素图像占位数 -下级占位数 再除以2，得到空行数
						initNumD = (Math.ceil(dataWidthD / widthD) - inittempD) / 2;
						initNum = (int) initNumD;
						if (initNum == 0) {
							initNum++;
						}
					}
				}
				if (i == 0) {
					widthnums = initNum;
				}

				// 如果上一层存在虚位
				if (blankMap.containsKey(levenum - 1)) {
					Map blankNumTmpMap = (TreeMap) blankMap.get(levenum - 1);
					Iterator blankNumTmpIts = blankNumTmpMap.entrySet()
							.iterator();
					// 迭带出虚位占位数，以便判断
					for (; blankNumTmpIts.hasNext();) {
						Entry blankNumPo = (Entry) blankNumTmpIts.next();
						int blankNumtmp = (Integer) blankNumPo.getKey();
						// 如果下一位为虚位，则自增1，并将此虚位代入下层
						if (blankNumtmp == (widthnums + 1)) {
							widthnums++;
							blankNumMap.put(widthnums, id);
						}
					}
				}
				// 求出本元素，占位数。
				widthnum = codeNum(kindmap, id, 1, widthnums);
				if (widthnum == 0) {
					widthnum = widthnums + 1;
					blankNumMap.put(widthnum, id);
				}

				po.put("widthnumStart", widthnums);
				po.put("widthnumEnd", widthnum);
				// 初始化初始占位数
				widthnums = widthnum;
				int widthttmp = width * widthnum + widthSpacing * widthnum;
				// 本层宽度 widthSpacing为图像间距
				leveWidth = widthttmp < leveWidth ? leveWidth : widthttmp;
				// 本层最大高度
				leveHeigth = (Integer) po.get("height") < leveHeigth ? leveHeigth
						: (Integer) po.get("height");
			}
			blankMap.put(levenum, blankNumMap);
			map.put("leveHeigth", leveHeigth);
			// 获取最宽的一层,以便于确定图像宽度
			imageWidth = imageWidth < leveWidth ? leveWidth : imageWidth;
			// 累计层高度，以便于确实图像高度，widthSpacing为层间距
			imageHeigth = imageHeigth + leveHeigth + heigthSpacing;

		}
		if (initNum > 0) {
			imageWidth += initNum * (width + widthSpacing);
		}
		print(columnMap, kindmap, imageWidth, imageHeigth,filePath,headId);

	}

	/**
	 * 
	 * @param allMap
	 *            所有元素map
	 * @param kindmap
	 *            通过上级调下级map
	 * @param pid
	 *            父id
	 * @param codenum
	 *            上级本层占位数
	 * @param sumnum
	 *            累加占位数
	 * @return
	 * @throws Exception
	 */
	public static int codeNum(HashMap kindmap, String pid, int codenum,
			int sumnum) throws Exception {
		Map codeMap = new HashMap();
		List codelist = new ArrayList();
		// 查出是否有父级，如果有，取出子集信息，比如子集宽度，子集占位
		if (kindmap.containsKey(pid)) {
			codeMap = (Map) kindmap.get(pid);
			codelist = (ArrayList) codeMap.get("codelist");
			// 本级所占格子位
			int num = 0;
			// 求出下级所占的格子位
			for (int i = 0; i < codelist.size(); i++) {
				num++;
				Map po = (Map) codelist.get(i);
				String id = (String) po.get("id");
				String name = (String) po.get("name");
				// 求出下级所占位子数
				int nums = codeNum(kindmap, id, 0, 0);
				// 如果下级占位数大于1，则存在，将下级占位数减去当前占位数1，得到总共占位数
				if (nums > 1)
					num += nums - 1;
			}
			// 如果本层总共战位数大于，其当前层占位数，则替换为最大占位数
			if (num > codenum) {
				codenum = num;
			}
		} else {
			// 没有占位数返回0
			return 0;
		}
		// 本元系总共占位数，加上累加占位数，等于当前排位数。
		codenum += sumnum;
		return codenum;
	}

	public static void print(TreeMap columnMap, HashMap kindmap,
			int imageWidth, int imageHeigth,String filePath,String headId) throws Exception {
		File file = new File(filePath);
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(imageWidth, imageHeigth,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();

		// 将图像填充色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageWidth, imageHeigth);

		GradientPaint g1 = new GradientPaint(100, 0, Color.WHITE, 100,
				imageHeigth, new Color(160, 160, 160), true);
		g.setPaint(g1);
		g.fillRoundRect(0, 0, imageWidth, imageHeigth, 0, 0);
		g.setColor(Color.GRAY);
		// 画边框。
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, imageWidth - 1, imageHeigth - 1);
		Iterator dateIts = columnMap.entrySet().iterator();
		int drawheight = 10;
		for (; dateIts.hasNext();) {
			// 获取层对象
			Entry e = (Entry) dateIts.next();
			// 获取层号
			int key = (Integer) e.getKey();
			// 层属性
			Map map = (HashMap) e.getValue();
			int leveHeigth = (Integer) map.get("leveHeigth");// 层高
			int drawwidth = imageWidth;

			List list = (List) map.get("list");
			int nums = 0;
			int codenum = 0;
			int lineStart = 0;
			int lineTemp = 0;
			int lineEnd = 0;
			String pidSign = "";
			for (int i = 0; i < list.size(); i++) {
				Map po = (Map) list.get(i);
				Map codeMap = new HashMap();

				int lineP = 0;// 父类线标
				int dataWidth = (Integer) po.get("width");// 图像宽
				int dataHeight = (Integer) po.get("height");// 图像高
				String name = (String) po.get("name");// 名称
				String type = (String) po.get("type");// 类型
				String id = (String) po.get("id");// id
				String pid = (String) po.get("pid");// id
				List codelist = (List) po.get("codelist");// 下级集合
				int widthnumStart = (Integer) po.get("widthnumStart");// 开始占位数
				int widthnumEnd = (Integer) po.get("widthnumEnd");// 结束占位数
				int widthnum = widthnumStart;
				for (int tempnum = widthnumStart, tempi = 0; tempnum < widthnumEnd; tempnum++, tempi++) {
					if (widthnumStart + tempi >= widthnumEnd - tempi) {
						widthnum = tempnum;
						break;
					}
				}
				double widthnumStartD = widthnumStart;// 开始占位数
				double widthnumEndD = widthnumEnd;// 结束占位数
				double widthD = width;// 占位数，1单位宽度
				double widthSpacingD = widthSpacing;// 1单位间隔
				double dataWidthD = dataWidth;// 元素长度
				double tempD = widthnumEndD - widthnumStartD;// 结束占位数减开始占位数
				double temp1D = Math
						.ceil((tempD * (widthD + widthSpacingD)) / 2);// 结束占位数-开始占位数*宽度+间隔
																		// 除2
				double tempWidthD = widthnumStart * (width + widthSpacing);// 初始位置
																			// 开始占位数*(宽度+间隔)
				double codenumD = codenum; // 元素实际摆放位置
				if (i == 0) {
					codenumD = tempWidthD + temp1D
							- Math.ceil((dataWidthD / 2));
					codenum = (int) codenumD;
				} else {
					codenumD = (tempWidthD - widthSpacingD * 2) + temp1D
							+ widthSpacingD;
					codenum = (int) codenumD;
				}

				g.setColor(new Color(190, 209, 231));
				// g.drawRect(int x,int y,int width,int height); 在图中绘制一个空心的矩形。
				g.fillRect(codenum, drawheight, dataWidth, leveHeigth);
				// 画边框。
				g.setColor(Color.BLACK);
				g.drawRect(codenum, drawheight, dataWidth + 1, leveHeigth + 1);
				// 如果有下级，则画连接线，并记录方位
				if (kindmap.containsKey(id)) {
					kindmap.put("line", (drawheight + leveHeigth)
							+ heigthSpacing / 2);
					// 画线
					g.drawLine(codenum + dataWidth / 2, drawheight + leveHeigth
							+ 2, codenum + dataWidth / 2,
							(drawheight + leveHeigth) + heigthSpacing / 2);
				}
				// 创建字体，字体的大小应该根据图片的高度来定。
				Font font = new Font("黑体", Font.PLAIN, fontHeight);
				// 设置字体。
				g.setFont(font);
				g.setColor(Color.BLACK);
				if ("lateral".equals(type)) {
					g.drawString(name, codenum + fontWidth, drawheight
							+ leveHeigth / 2 + fontHeight / 2);
				} else {
					char[] fontArray = name.toCharArray();

					for (int fontnum = 0, num = 20; fontnum < fontArray.length; fontnum++, num = num + 20) {
						g.drawString(fontArray[fontnum] + "", codenum
								+ fontWidth / 2, drawheight + num);
					}
				}
				// 顶级节点不划线
				if (!headId.equals(pid)) {
					// 如果为上级节点，则画出向下连接线
					if (kindmap.containsKey(pid)) {
						codeMap = (HashMap) kindmap.get(pid);
						g
								.drawLine(codenum + dataWidth / 2, drawheight,
										codenum + dataWidth / 2, drawheight
												- heigthSpacing / 2);
					}
					// 如果开始位存在，并且元素上级与前次循环记录的上级标志对应，记录结束位
					if (lineStart != 0 && pid.equals(pidSign)) {
						lineEnd = codenum + dataWidth / 2;
						lineTemp++;
					}
					// 如果元素上级变更 或者，循环到最后一位,进行操作
					if (!pid.equals(pidSign) || i == list.size() - 1) {

						// 如果开始位不为0，且结束位不为0，进行画线，并初始开始位与结束位
						if (lineStart != 0 && lineEnd != 0) {
							if (lineTemp > 0) {
								// 画线
								g.drawLine(lineStart, drawheight
										- heigthSpacing / 2, lineEnd,
										drawheight - heigthSpacing / 2);
								lineTemp = 0;
							}
							lineStart = codenum + dataWidth / 2;
							lineEnd = 0;

						}
						// 如果开始位为0，记录开始位
						lineStart = codenum + dataWidth / 2;

					}
				}
				// 记录当前所画图的宽度位置
				drawwidth = drawwidth + codenum + widthSpacing;
				// 更新上级标志
				pidSign = pid;
			}
			// 记录当前所画图的高度位置
			drawheight = drawheight + leveHeigth + heigthSpacing;
		}

		ImageIO.write(buffImg, "jpg", file);
		// 清空缓存
		g.dispose();
	}
}
