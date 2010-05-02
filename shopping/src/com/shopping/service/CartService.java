package com.shopping.service;

import java.util.List;

import com.shopping.vo.CartVo;

/**
 * CartService
 */
public interface CartService {
	/**
	 * 添加到购物车
	 * 
	 * @param cart
	 * @return 是否添加成功
	 */
	public boolean addCart(CartVo cart);

	/**
	 * 按id删除一条购物车里面的记录
	 * 
	 * @param id
	 * @return 是否删除成功
	 */
	public boolean removeCartById(int id);

	/**
	 * 修改购物车
	 * 
	 * @param cart
	 * @return 是否修改成功
	 */
	public boolean modifyCart(CartVo cart);

	/**
	 * 查询所有的购物车并分页
	 * 
	 * @param start
	 *            开始位置
	 * @param limit
	 *            偏移量
	 * @return 符合条件的记录
	 */
	public List<CartVo> findAllCart(int start, int limit);

	/**
	 * 按用户主键查询该用户所添加到购物车里面的商品
	 * 
	 * @param id
	 * @return 购物车里面的商品
	 */
	public List<CartVo> findCartByUserId(int id);

	/**
	 * 按id查询购物车记录
	 * 
	 * @param id
	 * @return 一条购物车记录
	 */
	public CartVo findCartById(int id);

	/**
	 * 按用户的主键删除购物车信息
	 * 
	 * @param id
	 * @return 是否删除成功
	 */
	public boolean removeByUserId(int id);
}
