package com.shopping.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shopping.factory.ServiceFactory;
import com.shopping.util.JSONUtil;
import com.shopping.util.PageModel;
import com.shopping.vo.CartVo;
import com.shopping.vo.ProductVo;

public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		// 取得命令参数
		String action = request.getParameter("action");

		// 定义转发路径
		String path = "";

		String json = "";

		// 定义一个变量用来判断是否跳转页面，默认不跳转
		boolean flag = false;

		// 根据命令参数进行相应的操作
		if ("show".equals(action)) {
			// 取得要显示商品的id
			int id = Integer.parseInt(request.getParameter("id"));

			ProductVo product = new ProductVo();
			product = ServiceFactory.getProductServiceInstance()
					.findProductById(id);

			request.setAttribute("pro", product);
			path = "user/product.jsp";

			flag = true;
		} else if ("buy".equals(action)) {
			// 取得商品id
			int id = Integer.parseInt(request.getParameter("id"));
			// 取得商品数量
			int amount = Integer.parseInt(request.getParameter("amount"));
			// 取得用户主键
			int userId = (Integer) request.getSession().getAttribute("userId");

			CartVo cart = new CartVo();
			cart.setUserId(userId);
			cart.setProId(id);
			cart.setProAmount(amount);

			if (ServiceFactory.getCartServiceInstance().addCart(cart)) {

				// 查询出当前用户购物车里面的内容
				List<CartVo> list = new ArrayList<CartVo>();
				list = ServiceFactory.getCartServiceInstance()
						.findCartByUserId(userId);
				request.setAttribute("cart", list);
				path = "user/cart.jsp";

				flag = true;
			}
		} else if ("search".equals(action)) {
			// 取得关键字
			String key = "";
			try {
				key = URLDecoder.decode(request.getParameter("keyword"),
						"utf-8");
			} catch (UnsupportedEncodingException e) {
				key = request.getParameter("keyword");
			}

			List<ProductVo> list = new ArrayList<ProductVo>();

			int offset = 0;
			int limit = 5;
			int total = 0;

			try {
				offset = Integer.parseInt(request.getParameter("pager.offset"));
			} catch (NumberFormatException e) {
				// e.printStackTrace();
			}

			list = ServiceFactory.getProductServiceInstance()
					.findProductByLike(key, offset, limit);
			total = ServiceFactory.getProductServiceInstance()
					.getTotalProductByLike(key);

			PageModel pm = new PageModel();
			pm.setTotal(total);
			pm.setDatas(list);

			path = "user/search.jsp";

			// 把关键字也放在request里面
			request.setAttribute("key", key);
			request.setAttribute("pm", pm);

			flag = true;
		} else if ("all".equals(action)) {

			// 分页参数
			int start = 0;
			int limit = 10;

			// 商品总数
			int total = 0;

			// 商品
			List<ProductVo> list = new ArrayList<ProductVo>();

			start = Integer.parseInt(request.getParameter("start"));
			limit = Integer.parseInt(request.getParameter("limit"));

			// 取得商品小类主键
			int itemId = 0;
			try {
				itemId = Integer.parseInt(request.getParameter("itemId"));
			} catch (NumberFormatException e) {
				itemId = 0;
				// e.printStackTrace();
			}

			// 取得查询所用的关键字
			String key = request.getParameter("query");
			if (key == null && itemId == 0) {
				// 没有查询条件，就是查询出全部

				// 商品总数
				total = ServiceFactory.getProductServiceInstance().getTotalNum();

				list = ServiceFactory.getProductServiceInstance().findAllProduct(start, limit);
			} else if (key != null && itemId == 0) {
				// 有查询条件，就是按条件查询，此处是按商品名称进行模糊查询
				total = ServiceFactory.getProductServiceInstance().getTotalProductByLike(key);

				list = ServiceFactory.getProductServiceInstance().findProductByLike(key, start, limit);

				request.setAttribute("query", null);
			} else if (key == null && itemId > 0) {
				// 按商品小类进行查询
				total = ServiceFactory.getProductServiceInstance().getTotalNumber(itemId);

				list = ServiceFactory.getProductServiceInstance().findAllProduct(itemId, start, limit);
			}

			// 组织要返回的json
			json += "{total:" + total + ",list:" + JSONUtil.list2json(list)
					+ "}";

			// 这个是不进行跳转的
			flag = false;
		}

		// 根据path进行跳转
		if (flag) {
			request.getRequestDispatcher(path).forward(request, response);
		} else {
			out.println(json);
		}
	}

}
